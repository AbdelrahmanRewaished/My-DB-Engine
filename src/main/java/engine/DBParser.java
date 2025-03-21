package engine;

import parser.SQLLexer;
import parser.SQLParser;
import engine.elements.Record;
import engine.exceptions.ColumnDoesNotExistException;
import engine.exceptions.DBAppException;
import engine.exceptions.InvalidTypeException;
import engine.exceptions.TableDoesNotExistException;
import engine.exceptions.update_exceptions.UpdateConditionMustOnlyHavePrimaryKeyException;
import engine.operations.creation.CreateIndexParams;
import engine.operations.creation.CreateTableParams;
import engine.operations.deletion.DeleteFromTableParams;
import engine.operations.insertion.InsertIntoTableParams;
import engine.operations.selection.SQLTerm;
import engine.operations.selection.SelectFromTableParams;
import engine.operations.update.UpdateTableParams;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import utilities.datatypes.Bound;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.metadata.MetadataReader;
import utilities.validation.ParserValidator;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ALL")
public class DBParser {
    private final SQLParser parser;
    private final SQLParser.QueryContext queryContext;

    public DBParser(String sqlStatement) {
        CharStream input = CharStreams.fromString(sqlStatement);
        // Create a lexer from the input
        SQLLexer lexer = new SQLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser = new SQLParser(tokens);
        queryContext = parser.query();
    }
    boolean isHavingSyntaxError() {
        return parser.getNumberOfSyntaxErrors() > 0;
    }
    String getCommandType() {
        return queryContext.getChild(0).getChild(0).getText();
    }

    String getCreationType() {return queryContext.getChild(0).getChild(1).getText();}

    private String getValue(SQLParser.ValueContext valueContext) {
        if(valueContext.date() != null) {
            return valueContext.date().dateValue().getText();
        }
        if(valueContext.string() != null) {
            return valueContext.string().stringWord().getText();
        }
        return valueContext.getText();
    }
    private SQLTerm[] getSQLTerms(String tableName, List<String> columns, List<String> operators, List<String> values) throws DBAppException {
        if(columns.isEmpty() && operators.isEmpty() && values.isEmpty()) {
            return new SQLTerm[]{new SQLTerm(tableName, null, null, null)};
        }
        SQLTerm[] sqlTerms = new SQLTerm[columns.size()];
        if(MetadataReader.search(tableName) == -1) {
            throw new TableDoesNotExistException(tableName);
        }
        for(int i = 0; ! columns.isEmpty(); i++) {
            String columnName = columns.remove(0);
            if(MetadataReader.getTableColumnMetadataRecord(tableName, columnName) == null) {
                throw new ColumnDoesNotExistException(tableName, columnName);
            }
            String columnType = Objects.requireNonNull(MetadataReader.getTableColumnMetadataRecord(tableName, columnName)).getColumnType();
            String operator = operators.remove(0);
            String value = values.remove(0);
            Object objectValue = DatabaseTypesHandler.getObject(value, columnType);
            sqlTerms[i] = new SQLTerm(tableName, columnName, operator, objectValue);
        }
        return sqlTerms;
    }
    public SelectFromTableParams getSelectionParams() throws DBAppException {
        SQLParser.SelectStatementContext selectStatement = queryContext.selectStatement();
        String tableName = selectStatement.tableName().getText();
        List<String> operators = new ArrayList<>();
        List<String> conditionValues = new ArrayList<>();
        List<String> conditionColumns = new ArrayList<>();
        SQLParser.ConditionListContext conditionListContext = selectStatement.conditionList();
        List<String> logicalOperators = new ArrayList<>();
        if(conditionListContext == null) {
            return new SelectFromTableParams(getSQLTerms(tableName, conditionColumns, operators, conditionValues), logicalOperators.toArray(new String[0]));
        }
        for(SQLParser.ConditionExpressionContext expressionContext: conditionListContext.conditionExpression()) {
            conditionColumns.add(expressionContext.columnName().getText());
            operators.add(expressionContext.operator().getText());
            conditionValues.add(getValue(expressionContext.value()));
        }

        for(SQLParser.LogicalOperatorContext logicalOperatorContext: conditionListContext.logicalOperator()) {
            logicalOperators.add(logicalOperatorContext.getText());
        }
        return new SelectFromTableParams(getSQLTerms(tableName, conditionColumns, operators, conditionValues), logicalOperators.toArray(new String[0]));
    }

    private Comparable getConditionExpressionBoundValue(Object value, String operator) {
        return switch(operator) {
            case "<" -> (Comparable) DatabaseTypesHandler.addObjectValue(value, -1);
            case ">" -> (Comparable) DatabaseTypesHandler.addObjectValue(value, 1);
            default -> (Comparable) value;
        };
    }
    private void adjustBound(Bound bound, SQLParser.ColumnDefinitionConditionListContext columnDefinitionConditionListContext, String columnName, String type) throws DBAppException {
        Comparable min = null, max= null;
        while(columnDefinitionConditionListContext != null) {
            SQLParser.ConditionExpressionContext conditionExpressionContext = columnDefinitionConditionListContext.conditionExpression();
            if(! columnName.equals(conditionExpressionContext.columnName().getText())) {
                throw new DBAppException("Invalid Expression");
            }
            String conditionCheckValue = getValue(conditionExpressionContext.value());
            String actualType = DatabaseTypesHandler.getType(conditionCheckValue);
            if(! DatabaseTypesHandler.isCompatibleTypes(actualType, conditionCheckValue)) {
                throw new InvalidTypeException(conditionCheckValue, DatabaseTypesHandler.getCorrespondingJavaType(type), actualType);
            }
            Object conditionCheckValueObject = DatabaseTypesHandler.getObject(conditionCheckValue, DatabaseTypesHandler.getCorrespondingJavaType(type));
            String operator = conditionExpressionContext.operator().getText();
            Comparable boundValue = getConditionExpressionBoundValue(conditionCheckValueObject, operator);
            if(operator.contains(">") && (min == null || min.compareTo(boundValue) > 0)) {
                min = boundValue;
            }
            else if(operator.contains("<") && (max == null || max.compareTo(boundValue) < 0)) {
                max = boundValue;
            }
            else if(operator.equals("=")) {
                if(min == null || min.compareTo(boundValue) > 0) {
                    min = boundValue;
                }
                if(max == null || max.compareTo(boundValue) < 0) {
                    max = boundValue;
                }
            }
            columnDefinitionConditionListContext = columnDefinitionConditionListContext.columnDefinitionConditionList(0);
        }
        if(max != null) {
            String maxBound = DatabaseTypesHandler.getString(max);
            bound.setMax(maxBound);
        }
        if(min != null) {
            String minBound = DatabaseTypesHandler.getString(min);
            bound.setMin(minBound);
        }
    }
    CreateTableParams getTableCreationParams() throws DBAppException {
        SQLParser.CreateTableStatementContext createTableStatementContext = queryContext.createTableStatement();
        String tableName = createTableStatementContext.tableName().getText();
        String clusteringKey = null;
        Hashtable<String, String> colNameType = new Hashtable<>(), colNameMin = new Hashtable<>(), colNameMax = new Hashtable<>();
        for(SQLParser.ColumnDefinitionContext columnDefinitionContext: createTableStatementContext.columnDefinition()) {
            String columnName = columnDefinitionContext.columnName().getText();
            SQLParser.DataTypeContext dataTypeContext = columnDefinitionContext.dataType();
            String type = dataTypeContext.getText().toUpperCase();
            Bound bound;
            boolean isPrimaryKey = clusteringKey == null;
            if(isPrimaryKey) {
                clusteringKey = columnName;
            }
            if(type.contains(DatabaseTypesHandler.getSqlVarcharType())) {
                bound = DatabaseTypesHandler.getBoundDataValues(type, isPrimaryKey, Integer.parseInt(dataTypeContext.integer().getText()));
            }
            else{
                bound = DatabaseTypesHandler.getBoundDataValues(type, isPrimaryKey, null);
            }
            if(columnDefinitionContext.CHECK() != null) {
                adjustBound(bound, columnDefinitionContext.columnDefinitionConditionList(), columnName, type);
            }
            colNameType.put(columnName, DatabaseTypesHandler.getCorrespondingJavaType(type.toUpperCase()));
            colNameMin.put(columnName, bound.getMin());
            colNameMax.put(columnName, bound.getMax());
        }
        return new CreateTableParams(tableName, clusteringKey, colNameType, colNameMin, colNameMax);
    }
    private Hashtable<String, Object> getColNameValue(String tableName, List<String> columnNames, List<String> values) throws DBAppException {
        ParserValidator.checkIfAllColumnsExist(tableName, columnNames);
        Hashtable<String, String> colNameType = MetadataReader.getTableColNameType(tableName);
        ParserValidator.checkIfAllValuesAreInTheCorrectType(colNameType, columnNames, values);
        Hashtable<String, Object> colNameValue = new Hashtable<>();
        for(int i = 0; i < columnNames.size(); i++) {
            colNameValue.put(columnNames.get(i), DatabaseTypesHandler.getObject(values.get(i), colNameType.get(columnNames.get(i))));
        }
        return colNameValue;
    }
    InsertIntoTableParams getInsertionParams() throws DBAppException {
        SQLParser.InsertStatementContext insertStatementContext = queryContext.insertStatement();
        if(insertStatementContext.columnList().columnName().size() != insertStatementContext.valueList().value().size()) {
            throw new DBAppException("Invalid Expression");
        }
        String tableName = insertStatementContext.tableName().getText();
        if(MetadataReader.search(tableName) == -1) {
            throw new TableDoesNotExistException(tableName);
        }
        List<String> columnNames = new ArrayList<>();
        for(SQLParser.ColumnNameContext columnNameContext: insertStatementContext.columnList().columnName()) {
            columnNames.add(columnNameContext.getText());
        }
        List<String> values = new ArrayList<>();
        for(SQLParser.ValueContext valueContext: insertStatementContext.valueList().value()) {
            values.add(getValue(valueContext));
        }
        Hashtable<String, Object> colNameValue = getColNameValue(tableName, columnNames, values);
        return new InsertIntoTableParams(tableName, new Record(colNameValue));
    }
    UpdateTableParams getUpdateParams() throws DBAppException {
        SQLParser.UpdateStatementContext updateStatementContext = queryContext.updateStatement();
        String tableName = updateStatementContext.tableName().getText();
        if(MetadataReader.search(tableName) == -1) {
            throw new TableDoesNotExistException(tableName);
        }
        String conditionColumn = null;
        if(updateStatementContext.equalityExpression() != null) {
            conditionColumn = updateStatementContext.equalityExpression().columnName().getText();
        }
        String tablePrimaryKey = MetadataReader.getClusteringKey(tableName);
        if(conditionColumn != null && ! conditionColumn.equals(tablePrimaryKey)) {
            throw new UpdateConditionMustOnlyHavePrimaryKeyException(tableName, conditionColumn, tablePrimaryKey);
        }
        List<String> columnNames = new ArrayList<>();
        List<String> updatingValues = new ArrayList<>();
        SQLParser.UpdateListContext updateListContext = updateStatementContext.updateList();
        while(updateListContext != null) {
            SQLParser.EqualityExpressionContext equalityExpressionContext = updateListContext.equalityExpression();
            columnNames.add(equalityExpressionContext.columnName().getText());
            updatingValues.add(getValue(equalityExpressionContext.value()));
            updateListContext = updateListContext.updateList(0);
        }
        String primaryKeyValue = updateStatementContext.equalityExpression() == null ? null: getValue(updateStatementContext.equalityExpression().value());
        Hashtable<String, Object> colNameValue = getColNameValue(tableName, columnNames, updatingValues);
        return new UpdateTableParams(tableName, primaryKeyValue, new Record(colNameValue));
    }
    DeleteFromTableParams getDeletionParams() throws DBAppException {
        SQLParser.DeleteStatementContext deleteStatementContext = queryContext.deleteStatement();
        String tableName = deleteStatementContext.tableName().getText();
        if(MetadataReader.search(tableName) == -1) {
            throw new TableDoesNotExistException(tableName);
        }
        List<String> conditionColumns = new ArrayList<>();
        List<String> conditionValues = new ArrayList<>();
        SQLParser.DeleteConditionListContext conditionListContext = deleteStatementContext.deleteConditionList();
        while(conditionListContext != null) {
            conditionColumns.add(conditionListContext.equalityExpression().columnName().getText());
            conditionValues.add(getValue(conditionListContext.equalityExpression().value()));
            conditionListContext = conditionListContext.deleteConditionList(0);
        }
        Hashtable<String, Object> colNameValue = getColNameValue(tableName, conditionColumns, conditionValues);
        return new DeleteFromTableParams(tableName, colNameValue);
    }

    public CreateIndexParams getIndexCreationParams() throws DBAppException {
        SQLParser.CreateIndexStatementContext createIndexStatementContext = queryContext.createIndexStatement();
        String tableName = createIndexStatementContext.tableName().getText();
        String indexName = createIndexStatementContext.indexName().getText();
        SQLParser.ColumnListContext columnListContext = createIndexStatementContext.columnList();
        List<String> columnNamesList = new ArrayList<>();
        for(SQLParser.ColumnNameContext columnNameContext: columnListContext.columnName()) {
            columnNamesList.add(columnNameContext.getText());
        }
        String[] columnNames = columnNamesList.toArray(new String[0]);
        return new CreateIndexParams(tableName, indexName, columnNames);
    }
}
