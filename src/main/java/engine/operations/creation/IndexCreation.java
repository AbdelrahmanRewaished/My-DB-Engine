package engine.operations.creation;

import engine.DBApp;
import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.elements.index.IndexRecordInfo;
import engine.elements.index.Octree;
import engine.exceptions.DBAppException;
import utilities.FileHandler;
import utilities.metadata.MetadataReader;
import utilities.metadata.MetadataWriter;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;
import utilities.validation.IndexCreationValidator;

public class IndexCreation {
    private final CreateIndexParams params;

    public IndexCreation(CreateIndexParams params) {
       this.params = params;
    }

    private void fillIndexWithTableRecords(Table table, Octree index) throws DBAppException {
        for(int i = 0; i < table.getPagesInfo().size(); i++) {
            PageMetaInfo pageMetaInfo = table.getPagesInfo().get(i);
            Page page = Page.deserializePage(pageMetaInfo);
            for (Record record : page) {
                index.insert(table, new IndexRecordInfo((Comparable) record.get(table.getClusteringKey()), i));
            }
        }
    }
    private void storeNewTableIndex(Table table, Octree index) {
         if(! FileHandler.isFileExisting(DBApp.getIndexFolderLocationOfTable(table.getName()))) {
            FileHandler.createFolder(DBApp.getIndexFolderLocationOfTable(table.getName()));
        }
        table.addIndexInfo(index.getIndexMetaInfo());
        Serializer.serialize(index.getIndexMetaInfo().getIndexFileLocation(), index);
        Serializer.serialize(DBApp.getTableInfoFileLocation(table.getName()), table);
    }
    public void createIndex() throws DBAppException {
        IndexCreationValidator.validateElementsExistence(params);
        Table table = (Table) Deserializer.deserialize(DBApp.getTableInfoFileLocation(params.getTableName()));
        IndexCreationValidator.validateIndexedColumnsExistence(table, params.getIndexedColumns());
        String tableIndicesFolderLocation = DBApp.getIndexFolderLocationOfTable(table.getName());
        IndexMetaInfo indexMetaInfo = new IndexMetaInfo(params.getIndexName(), params.getIndexedColumns(), tableIndicesFolderLocation + table.getNextIndexFileLocation());
        Octree index = new Octree(indexMetaInfo, MetadataReader.getTableColumnsBoundaries(params.getTableName(), params.getIndexedColumns()));
        MetadataWriter.setColumnsToBeIndexed(params);
        fillIndexWithTableRecords(table, index);
        storeNewTableIndex(table, index);

    }
}
