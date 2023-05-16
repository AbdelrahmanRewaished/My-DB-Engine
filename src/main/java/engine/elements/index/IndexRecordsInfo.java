package engine.elements.index;

import engine.elements.Record;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.metadata.MetadataReader;

import java.util.Vector;

public class IndexRecordsInfo extends Vector<IndexRecordInfo> {
    public boolean contains(Table table, IndexRecordInfo upComingRecordInfo) {
        if(this.isEmpty()) {
            return false;
        }
        IndexRecordInfo storedRecordInfo = this.get(0);

        return Record.getRecord(table, storedRecordInfo).hasMatchingNonNullValues(table.getClusteringKey(), Record.getRecord(table, upComingRecordInfo));
    }

    public synchronized Record getMaxValuesRecord(Table table) throws DBAppException {
        int maxElementsInARecord = MetadataReader.getTableMetadataRecords(table.getName()).size();
        int currentMax = 0;
        Record maxValuesRecord = null;
        for(IndexRecordInfo recordInfo: this) {
            Record record = Record.getRecord(table, recordInfo);
            int recordNonNullValuesCount = record.countNonNullValues();
            if(currentMax > recordNonNullValuesCount) {
                continue;
            }
            currentMax = recordNonNullValuesCount;
            maxValuesRecord = record;
            if(currentMax == maxElementsInARecord) {
                break;
            }
        }
        return maxValuesRecord;
    }
}
