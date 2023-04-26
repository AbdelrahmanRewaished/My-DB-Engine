package engine.elements;

import utilities.serialization.Deserializer;

import java.io.Serial;
import java.util.Arrays;
import java.util.Vector;

public class Page extends Vector<Record> {
    private PageMetaInfo pageMetaInfo;
    @Serial
    private static final long serialVersionUID = 4540224892639226411L;

    public Page(PageMetaInfo pageMetaInfo) {
        super();
        this.pageMetaInfo = pageMetaInfo;
    }

    public PageMetaInfo getPageInfo() {
        return pageMetaInfo;
    }

    public void setPageInfo(PageMetaInfo pageMetaInfo) {
        this.pageMetaInfo = pageMetaInfo;
    }
    private void adjustCurrentMinAndMaxClusteringValue(String clusteringKey) {
        pageMetaInfo.setMinimumContainedKey((Comparable) get(0).get(clusteringKey));
        pageMetaInfo.setMaximumContainedKey((Comparable) get(size() - 1).get(clusteringKey));
    }
    public boolean addRecord(int index, String clusteringKey, Record record) {
        boolean isFull = pageMetaInfo.isFull();
        add(index, record);
        adjustCurrentMinAndMaxClusteringValue(clusteringKey);
        pageMetaInfo.incrementCurrentNumberOfRecords();
        return ! isFull;
    }
    public Record removeRecord(int index, String clusteringKey) {
        Record removed = remove(index);
        pageMetaInfo.decrementCurrentNumberOfRecords();
        if(! isEmpty()) {
            adjustCurrentMinAndMaxClusteringValue(clusteringKey);
        }
        return removed;
    }
    public void updateRecord(int index, Record colNameNewValue) {
        Record requiredRecord = get(index);
        for(String columnName: colNameNewValue.keySet()) {
            Object newValue = colNameNewValue.get(columnName);
            requiredRecord.put(columnName, newValue);
        }
    }
    public static Page deserializePage(PageMetaInfo pageMetaInfo) {
        Page page = (Page) Deserializer.deserialize(pageMetaInfo.getLocation());
        page.setPageInfo(pageMetaInfo);
        return page;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageInfo=" + pageMetaInfo +
                ", elementData=" + Arrays.toString(elementData) +
                '}';
    }
}
