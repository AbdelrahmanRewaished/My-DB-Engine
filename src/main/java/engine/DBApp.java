package engine;

import engine.operations.*;
import engine.elements.Record;

import java.util.Hashtable;
import java.util.Iterator;

public class DBApp {

    private static final String rootDatabaseFolder = System.getenv("ROOT_DATABASE_FOLDER") + "/DBEngine";
    private static final String tablesRootFolder = rootDatabaseFolder + "/tables";
    private static final String serializedTablesInfoLocation = tablesRootFolder + "/serializedTablesInfo.txt";
    public static String getRootDatabaseFolder() {
        return rootDatabaseFolder;
    }
    public static String getTablesRootFolder() {
        return tablesRootFolder;
    }
    public static String getSerializedTablesInfoLocation() {
        return serializedTablesInfoLocation;
    }
    // execute at application startup

    private void printMessage(String message) {
        System.out.println(message);
        System.out.println();
        System.out.println();
    }
    public void init() {
        Initialization.init();
    }



    // following method creates one table only
    // strClusteringKeyColumn is the name of the column that will be the primary
    // key and the clustering column as well. The data type of that column will
    // be passed in htblColNameType
    // htblColNameValue will have the column name as key and the data
    // type as value
    // htblColNameMin and htblColNameMax for passing minimum and maximum values
    // for data in the column. Key is the name of the column
    public void createTable(String strTableName,
                            String strClusteringKeyColumn,
                            Hashtable<String,String> htblColNameType,
                            Hashtable<String,String> htblColNameMin,
                            Hashtable<String,String> htblColNameMax )
            throws DBAppException
    {
        Creation.createTable(strTableName, strClusteringKeyColumn, htblColNameType, htblColNameMin, htblColNameMax);
        printMessage(String.format("Table '%s' is created Successfully .", strTableName));
    }



    // following method creates an octree
    // depending on the count of column names passed.
    // If three column names are passed, create an octree.
    // If only one or two column names is passed, throw an Exception.
    public void createIndex(String strTableName,
                            String[] strarrColName) throws DBAppException
    {
        printMessage(String.format("Index is created successfully in Table '%s' on column '%s'", strTableName, strarrColName));
    }


    // following method inserts one row only.
    // htblColNameValue must include a value for the primary key
    public void insertIntoTable(String strTableName,
                                Hashtable<String,Object> htblColNameValue)
            throws DBAppException
    {
        Insertion.insertIntoTable(strTableName, new Record(htblColNameValue));
        printMessage("Record is inserted Successfully .");
    }


    // following method updates one row only
    // htblColNameValue holds the key and new value
    // htblColNameValue will not include clustering key as column name
    // strClusteringKeyValue is the value to look for to find the row to update.
    public void updateTable(String strTableName,
                            String strClusteringKeyValue,
                            Hashtable<String,Object> htblColNameValue )
            throws DBAppException
    {
        Update.updateTable(strTableName, strClusteringKeyValue, new Record(htblColNameValue));
        printMessage("Record is Updated Successfully .");
    }

    // following method could be used to delete one or more rows.
    // htblColNameValue holds the key and value. This will be used in search
    // to identify which rows/tuples to delete.
    // htblColNameValue enteries are ANDED together
    public void deleteFromTable(String strTableName,
                                Hashtable<String,Object> htblColNameValue)
            throws DBAppException
    {
        int rowsDeleted = Deletion.deleteFromTable(strTableName, htblColNameValue);
        printMessage(String.format("%d row(s) deleted", rowsDeleted));
    }

    public Iterator selectFromTable(SQLTerm[] arrSQLTerms,
                                    String[] strarrOperators)
            throws DBAppException
    {
        return null;
    }
}
