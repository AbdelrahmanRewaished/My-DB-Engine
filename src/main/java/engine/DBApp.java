package engine;

import engine.elements.PageInfo;
import engine.elements.Table;
import engine.operations.*;
import engine.elements.Record;
import engine.operations.Creation;
import engine.operations.Deletion;
import engine.operations.Insertion;
import engine.operations.Selection;
import engine.operations.Update;
import engine.operations.paramters.*;
import utilities.serialization.Deserializer;

import java.util.*;

public class DBApp {

    private static final String rootDatabaseFolder = System.getenv("ROOT_DATABASE_FOLDER") + "/DBEngine";
    private static final String tablesRootFolder = rootDatabaseFolder + "/tables/";
    private static final String serializedTablesInfoLocation = tablesRootFolder + "serializedTablesInfo.txt";
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
        CreateTableParams createTableParams = new CreateTableParams(strTableName, strClusteringKeyColumn, htblColNameType, htblColNameMin, htblColNameMax);
        new Creation(createTableParams).createTable();
        printMessage(String.format("Table '%s' created Successfully .", strTableName));
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
        new Insertion(new InsertIntoTableParams(strTableName, new Record(htblColNameValue))).insertIntoTable();
        printMessage("1 Row Affected");
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
        int recordsUpdated = new Update(new UpdateTableParams(strTableName, strClusteringKeyValue, new Record(htblColNameValue))).updateTable();
        printMessage(String.format("%d Row(s) Affected", recordsUpdated));
    }

    // following method could be used to delete one or more rows.
    // htblColNameValue holds the key and value. This will be used in search
    // to identify which rows/tuples to delete.
    // htblColNameValue enteries are ANDED together
    public void deleteFromTable(String strTableName,
                                Hashtable<String,Object> htblColNameValue)
            throws DBAppException
    {
        int rowsDeleted = new Deletion(new DeleteFromTableParams(strTableName, htblColNameValue)).deleteFromTable();
        printMessage(String.format("%d row(s) deleted", rowsDeleted));
    }

    public Iterator selectFromTable(SQLTerm[] arrSQLTerms,
                                    String[] strarrOperators)
            throws DBAppException
    {
        return new Selection(new SelectFromTableParams(arrSQLTerms, strarrOperators)).select();
    }

    public Iterator parseSQL( StringBuffer strbufSQL ) throws
            DBAppException
    {
        DBParser parser = new DBParser(strbufSQL.toString());
        if(parser.isHavingSyntaxError()) {
            throw new DBAppException("Invalid Statement");
        }
        switch(parser.getCommandType()) {
            case "SELECT":
                SelectFromTableParams sp = parser.getSelectionParams();
                return selectFromTable(sp.getSqlTerms(), sp.getLogicalOperators());
            case "CREATE":
                CreateTableParams cp = parser.getCreationParams();
                createTable(cp.getTableName(), cp.getClusteringKey(), cp.getColNameType(), cp.getColNameMin(), cp.getColNameMax());
                return null;
            case "INSERT":
                InsertIntoTableParams ip = parser.getInsertionParams();
                insertIntoTable(ip.getTableName(), ip.getRecord());
                return null;
            case "UPDATE":
                UpdateTableParams up = parser.getUpdateParams();
                updateTable(up.getTableName(), up.getClusteringKeyValue(), up.getColNameValue());
                return null;
            case "DELETE":
                DeleteFromTableParams dp = parser.getDeletionParams();
                deleteFromTable(dp.getTableName(), dp.getColNameValue());
                return null;
            default:
                return null;
        }
    }
    public void dropTable(String strTableName) throws DBAppException {
        new Dropping(strTableName).drop();
        printMessage("Table deleted successfully");
    }
}
