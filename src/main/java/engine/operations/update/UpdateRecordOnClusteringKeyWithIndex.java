package engine.operations.update;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.elements.index.IndexRecordInfo;
import engine.elements.index.Octree;
import engine.exceptions.DBAppException;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.metadata.MetadataReader;
import utilities.serialization.Serializer;

import java.util.Hashtable;

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
        int pageInfoIndex = table.findPageIndexToLookIn(clusteringKeyValue);
        if(pageInfoIndex == -1) {
            return 0;
        }
        Page page = Page.deserializePage(table.getPagesInfo().get(pageInfoIndex));
        int recordIndexInPage = page.findRecordIndex(table.getClusteringKey(), clusteringKeyValue);
        if(recordIndexInPage == -1) {
            return 0;
        }
        Hashtable<String, Object> recordToUpdate = (Hashtable<String, Object>) page.get(recordIndexInPage).clone();
        new UpdateRecordOnClusteringKey(params, table).updateRecordImmediately(page, recordIndexInPage);
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            if(! indexMetaInfo.isContainingIndexedColumns(params.getColNameValue())) {
                continue;
            }
            Octree index = Octree.deserializeIndex(indexMetaInfo);
            index.deleteMatchingRecords(table, recordToUpdate);
            index.insert(table, new IndexRecordInfo(clusteringKeyValue, pageInfoIndex));
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
        return 1;
    }
}
