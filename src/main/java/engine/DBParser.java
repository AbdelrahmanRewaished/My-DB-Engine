package engine;

import compiler.SQLLexer;
import compiler.SQLParser;
import engine.elements.Record;
import engine.exceptions.DBAppException;
import engine.exceptions.InvalidTypeException;
import engine.exceptions.TableDoesNotExistException;
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

class DBParser {
    private final SQLParser parser;
    private final SQLParser.QueryContext queryContext;

    DBParser(String sqlStatement) {
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

    private SQLTerm[] createSQLTerms(List<String> tableName, List<String> columns, List<String> operators, List<String> values) {
        SQLTerm[] sqlTerms = new SQLTerm[columns.size()];
        for(int i = 0; i < columns.size(); i++) {

        }
        return sqlTerms;
    }
    SelectFromTableParams getSelectionParams() {
        SQLParser.SelectStatementContext selectStatement = queryContext.selectStatement();
        List<String> tableNames = new ArrayList<>();
        for(SQLParser.TableNameContext tableNameContext: selectStatement.tableList().tableName()) {
            tableNames.add(tableNameContext.getText());
        }
        List<String> operators = new ArrayList<>();
        List<String> conditionValues = new ArrayList<>();
        List<String> conditionColumns = new ArrayList<>();
        List<String> logicalOperators = new ArrayList<>();
        SQLParser.ConditionListContext conditionListContext = selectStatement.conditionList();
        while(conditionListContext != null) {
            SQLParser.ConditionExpressionContext expressionContext = conditionListContext.conditionExpression();
            conditionColumns.add(expressionContext.columnName().getText());
            operators.add(expressionContext.operator().getText());
            conditionValues.add(expressionContext.value().getText());
            if(conditionListContext.logicalOperator(0) != null)
                logicalOperators.add(conditionListContext.logicalOperator(0).getText());
            conditionListContext = conditionListContext.conditionList(0);
        }
        System.out.println(String.format("Tables: %s\nCondition Columns: %s\nOperators: %s\nCondition Values: %s\nLogical Operators: %s", tableNames, conditionColumns, operators, conditionValues, logicalOperators));
        return null;
    }

    private Comparable getConditionExpressionBoundValue(Object value, String operator) {
        return switch(operator) {
            case "<=" -> (Comparable) DatabaseTypesHandler.addObjectValue(value, 1);
            case ">=" -> (Comparable) DatabaseTypesHandler.addObjectValue(value, -1);
            default -> (Comparable) value;
        };
    }
    CreateTableParams getCreationParams() throws DBAppException {
        SQLParser.CreateTableStatementContext createTableStatementContext = queryContext.createTableStatement();
        String tableName = createTableStatementContext.tableName().getText();
        String clusteringKey = null;
        Hashtable<String, String> colNameType = new Hashtable<>(), colNameMin = new Hashtable<>(), colNameMax = new Hashtable<>();
        for(SQLParser.ColumnDefinitionContext columnDefinitionContext: createTableStatementContext.columnDefinition()) {
            String columnName = columnDefinitionContext.columnName().getText();
            SQLParser.DataTypeContext dataTypeContext = columnDefinitionContext.dataType();
            String type = dataTypeContext.getText().toUpperCase();
            Bound bound = null;
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
                SQLParser.ColumnDefinitionConditionListContext columnDefinitionConditionListContext = columnDefinitionContext.columnDefinitionConditionList();
                Comparable min = null, max= null;
                while(columnDefinitionConditionListContext != null) {
                    SQLParser.ConditionExpressionContext conditionExpressionContext = columnDefinitionConditionListContext.conditionExpression();
                    if(! columnName.equals(conditionExpressionContext.columnName().getText())) {
                        throw new DBAppException("Invalid Expression");
                    }
                    String conditionCheckValue = conditionExpressionContext.value().getText();
                    String actualType = DatabaseTypesHandler.getType(conditionCheckValue);
                    if(! actualType.equals(DatabaseTypesHandler.getCorrespondingJavaType(type))) {
                        throw new InvalidTypeException(conditionCheckValue, type, actualType);
                    }
                    Object conditionCheckValueObject = DatabaseTypesHandler.getObject(conditionCheckValue, type);
                    String operator = conditionExpressionContext.operator().getText();
                    Comparable boundValue = getConditionExpressionBoundValue(conditionCheckValueObject, operator);
                    if(operator.contains(">") && (min == null || min.compareTo(boundValue) > 0)) {
                        min = boundValue;
                    }
                    if(operator.contains("<") && (max == null || max.compareTo(boundValue) < 0)) {
                        max = boundValue;
                    }
                    columnDefinitionConditionListContext = columnDefinitionConditionListContext.columnDefinitionConditionList(0);
                }
                if(max != null)
                    bound.setMax(DatabaseTypesHandler.getString(max));
                if(min != null)
                    bound.setMin(DatabaseTypesHandler.getString(min));
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
            values.add(valueContext.getText());
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
        List<String> columnNames = new ArrayList<>();
        List<String> updatingValues = new ArrayList<>();
        SQLParser.UpdateListContext updateListContext = updateStatementContext.updateList();
        while(updateListContext != null) {
            SQLParser.EqualityExpressionContext equalityExpressionContext = updateListContext.equalityExpression();
            columnNames.add(equalityExpressionContext.columnName().getText());
            updatingValues.add(equalityExpressionContext.value().getText());
            updateListContext = updateListContext.updateList(0);
        }
        String primaryKeyValue = updateStatementContext.equalityExpression() == null ? null: updateStatementContext.equalityExpression().value().getText();
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
            conditionValues.add(conditionListContext.equalityExpression().value().getText());
            conditionListContext = conditionListContext.deleteConditionList(0);
        }
        Hashtable<String, Object> colNameValue = getColNameValue(tableName, conditionColumns, conditionValues);
        return new DeleteFromTableParams(tableName, colNameValue);
    }
}
