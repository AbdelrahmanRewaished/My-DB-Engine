package engine.operations.creation;

import engine.DBApp;
import engine.elements.IndexMetaInfo;
import engine.elements.Page;
import engine.elements.Table;
import engine.elements.index.Octree;
import engine.exceptions.DBAppException;
import engine.operations.selection.TableRecordInfo;
import utilities.FileHandler;
import utilities.metadata.MetadataReader;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;
import utilities.validation.IndexCreationValidator;

public class IndexCreation {
    private final CreateIndexParams params;

    public IndexCreation(CreateIndexParams params) {
       this.params = params;
    }

    private void fillIndexWithTableRecords(Table table, Octree index) {
        for(int i = 0 ; i < table.getPagesInfo().size(); i++) {
            Page page = Page.deserializePage(table.getPagesInfo().get(i));
            for(int j = 0; j < page.size(); j++) {
                index.insert(table, new TableRecordInfo(i, j));
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
        IndexMetaInfo indexMetaInfo = new IndexMetaInfo(params.getTableName(), params.getIndexedColumns(), tableIndicesFolderLocation + table.getNextIndexFileLocation());
        Octree index = new Octree(indexMetaInfo, MetadataReader.getTableColumnsBoundaries(params.getTableName(), params.getIndexedColumns()));
        fillIndexWithTableRecords(table, index);
        storeNewTableIndex(table, index);
    }
}
