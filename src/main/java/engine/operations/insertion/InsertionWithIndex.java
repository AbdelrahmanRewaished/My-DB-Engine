package engine.operations.insertion;

import engine.elements.index.IndexMetaInfo;
import engine.elements.index.IndexRecordInfo;
import engine.elements.index.Octree;
import engine.exceptions.DBAppException;
import utilities.serialization.Serializer;

public class InsertionWithIndex extends Insertion{
    public InsertionWithIndex(InsertIntoTableParams params) {
        super(params);
    }

    public synchronized IndexRecordInfo insertIntoTable() throws DBAppException {
        IndexRecordInfo recordInfoToBeInserted = super.insertIntoTable();
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            Octree index = Octree.deserializeIndex(indexMetaInfo);
            index.insert(table, recordInfoToBeInserted);
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
        return recordInfoToBeInserted;
    }
}
