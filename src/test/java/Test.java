import engine.DBApp;
import engine.DBAppException;
import engine.elements.Page;
import utilities.DatabaseTypesHandler;
import utilities.FileHandler;
import utilities.serialization.Deserializer;

import java.util.Date;
import java.util.Hashtable;

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
        dbApp.updateTable(tableName, clusteringKey, colNameValue);
    }
    public static void main(String[] args) throws DBAppException {
        Test test = new Test();
        test.initTest();
        test.createTableTest();
        test.insertMultipleValueIntoTablesTest();

        test.deleteFromTableTest();
//        System.out.println(Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation()));
        test.updateFromTableTest();
//        FileHandler.deleteDatabaseRootFolder();
    }
}
