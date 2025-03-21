package engine.operations.deletion;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;
import utilities.FileHandler;
import utilities.serialization.Serializer;

public class DeleteRecordOnClusteringKey implements DeletionStrategy {

    private final DeleteFromTableParams dp;
    private final Table table;

    public DeleteRecordOnClusteringKey(DeleteFromTableParams dp, Table table) {
        this.dp = dp;
        this.table = table;
    }

    @Override
    public int deleteFromTable() {
        // Binary Search over the Pages
        String clusteringKey = table.getClusteringKey();
        int requiredPageIndexToDeleteFrom = table.findPageIndexToLookIn(dp.getColNameValue().get(clusteringKey));
        if(requiredPageIndexToDeleteFrom == -1) {
            return 0;
        }
        // Binary search over records in the Page found
        PageMetaInfo pageMetaInfo = table.getPagesInfo().get(requiredPageIndexToDeleteFrom);
        Page page = Page.deserializePage(pageMetaInfo);
        int requiredRecordIndex = page.findRecordIndex(clusteringKey, (Comparable) dp.getColNameValue().get(clusteringKey));
        if(requiredRecordIndex == -1) {
            return 0;
        }
        Record record = page.get(requiredRecordIndex);
        // Check if the all other values in colNameValue exist in the record found
        // otherwise return 0
        if(! record.hasMatchingValues(dp.getColNameValue())) {
            return 0;
        }
        page.removeRecord(requiredRecordIndex, clusteringKey);
        if(! page.isEmpty()) {
            Serializer.serialize(page.getPageInfo().getLocation(), page);
        }
        else {
            table.removePageInfo(requiredPageIndexToDeleteFrom);
            FileHandler.deleteFile(pageMetaInfo.getLocation());
        }
        return 1;
    }
}
