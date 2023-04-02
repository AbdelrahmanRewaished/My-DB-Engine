package engine.operations;

import engine.DBApp;
import engine.DBAppException;
import engine.elements.Table;
import utilities.FileHandler;
import utilities.metadata.MetaDataWriter;
import utilities.Validator;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;

import java.util.HashMap;
import java.util.Hashtable;
public class Creation {

    public static void createTable(String tableName, String clusteringKey, Hashtable<String, String> colNameType, Hashtable<String, String> colNameMin, Hashtable<String, String> colNameMax) throws DBAppException {
        Validator.checkCreationValidity(tableName, clusteringKey, colNameType, colNameMin, colNameMax);
        String tableLocation = FileHandler.createFolder(DBApp.getTablesRootFolder() + tableName);
        Table table = new Table(tableName, tableLocation, clusteringKey);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        serializedTablesInfo.put(tableName, table);
        Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), serializedTablesInfo);
        MetaDataWriter.addTableInfo(tableName, clusteringKey, colNameType, colNameMin, colNameMax);
    }
}
