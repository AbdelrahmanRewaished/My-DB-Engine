import compiler.SQLLexer;
import compiler.SQLParser;
import engine.DBApp;
import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import utilities.serialization.Deserializer;

import java.io.PrintWriter;
import java.util.*;

import static utilities.datatypes.DatabaseTypesHandler.*;

public class Test {
    private final DBApp dbApp = new DBApp();
    private final String tableName = "Employee";
    private final String[] columns = {"id", "name", "birthdate", "salary"};
    private Hashtable<String, Object> colNameValue;
    private Hashtable<String, String> getColNameType(String[] names, String[] types) {
        Hashtable<String, String> res = new Hashtable<>();
        for(int i = 0; i < names.length; i++) {
            res.put(names[i], types[i]);
        }
        return res;
    }
    private Hashtable<String, String> getColNameBound(String[] names, String[] bound) {
        Hashtable<String, String> res = new Hashtable<>();
        for(int i = 0; i < names.length; i++) {
            res.put(names[i], bound[i]);
        }
        return res;
    }
    private Hashtable<String, Object> getColNameValue(String[] names, Object[] objects) {
        Hashtable<String, Object> res = new Hashtable<>();
        for(int i = 0; i < names.length; i++) {
            res.put(names[i], objects[i]);
        }
        return res;
    }
    private void initTest() {
        dbApp.init();
    }
    private void createTableTest() throws DBAppException {
        Hashtable<String, String> colNameType = getColNameType(columns, new String[]{
                getIntegerType(),
                getStringType(), getDateType(), getDoubleType()});
        Hashtable<String, String> colNameMin = getColNameBound(columns, new String[]{"0", "A", "1700-01-01", "1000.0"});
        Hashtable<String, String> colNameMax = getColNameBound(columns, new String[]{Integer.MAX_VALUE + "", "zzzzzzzzz", "2500-12-31", "999999.0"});
        String clusteringKey = "id";
        dbApp.createTable(tableName, clusteringKey, colNameType, colNameMin, colNameMax);
    }
    private void insertMultipleValueIntoTablesTest() throws DBAppException {
        colNameValue = getColNameValue(columns, new Object[]{5, "Hossam", new Date(2002, 1, 1), 12500.0});
        dbApp.insertIntoTable(tableName, colNameValue);

        colNameValue = getColNameValue(columns, new Object[]{1, "Hossam", new Date(2002, 1, 1), 12500.0});
        dbApp.insertIntoTable(tableName, colNameValue);

        colNameValue = getColNameValue(columns, new Object[]{2, "Hossam", new Date(2002, 1, 1), 12500.0});
        dbApp.insertIntoTable(tableName, colNameValue);

        colNameValue = getColNameValue(columns, new Object[]{6, "Hossam", new Date(2002, 1, 1), 12500.0});
        dbApp.insertIntoTable(tableName, colNameValue);

        colNameValue = getColNameValue(columns, new Object[]{3, "Hossam", new Date(2002, 1, 1), 12500.0});
        dbApp.insertIntoTable(tableName, colNameValue);
    }
    private void deleteFromTableTest() throws DBAppException {
        colNameValue = getColNameValue(new String[]{"name"}, new Object[]{"Hossam"});
        dbApp.deleteFromTable(tableName, colNameValue);
    }
    private void updateFromTableTest() throws DBAppException {
        colNameValue = getColNameValue(new String[]{"name"}, new Object[]{"Maged"});
        dbApp.updateTable(tableName, "1", colNameValue);
    }
    private void testSQLStatement(String statement) {
        // Create a CharStream from the SQL statement
        CharStream input = CharStreams.fromString(statement);
        // Create a lexer from the input
        SQLLexer lexer = new SQLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create a parser from the tokens
        SQLParser parser = new SQLParser(tokens);

        char command = statement.charAt(0);
        switch(command) {
            case 'S':
                SQLParser.SelectStatementContext selectCtx = parser.selectStatement();
                if(parser.getNumberOfSyntaxErrors() > 0) {
                    break;
                }
                List<String> tableNames = new ArrayList<>();
                for(SQLParser.TableNameContext tableCtx: selectCtx.tableList().tableName()) {
                    tableNames.add(tableCtx.getText());
                }
                List<String> columnNames = new ArrayList<>();
                List<String> operators = new ArrayList<>();
                List<String> conditionValues = new ArrayList<>();
                List<String> conditionColumns = new ArrayList<>();
                List<String> logicalOperators = new ArrayList<>();
                SQLParser.ConditionListContext conCtx = selectCtx.conditionList();
                while(conCtx != null) {
                    conditionColumns.add(conCtx.columnName().getText());
                    operators.add(conCtx.operator().getText());
                    conditionValues.add(conCtx.value().getText());
                    if(conCtx.logicalOperator(0) != null)
                        logicalOperators.add(conCtx.logicalOperator(0).getText());
                    conCtx = conCtx.conditionList(0);
                }
                System.out.println(String.format("Tables: %s\nSelected Columns: %s\nCondition Columns: %s\nOperators: %s\nCondition Values: %s\nLogical Operators: %s", tableNames, "Everything", conditionColumns, operators, conditionValues, logicalOperators));
                break;
            case 'C':
                SQLParser.CreateTableStatementContext createCtx = parser.createTableStatement();
                String tableName = createCtx.tableName().getText();
                columnNames = new ArrayList<>();
                List<String> types = new ArrayList<>();
                for(SQLParser.ColumnDefinitionContext colCtx: createCtx.columnDefinition()) {
                    columnNames.add(colCtx.columnName().getText());
                    types.add(colCtx.dataType().getText());
                }
                System.out.println(String.format("Table: %s\nColumns: %s\nTypes: %s", tableName, columnNames, types));
                break;
            case 'I':
                SQLParser.InsertStatementContext insertCtx = parser.insertStatement();
                if(parser.getNumberOfSyntaxErrors() > 0) {
                    break;
                }
                tableName = insertCtx.tableName().getText();
                List<String> values = new ArrayList<>();
                for(SQLParser.ValueContext valueCtx: insertCtx.valueList().value()) {
                    values.add(valueCtx.getText());
                }
                System.out.println(String.format("Table: %s\nValues: %s", tableName, values));
                break;
            case 'U':
                SQLParser.UpdateStatementContext updateCtx = parser.updateStatement();
                if(parser.getNumberOfSyntaxErrors() > 0) {
                    break;
                }
                tableName = updateCtx.tableName().getText();
                columnNames = new ArrayList<>();
                values = new ArrayList<>();
                String conditionColumn, operator = "=", conditionValue;
                for(SQLParser.ColumnNameContext columnNameContext: updateCtx.updateList().columnName()) {
                    columnNames.add(columnNameContext.getText());
                }
                conditionColumn = updateCtx.columnName().getText();
                conditionValue = updateCtx.value().getText();
                for(SQLParser.ValueContext valueCtx: updateCtx.updateList().value()) {
                    values.add(valueCtx.getText());
                }
                System.out.println(String.format("Table: %s\nColumns: %s\nValues: %s\nCondition Columns: %s\nOperator: %s\nConditionValues: %s", tableName, columnNames, values, conditionColumn, operator, conditionValue));
                break;

            case 'D':
                SQLParser.DeleteStatementContext deleteCtx = parser.deleteStatement();
                if(parser.getNumberOfSyntaxErrors() > 0) {
                    break;
                }
                System.out.println(deleteCtx.deleteConditionList().getText());
                tableName = deleteCtx.tableName().getText();
                conditionColumns = new ArrayList<>();
                conditionValues = new ArrayList<>();
                logicalOperators = new ArrayList<>();
                SQLParser.DeleteConditionListContext deleteConditionList = deleteCtx.deleteConditionList();
                while(deleteConditionList != null) {
                    conditionColumns.add(deleteConditionList.columnName().getText());
                    conditionValues.add(deleteConditionList.value().getText());
                    if(deleteConditionList.logicalOperator(0) != null)
                        logicalOperators.add(deleteConditionList.logicalOperator(0).getText());
                    deleteConditionList = deleteConditionList.deleteConditionList(0);
                }
                System.out.println(String.format("Table: %s\nCondition Columns: %s\nCondition values: %s\nLogical Operators: %s", tableName, conditionColumns, conditionValues, logicalOperators));
                break;
            default:
                parser.query();
        }
    }
    public static void showAllTableRecords(String tableName) {
        PrintWriter pw = new PrintWriter(System.out);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(tableName);
        for(PageMetaInfo pageMetaInfo: table.getPagesInfo()) {
            pw.println(Page.deserializePage(pageMetaInfo));
        }
        pw.flush();
    }
}

