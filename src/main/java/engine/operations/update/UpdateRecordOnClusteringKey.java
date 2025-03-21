package engine.operations.update;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.datatypes.DBAppNull;
import utilities.metadata.MetadataReader;
import utilities.serialization.Serializer;

public class UpdateRecordOnClusteringKey implements UpdateStrategy{
    private final UpdateTableParams up;
    private final Table table;

    public UpdateRecordOnClusteringKey(UpdateTableParams up, Table table) {
        this.up = up;
        this.table = table;
    }
    @Override
    public int updateTable() throws DBAppException {
        if(up.getClusteringKeyValue().equals(DBAppNull.getValue())) {
            return 0;
        }
        String clusteringKeyType = MetadataReader.getTableColumnMetadataRecord(table.getName(), table.getClusteringKey()).getColumnType();
        Comparable keyObjectValue = DatabaseTypesHandler.getObject(up.getClusteringKeyValue(), clusteringKeyType);
        int pageContainingKeyIndex = table.findPageIndexToLookIn(keyObjectValue);
        if(pageContainingKeyIndex == -1) {
            return 0;
        }
        PageMetaInfo pageMetaInfo = table.getPagesInfo().get(pageContainingKeyIndex);
        Page page = Page.deserializePage(pageMetaInfo);
        int requiredRecordIndex = page.findRecordIndex(table.getClusteringKey(), keyObjectValue);
        if(requiredRecordIndex == -1) {
            return 0;
        }
        Record recordToUpdate = page.get(requiredRecordIndex);
        recordToUpdate.updateValues(up.getColNameValue());
        Serializer.serialize(pageMetaInfo.getLocation(), page);
        return 1;
    }
    void updateRecordImmediately(Page page, int recordIndex) {
        Record recordToUpdate = page.get(recordIndex);
        recordToUpdate.updateValues(up.getColNameValue());
        Serializer.serialize(page.getPageInfo().getLocation(), page);
    }
}
