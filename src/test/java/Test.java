import engine.DBApp;
import engine.elements.*;
import engine.elements.Record;
import engine.elements.index.Boundary;
import engine.elements.index.Octree;
import engine.exceptions.DBAppException;
import engine.operations.selection.TableRecordInfo;
import utilities.datatypes.DatabaseTypesHandler;
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
    public static void showAllTableRecords(String tableName) {
        PrintWriter pw = new PrintWriter(System.out);
        pw.println("Table " + tableName + " content: ");
        Table table = (Table) Deserializer.deserialize(DBApp.getTableInfoFileLocation(tableName));
        for(PageMetaInfo pageMetaInfo: table.getPagesInfo()) {
            pw.println(Page.deserializePage(pageMetaInfo));
        }
        pw.println();
        pw.flush();
    }


    public static void main(String[] args) throws DBAppException {
//        Hashtable<String, Comparable> colNameMin = new Hashtable<>();
//        Hashtable<String, Comparable> colNameMax = new Hashtable<>();
//        colNameMin.put("id", 0);
//        colNameMin.put("name", "");
//        colNameMin.put("date", DatabaseTypesHandler.getDate("1500-01-01"));
//
//        colNameMax.put("id", 10000);
//        colNameMax.put("name", "~~~~~~~~~~~~~");
//        colNameMax.put("date", DatabaseTypesHandler.getDate("2500-12-31"));
//
//        Boundary boundary = new Boundary(colNameMin, colNameMax);
//        Octree octree = new Octree("", boundary);
//
//        Record record = new Record();
//        record.put("id", 1);
//        record.put("name", "hossam");
//        record.put("date", DatabaseTypesHandler.getDate("2002-03-10"));
//        octree.insert(new TableRecordInfo(0 ,0));
//
//        record = new Record();
//        record.put("id", 2);
//        record.put("name", "amgad");
//        record.put("date", DatabaseTypesHandler.getDate("2010-05-10"));
//        octree.insert(new TableRecordInfo(new TableRecordInfo(0 ,0), record));
//
//        record = new Record();
//        record.put("id", 3);
//        record.put("name", "essam");
//        record.put("date", DatabaseTypesHandler.getDate("2020-05-10"));
//        octree.insert(new TableRecordInfo(new TableRecordInfo(0 ,0), record));
        DBApp dbApp = new DBApp();
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (1, 'abdo', 12000, '2002-02-10')"));
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (2, 'hossam', 15000, '1999-12-31')"));
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (3, 'ibrahim', 16000, '1680-01-01')"));
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (4, 'emad', 10000, '2003-05-10')"));
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (5, 'emad', 10000, '2003-05-10')"));
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (6, 'emad', 10000, '2003-05-10')"));
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (7, 'emad', 10000, '2003-05-10')"));
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (8, 'emad', 10000, '2003-05-10')"));
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (9, 'emad', 10000, '2003-05-10')"));
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (10, 'emad', 10000, '2003-05-10')"));
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (11, 'emad', 10000, '2003-05-10')"));
        dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, salary, birth_time) values (12, 'emad', 10000, '2003-05-10')"));

    }
}

