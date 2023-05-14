package engine.operations.deletion;

import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.elements.index.Octree;
import utilities.serialization.Serializer;

public class DeleteAllRecordsWithIndex extends DeleteAllMatchingRecords{
    public DeleteAllRecordsWithIndex(DeleteFromTableParams dp, Table table) {
        super(dp, table);
    }

    @Override
    public int deleteFromTable() {
        int deletedRecords = super.deleteFromTable();
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            Octree index = Octree.deserializeIndex(indexMetaInfo);
            index.deleteAllEntries();
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
        return deletedRecords;
    }
}
