package engine.operations.update;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.serialization.Serializer;

public class UpdateAllRecords implements UpdateStrategy{

    final UpdateTableParams params;
    final Table table;

    public UpdateAllRecords(UpdateTableParams up, Table table) {
        this.params = up;
        this.table = table;
    }

    @Override
    public int updateTable() throws DBAppException {
        int totalRecordsUpdated = 0;
        for(PageMetaInfo pageMetaInfo : table.getPagesInfo()) {
            Page currentPage = Page.deserializePage(pageMetaInfo);
            for (Record record : currentPage) {
                record.updateValues(params.getColNameValue());
            }
            totalRecordsUpdated += currentPage.size();
            Serializer.serialize(pageMetaInfo.getLocation(), currentPage);
        }
        return totalRecordsUpdated;
    }
}
