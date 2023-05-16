package engine.operations.update;

import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.elements.index.Octree;
import engine.exceptions.DBAppException;
import utilities.serialization.Serializer;

public class UpdateAllRecordsWithIndex extends UpdateAllRecords {
    public UpdateAllRecordsWithIndex(UpdateTableParams params, Table table) {
        super(params, table);
    }

    public int updateTable() throws DBAppException {
        int updatedRecords = super.updateTable();
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            if(! indexMetaInfo.isContainingIndexedColumns(params.getColNameValue())) {
                continue;
            }
            Octree index = Octree.deserializeIndex(indexMetaInfo);
            updatedRecords = index.updateAllRecords(table);
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
        return updatedRecords;
    }
}
