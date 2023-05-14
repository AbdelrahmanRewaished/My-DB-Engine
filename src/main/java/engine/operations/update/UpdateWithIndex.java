package engine.operations.update;

import engine.elements.index.IndexMetaInfo;
import engine.elements.index.Octree;
import engine.exceptions.DBAppException;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.metadata.MetadataReader;
import utilities.serialization.Serializer;

public class UpdateWithIndex extends Update{

    public UpdateWithIndex(UpdateTableParams params) {
        super(params);
    }
    @Override
    public synchronized int updateTable() throws DBAppException {
        int updatedRecords = super.updateTable();
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            if(! indexMetaInfo.isContainingIndexedColumns(params.getColNameValue())) {
                continue;
            }
            Octree index = Octree.deserializeIndex(indexMetaInfo);
            if(strategy instanceof UpdateAllRecords) {
                index.refreshAllRecords(table, params.getColNameValue());
            }
            else {
                String clusteringKeyType = MetadataReader.getTableColumnMetadataRecord(params.getTableName(), table.getClusteringKey()).getColumnType();
                index.refreshRecordWithPrimaryKey(table, DatabaseTypesHandler.getObject(params.getClusteringKeyValue(), clusteringKeyType), params.getColNameValue());
            }
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
        return updatedRecords;
    }
}
