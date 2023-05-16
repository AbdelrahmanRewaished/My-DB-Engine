package engine.operations.deletion;

import engine.elements.PageMetaInfo;
import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.elements.index.Octree;
import utilities.FileHandler;
import utilities.serialization.Serializer;

public class DeleteAllRecordsWithIndex implements DeletionStrategy{
    private DeleteFromTableParams params;
    private Table table;
    public DeleteAllRecordsWithIndex(DeleteFromTableParams params, Table table) {
        this.params = params;
        this.table = table;
    }
    private int deleteAllTableData() {
        int deletedRecords = 0;
        for(PageMetaInfo pageMetaInfo: table.getPagesInfo()) {
            deletedRecords += pageMetaInfo.getCurrentNumberOfRecords();
            FileHandler.deleteFile(pageMetaInfo.getLocation());
        }
        table.getPagesInfo().clear();
        return deletedRecords;
    }

    @Override
    public int deleteFromTable() {
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            Octree index = Octree.deserializeIndex(indexMetaInfo);
            index.deleteAllEntries();
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
        return deleteAllTableData();
    }
}
