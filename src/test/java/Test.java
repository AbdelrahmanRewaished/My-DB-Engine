import engine.DBApp;
import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.serialization.Deserializer;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

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
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(tableName);
        for(PageMetaInfo pageMetaInfo: table.getPagesInfo()) {
            pw.println(Page.deserializePage(pageMetaInfo));
        }
        pw.println();
        pw.flush();
    }

    public static void main(String[] args) throws DBAppException {
        DBApp dbApp = new DBApp();
        dbApp.init();
//        dbApp.dropTable("Employee");
//        dbApp.parseSQL(new StringBuffer("create Table Employee (id int check(id > 0 and id < 100) primary KeY, name varChAR(20), salary float, birth_time Date check(birth_time > 1500-01-01 and birth_time < 2500-12-31))"));
//        dbApp.parseSQL(new StringBuffer("CREATE TABLE Manager (id INT PRIMARY KEY, name VARCHAR(20), salary FLOAT, depId INT)"));
//        Random r = new Random();
//        Set<Integer> emp_set = new HashSet<>(), man_set = new HashSet<>();
//        for(int i = 0; i < 20; i++) {
//            int emp_id = r.nextInt(100);
//            while(emp_set.contains(emp_id)) {
//                emp_id = r.nextInt(100);
//            }
//            if(emp_id % 3 == 0) {
//                dbApp.parseSQL(new StringBuffer(String.format("INSERT INTO Employee (id, name) VALUES (%d, %s)", emp_id, "abdo")));
//            }
//            else {
//                dbApp.parseSQL(new StringBuffer(String.format("INSERT INTO Employee (id, name, salary, birthdate) VALUES (%d, %s, %d, %s)", emp_id, "abdo", 12000, "2002-02-10")));
//            }
//            emp_set.add(emp_id);
//            int man_id = r.nextInt(100);
//            while(man_set.contains(man_id)) {
//                man_id = r.nextInt(100);
//            }
//            if(emp_id % 3 == 0) {
//                dbApp.parseSQL(new StringBuffer(String.format("INSERT INTO Manager (id, name) VALUES (%d, %s)", man_id, "abdo")));
//            }
//            else {
//                dbApp.parseSQL(new StringBuffer(String.format("INSERT INTO Manager (id, name, salary, depId) VALUES (%d, %s, %d, %d)", man_id, "abdo", 170000, man_id)));
//            }
//            man_set.add(man_id);
//        }
//          dbApp.parseSQL(new StringBuffer("insert into Employee (id, name, birth_time, salary) values (1, abdo, 2020-12-31, null)"));
//        Record record = new Record();
//        record.put("name", "abdo");
//        record.put("salary", 3000.0);
//        dbApp.updateTable("Manager", "-1", record);
          dbApp.parseSQL(new StringBuffer("update Employee set salary = 15000 where id = 1"));
//        showAllTableRecords("Employee");
//        showAllTableRecords("Manager");
    }
}

