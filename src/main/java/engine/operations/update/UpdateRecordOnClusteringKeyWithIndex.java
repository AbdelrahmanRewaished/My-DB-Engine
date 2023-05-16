package engine.operations.update;

import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.elements.index.Octree;
import engine.exceptions.DBAppException;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.metadata.MetadataReader;
import utilities.serialization.Serializer;

public class UpdateRecordOnClusteringKeyWithIndex implements UpdateStrategy{

    private final UpdateTableParams params;
    private final Table table;
    public UpdateRecordOnClusteringKeyWithIndex(UpdateTableParams params, Table table) {
        this.params = params;
        this.table = table;
    }
    @Override
    public synchronized int updateTable() throws DBAppException {
        String clusteringKeyType = MetadataReader.getTableColumnMetadataRecord(table.getName(), table.getClusteringKey()).getColumnType();
        Comparable clusteringKeyValue = DatabaseTypesHandler.getObject(params.getClusteringKeyValue(), clusteringKeyType);
        int updatedRecords = 0;
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            if(! indexMetaInfo.isContainingIndexedColumns(params.getColNameValue())) {
                continue;
            }
            Octree index = Octree.deserializeIndex(indexMetaInfo);
            updatedRecords = index.updateRecordWithPrimaryKey(table, clusteringKeyValue, params.getColNameValue());
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
        return updatedRecords;
    }
}
