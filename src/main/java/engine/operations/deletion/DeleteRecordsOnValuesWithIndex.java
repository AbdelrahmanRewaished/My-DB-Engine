package engine.operations.deletion;

import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.elements.index.Octree;
import utilities.serialization.Serializer;

public class DeleteRecordsOnValuesWithIndex implements DeletionStrategy{
    private final DeleteFromTableParams params;
    private final Table table;

    public DeleteRecordsOnValuesWithIndex(DeleteFromTableParams params, Table table) {
        this.params = params;
        this.table = table;
    }

    public synchronized int deleteFromTable() {
        int deletedRecords = -1;
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            Octree index = Octree.deserializeIndex(indexMetaInfo);
            deletedRecords = index.deleteMatchingRecords(table, params.getColNameValue(), true).size();
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
        return deletedRecords;
    }
}
