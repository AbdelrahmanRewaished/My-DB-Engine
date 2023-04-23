package engine;

import compiler.SQLLexer;
import compiler.SQLParser;
import engine.elements.Record;
import engine.operations.paramters.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import utilities.datatypes.Bound;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.metadata.MetadataReader;
import utilities.validation.Validator;

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
            conditionColumns.add(conditionListContext.columnName().getText());
            operators.add(conditionListContext.operator().getText());
            conditionValues.add(conditionListContext.value().getText());
            if(conditionListContext.logicalOperator(0) != null)
                logicalOperators.add(conditionListContext.logicalOperator(0).getText());
            conditionListContext = conditionListContext.conditionList(0);
        }
        System.out.println(String.format("Tables: %s\nCondition Columns: %s\nOperators: %s\nCondition Values: %s\nLogical Operators: %s", tableNames, conditionColumns, operators, conditionValues, logicalOperators));
        return null;
    }

    CreateTableParams getCreationParams() {
        SQLParser.CreateTableStatementContext createTableStatementContext = queryContext.createTableStatement();
        String tableName = createTableStatementContext.tableName().getText();
        String clusteringKey = null;
        Hashtable<String, String> colNameType = new Hashtable<>(), colNameMin = new Hashtable<>(), colNameMax = new Hashtable<>();
        for(SQLParser.ColumnDefinitionContext columnNameContext: createTableStatementContext.columnDefinition()) {
            String columnName = columnNameContext.columnName().getText();
            SQLParser.DataTypeContext dataTypeContext = columnNameContext.dataType();
            String type = dataTypeContext.getText();
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
            colNameType.put(columnName, DatabaseTypesHandler.getCorrespondingJavaType(type));
            colNameMin.put(columnName, bound.getMin());
            colNameMax.put(columnName, bound.getMax());
        }
        return new CreateTableParams(tableName, clusteringKey, colNameType, colNameMin, colNameMax);
    }
    private int getRequiredMetadataTableInfoIndex(String tableName) throws DBAppException{
        int res =  MetadataReader.search(tableName);
        if(res == -1) {
            throw new DBAppException(String.format("Table '%s' does not exist", tableName));
        }
        return res;
    }

    private Hashtable<String, Object> getColNameValue(int requiredMetadataTableInfoIndex, String tableName, List<String> columnNames, List<String> values) throws DBAppException {
        Validator.checkIfAllColumnsExist(requiredMetadataTableInfoIndex, tableName, columnNames);
        Hashtable<String, String> colNameType = MetadataReader.getTableColNameType(requiredMetadataTableInfoIndex, tableName);
        Hashtable<String, Object> colNameValue = new Hashtable<>();
        for(int i = 0; i < columnNames.size(); i++) {
            colNameValue.put(columnNames.get(i), DatabaseTypesHandler.getObject(values.get(i), colNameType.get(columnNames.get(i))));
        }
        return colNameValue;
    }
    InsertIntoTableParams getInsertionParams() throws DBAppException {
        SQLParser.InsertStatementContext insertStatementContext = queryContext.insertStatement();
        String tableName = insertStatementContext.tableName().getText();
        int requiredMetadataTableInfoIndex = getRequiredMetadataTableInfoIndex(tableName);
        List<String> columnNames = new ArrayList<>();
        for(SQLParser.ColumnNameContext columnNameContext: insertStatementContext.columnList().columnName()) {
            columnNames.add(columnNameContext.getText());
        }
        List<String> values = new ArrayList<>();
        for(SQLParser.ValueContext valueContext: insertStatementContext.valueList().value()) {
            values.add(valueContext.getText());
        }
        Hashtable<String, Object> colNameValue = getColNameValue(requiredMetadataTableInfoIndex, tableName, columnNames, values);
        return new InsertIntoTableParams(tableName, new Record(colNameValue));
    }
    UpdateTableParams getUpdateParams() throws DBAppException {
        SQLParser.UpdateStatementContext updateCtx = queryContext.updateStatement();
        String tableName = updateCtx.tableName().getText();
        List<String> columnNames = new ArrayList<>();
        int requiredMetadataTableInfoIndex = getRequiredMetadataTableInfoIndex(tableName);
        if(requiredMetadataTableInfoIndex == -1) {
            throw new DBAppException(String.format("Table '%s' does not exist", tableName));
        }
        for(SQLParser.ColumnNameContext columnNameContext: updateCtx.updateList().columnName()) {
            columnNames.add(columnNameContext.getText());
        }
        List<String> updatingValues = new ArrayList<>();
        for(SQLParser.ValueContext valueCtx: updateCtx.updateList().value()) {
            updatingValues.add(valueCtx.getText());
        }
        String primaryKeyValue = updateCtx.value() == null ? null: updateCtx.value().getText();
        Hashtable<String, Object> colNameValue = getColNameValue(requiredMetadataTableInfoIndex, tableName, columnNames, updatingValues);
        return new UpdateTableParams(tableName, primaryKeyValue, new Record(colNameValue));
    }
    DeleteFromTableParams getDeletionParams() throws DBAppException {
        SQLParser.DeleteStatementContext deleteStatementContext = queryContext.deleteStatement();
        String tableName = deleteStatementContext.tableName().getText();
        List<String> conditionColumns = new ArrayList<>();
        List<String> conditionValues = new ArrayList<>();
        List<String> logicalOperators = new ArrayList<>();
        SQLParser.DeleteConditionListContext conditionListContext = deleteStatementContext.deleteConditionList();
        while(conditionListContext != null) {
            conditionColumns.add(conditionListContext.columnName().getText());
            conditionValues.add(conditionListContext.value().getText());
            if(conditionListContext.logicalOperator(0) != null)
                logicalOperators.add(conditionListContext.logicalOperator(0).getText());
            conditionListContext = conditionListContext.deleteConditionList(0);
        }
        int requiredMetadataTableInfoIndex = getRequiredMetadataTableInfoIndex(tableName);
        Hashtable<String, Object> colNameValue = getColNameValue(requiredMetadataTableInfoIndex, tableName, conditionColumns, conditionValues);
        return new DeleteFromTableParams(tableName, colNameValue);
    }
}
