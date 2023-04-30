package engine.operations.update;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.datatypes.DatabaseTypesHandler;
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
        page.updateRecord(requiredRecordIndex, up.getColNameValue());
        Serializer.serialize(pageMetaInfo.getLocation(), page);
        return 1;
    }
}
