import compiler.SQLLexer;
import compiler.SQLParser;
import engine.DBApp;
import engine.DBAppException;
import engine.elements.Page;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import utilities.DatabaseTypesHandler;
import utilities.FileHandler;
import utilities.serialization.Deserializer;

import java.io.LineNumberInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import static utilities.DatabaseTypesHandler.*;

public class Test {
    private DBApp dbApp = new DBApp();
    private String tableName = "Employee";
    private String[] columns = {"id", "name", "birthdate", "salary"};
    private String clusteringKey = "id";
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
                if(selectCtx.columnList() != null) {
                    for (SQLParser.ColumnNameContext colCtx : selectCtx.columnList().columnName()) {
                        columnNames.add(colCtx.getText());
                    }
                    SQLParser.ConditionListContext conCtx = selectCtx.conditionList();
                    while(conCtx != null) {
                        conditionColumns.add(conCtx.columnName().getText());
                        operators.add(conCtx.operator().getText());
                        conditionValues.add(conCtx.value().getText());
                        if(conCtx.logicalOperator(0) != null)
                            logicalOperators.add(conCtx.logicalOperator(0).getText());
                        conCtx = conCtx.conditionList(0);
                    }
                }
                System.out.println(String.format("Tables: %s\nSelected Columns: %s\nCondition Columns: %s\nOperators: %s\nCondition Values: %s\nLogical Operators: %s", tableNames, columnNames, conditionColumns, operators, conditionValues, logicalOperators));
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
                operators = new ArrayList<>();
                conditionColumns = new ArrayList<>();
                conditionValues = new ArrayList<>();
                logicalOperators = new ArrayList<>();
                for(SQLParser.ColumnNameContext columnNameContext: updateCtx.updateList().columnName()) {
                    columnNames.add(columnNameContext.getText());
                }
                SQLParser.ConditionListContext conCtx = updateCtx.conditionList();
                while(conCtx != null) {
                    conditionColumns.add(conCtx.columnName().getText());
                    operators.add(conCtx.operator().getText());
                    conditionValues.add(conCtx.value().getText());
                    if(conCtx.logicalOperator(0) != null)
                        logicalOperators.add(conCtx.logicalOperator(0).getText());
                    conCtx = conCtx.conditionList(0);
                }
                for(SQLParser.ValueContext valueCtx: updateCtx.updateList().value()) {
                    values.add(valueCtx.getText());
                }
                System.out.println(String.format("Table: %s\nColumns: %s\nValues: %s\nCondition Columns: %s\nOperator: %s\nConditionValues: %s\nLogical Operators: %s", tableName, columnNames, values, conditionColumns, operators, conditionValues, logicalOperators));
                break;

            case 'D':
                SQLParser.DeleteStatementContext deleteCtx = parser.deleteStatement();
                if(parser.getNumberOfSyntaxErrors() > 0) {
                    break;
                }
                System.out.println(deleteCtx.conditionList().getText());
                tableName = deleteCtx.tableName().getText();
                conditionColumns = new ArrayList<>();
                operators = new ArrayList<>();
                conditionValues = new ArrayList<>();
                logicalOperators = new ArrayList<>();
                conCtx = deleteCtx.conditionList();
                while(conCtx != null) {
                    conditionColumns.add(conCtx.columnName().getText());
                    operators.add(conCtx.operator().getText());
                    conditionValues.add(conCtx.value().getText());
                    if(conCtx.logicalOperator(0) != null)
                        logicalOperators.add(conCtx.logicalOperator(0).getText());
                    conCtx = conCtx.conditionList(0);
                }
                System.out.println(String.format("Table: %s\nCondition Columns: %s\nOperator: %s\nCondition values: %s\nLogical Operators: %s", tableName, conditionColumns, operators, conditionValues, logicalOperators));
                break;
            default:
                break;
        }
    }
    public static void main(String[] args) throws DBAppException {
        Test test = new Test();
        test.testSQLStatement("SELEC id, name, birthdate FROM Employee WHERE name = ahmed AND age = 13 OR birthdate > 2002-03-01");
    }
}
