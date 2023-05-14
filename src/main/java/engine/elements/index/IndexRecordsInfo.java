package engine.elements.index;

import engine.elements.Record;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.metadata.MetadataReader;

import java.util.Vector;

public class IndexRecordsInfo extends Vector<Record> {
    public boolean contains(Table table, Record upComingRecord) {
        if(this.isEmpty()) {
            return false;
        }
        Record storedRecord = this.get(0);
        return storedRecord.hasMatchingNonNullValues(table.getClusteringKey(), upComingRecord);
    }

    public synchronized Record getMaxValuesRecord(Table table) throws DBAppException {
        int maxElementsInARecord = MetadataReader.getTableMetadataRecords(table.getName()).size();
        int currentMax = 0;
        Record maxValuesRecord = null;
        for(Record record: this) {
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
