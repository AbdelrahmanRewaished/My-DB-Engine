package engine.operations.deletion;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;
import utilities.FileHandler;
import utilities.serialization.Serializer;

public class DeleteAllMatchingRecords implements DeletionStrategy{
    private DeleteFromTableParams dp;
    Table table;

    public DeleteAllMatchingRecords(DeleteFromTableParams dp, Table table) {
        this.dp = dp;
        this.table = table;
    }

    @Override
    public int deleteFromTable() {
        int recordsDeleted = 0;
        for(int i = 0; i < table.getPagesInfo().size();) {
            PageMetaInfo currentPageMetaInfo = table.getPagesInfo().get(i);
            Page currentPage = Page.deserializePage(currentPageMetaInfo);
            currentPage.setPageInfo(currentPageMetaInfo);
            for(int j = 0; j < currentPage.size();) {
                Record currentRecord = currentPage.get(j);
                boolean toDeleteRecord = currentRecord.hasMatchingValues(dp.getColNameValue());
                if(toDeleteRecord) {
                    currentPage.removeRecord(j, table.getClusteringKey());
                    recordsDeleted++;
                }
                else {
                    j++;
                }
            }
            if(currentPage.isEmpty()) {
                table.removePageInfo(i);
                FileHandler.deleteFile(currentPageMetaInfo.getLocation());
                Deletion.updateRecordsPageNumbersBelowDeletedPage(table, i);
            }
            else {
                Serializer.serialize(currentPageMetaInfo.getLocation(), currentPage);
                i++;
            }
        }
        return recordsDeleted;
    }
}
