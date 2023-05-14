package engine.operations.insertion;

import engine.elements.index.IndexMetaInfo;
import engine.elements.index.Octree;
import engine.exceptions.DBAppException;
import utilities.serialization.Serializer;

public class InsertionWithIndex extends Insertion{
    public InsertionWithIndex(InsertIntoTableParams params) {
        super(params);
    }

    public synchronized void insertIntoTable() throws DBAppException {
        super.insertIntoTable();
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            Octree index = Octree.deserializeIndex(indexMetaInfo);
            index.insert(table, params.getRecord());
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
    }
}
