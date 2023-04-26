package engine.operations.update;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Table;
import utilities.serialization.Serializer;

public class UpdateAllRecords implements UpdateStrategy{

    private UpdateTableParams up;
    private Table table;

    public UpdateAllRecords(UpdateTableParams up, Table table) {
        this.up = up;
        this.table = table;
    }

    @Override
    public int updateTable() {
        int totalRecordsUpdated = 0;
        for(PageMetaInfo pageMetaInfo : table.getPagesInfo()) {
            Page currentPage = Page.deserializePage(pageMetaInfo);
            for(int i = 0; i < currentPage.size(); i++) {
                currentPage.updateRecord(i, up.getColNameValue());
            }
            totalRecordsUpdated += currentPage.size();
            Serializer.serialize(pageMetaInfo.getLocation(), currentPage);
        }
        return totalRecordsUpdated;
    }
}
