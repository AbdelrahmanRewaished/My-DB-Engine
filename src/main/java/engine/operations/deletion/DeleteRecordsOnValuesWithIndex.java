package engine.operations.deletion;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.elements.index.Octree;
import utilities.serialization.Serializer;

import java.util.Hashtable;

public class DeleteRecordsOnValuesWithIndex implements DeletionStrategy{
    private final DeleteFromTableParams params;
    private final Table table;

    public DeleteRecordsOnValuesWithIndex(DeleteFromTableParams params, Table table) {
        this.params = params;
        this.table = table;
    }

    public synchronized int deleteFromTable() {
        int deletedRecords = 0;
        Hashtable<String, Object> colNameValue = params.getColNameValue();
        if(params.getColNameValue().containsKey(table.getClusteringKey())) {
            Object clusteringKeyValue = colNameValue.get(table.getClusteringKey());
            int requiredPageIndex = table.findPageIndexToLookIn(clusteringKeyValue);
            if(requiredPageIndex == -1) {
                return 0;
            }
            PageMetaInfo pageMetaInfo = table.getPagesInfo().get(requiredPageIndex);
            Page page = Page.deserializePage(pageMetaInfo);
            int requiredRecordIndex = page.findRecordIndex(table.getClusteringKey(), (Comparable) clusteringKeyValue);
            if(requiredRecordIndex == -1) {
                return 0;
            }
            if(! page.get(requiredRecordIndex).hasMatchingValues(colNameValue)) {
                return 0;
            }
            colNameValue = page.get(requiredPageIndex);
        }
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            Octree index = Octree.deserializeIndex(indexMetaInfo);
            deletedRecords = index.deleteMatchingRecords(table, colNameValue, true);
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
        return deletedRecords;
    }
}
