package engine;

import engine.elements.Record;
import engine.exceptions.DBAppException;
import engine.operations.Initialization;
import engine.operations.creation.CreateIndexParams;
import engine.operations.creation.CreateTableParams;
import engine.operations.creation.IndexCreation;
import engine.operations.creation.TableCreation;
import engine.operations.deletion.DeleteFromTableParams;
import engine.operations.deletion.Deletion;
import engine.operations.dropping.Dropping;
import engine.operations.insertion.InsertIntoTableParams;
import engine.operations.insertion.InsertionWithIndex;
import engine.operations.selection.SQLTerm;
import engine.operations.selection.SelectFromTableParams;
import engine.operations.selection.Selection;
import engine.operations.update.Update;
import engine.operations.update.UpdateTableParams;
import engine.operations.update.UpdateRecordOnClusteringKeyWithIndex;
import utilities.PropertiesReader;

import java.util.*;

@SuppressWarnings("SpellCheckingInspection")
public class DBApp {
    private static final Set<String> supportedSqlLogicalOperators = new HashSet<>(Arrays.asList("AND", "OR", "XOR"));
    private static final String fileExtension = ".ser";
    private static final String rootDatabaseFolder = PropertiesReader.getProperty("databaseRootFolder");
    private static final String tablesRootFolder = rootDatabaseFolder + "/data/";
    private static final String metadataFolderLocation = rootDatabaseFolder + "/Metadata";
    private static final String csvFileLocation = metadataFolderLocation + "/metadata.csv";

    public static String getMetadataFolderLocation() {return metadataFolderLocation;}
    public static String getTablesRootFolder() {
        return tablesRootFolder;
    }
    public static String getCSVFileLocation() {
        return csvFileLocation;
    }

    public static Set<String> getSupportedSqlLogicalOperators() {return supportedSqlLogicalOperators;}
    public static String getFileExtension() {return fileExtension;}
    public static String getIndexFolderLocationOfTable(String tableName) {
        return tablesRootFolder + tableName + "/indices/";
    }
    public static String getTableInfoFileLocation(String tableName) {return tablesRootFolder + tableName + "/info" + fileExtension;}
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
        new TableCreation(createTableParams).createTable();
        printMessage(String.format("Table '%s' created Successfully .", strTableName));
    }



    // following method creates an octree
    // depending on the count of column names passed.
    // If three column names are passed, create an octree.
    // If only one or two column names is passed, throw an Exception.
    public void createIndex(String strTableName,
                            String[] strarrColName) throws DBAppException
    {
        createIndex(strTableName, "XYZIndex", strarrColName);
    }
    private void createIndex(String tableName, String indexName, String[] columnsToIndex) throws DBAppException {
        new IndexCreation(new CreateIndexParams(tableName, indexName, columnsToIndex)).createIndex();
        printMessage(String.format("Index '%s' is created successfully in Table '%s' on column '%s'", indexName, tableName, Arrays.toString(columnsToIndex)));
    }


    // following method inserts one row only.
    // htblColNameValue must include a value for the primary key
    public void insertIntoTable(String strTableName,
                                Hashtable<String,Object> htblColNameValue)
            throws DBAppException
    {
        new InsertionWithIndex(new InsertIntoTableParams(strTableName, new Record(htblColNameValue))).insertIntoTable();
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
        Selection selection = new Selection(new SelectFromTableParams(arrSQLTerms, strarrOperators));
        return selection.select();
    }

    public Iterator parseSQL( StringBuffer strbufSQL ) throws
            DBAppException
    {
        DBParser parser = new DBParser(strbufSQL.toString());
        if(parser.isHavingSyntaxError()) {
            throw new DBAppException("Invalid Statement");
        }
        switch (parser.getCommandType().toUpperCase()) {
            case "SELECT" -> {
                SelectFromTableParams sp = parser.getSelectionParams();
                return selectFromTable(sp.getSqlTerms(), sp.getLogicalOperators());
            }
            case "CREATE" -> {
                switch(parser.getCreationType().toUpperCase()) {
                    case "TABLE" -> {
                        CreateTableParams cp = parser.getTableCreationParams();
                        createTable(cp.getTableName(), cp.getClusteringKey(), cp.getColNameType(), cp.getColNameMin(), cp.getColNameMax());
                    }
                    default -> {
                        CreateIndexParams cp = parser.getIndexCreationParams();
                        createIndex(cp.getTableName(), cp.getIndexName(), cp.getIndexedColumns());
                    }
                }
                return null;
            }
            case "INSERT" -> {
                InsertIntoTableParams ip = parser.getInsertionParams();
                insertIntoTable(ip.getTableName(), ip.getRecord());
                return null;
            }
            case "UPDATE" -> {
                UpdateTableParams up = parser.getUpdateParams();
                updateTable(up.getTableName(), up.getClusteringKeyValue(), up.getColNameValue());
                return null;
            }
            case "DELETE" -> {
                DeleteFromTableParams dp = parser.getDeletionParams();
                deleteFromTable(dp.getTableName(), dp.getColNameValue());
                return null;
            }
            default -> {
                return null;
            }
        }
    }
    public void dropTable(String strTableName) throws DBAppException {
        new Dropping(strTableName).dropTable();
        printMessage("Table deleted successfully");
    }
}
