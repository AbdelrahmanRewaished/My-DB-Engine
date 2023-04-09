package engine.elements;

import utilities.serialization.Deserializer;

import java.io.Serial;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

public class Page extends Vector<Record> {
    private PageInfo pageInfo;
    @Serial
    private static final long serialVersionUID = 4540224892639226411L;

    public Page(PageInfo pageInfo) {
        super();
        this.pageInfo = pageInfo;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
    private void adjustCurrentMinAndMaxClusteringValue(String clusteringKey) {
        pageInfo.setMinimumContainedKey((Comparable) get(0).get(clusteringKey));
        pageInfo.setMaximumContainedKey((Comparable) get(size() - 1).get(clusteringKey));
    }
    public boolean addRecord(int index, String clusteringKey, Record record) {
        boolean isFull = pageInfo.isFull();
        add(index, record);
        adjustCurrentMinAndMaxClusteringValue(clusteringKey);
        pageInfo.incrementCurrentNumberOfRecords();
        return ! isFull;
    }
    public Record removeRecord(int index, String clusteringKey) {
        Record removed = remove(index);
        pageInfo.decrementCurrentNumberOfRecords();
        if(! isEmpty()) {
            adjustCurrentMinAndMaxClusteringValue(clusteringKey);
        }
        return removed;
    }
    public void updateRecord(int index, Record colNameNewValue) {
        Record requiredRecord = get(index);
        for(String columnName: colNameNewValue.keySet()) {
            Object newValue = colNameNewValue.get(columnName);
            requiredRecord.putValue(columnName, newValue);
        }
    }
    public static Page deserializePage(PageInfo pageInfo) {
        Page page = (Page) Deserializer.deserialize(pageInfo.getLocation());
        page.setPageInfo(pageInfo);
        return page;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageInfo=" + pageInfo +
                ", elementData=" + Arrays.toString(elementData) +
                '}';
    }
}
