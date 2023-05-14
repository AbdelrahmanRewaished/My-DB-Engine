package engine.elements;

import utilities.serialization.Deserializer;

import java.io.Serial;
import java.util.Arrays;
import java.util.Hashtable;
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
    public void addRecord(int index, String clusteringKey, Record record) {
        add(index, record);
        adjustCurrentMinAndMaxClusteringValue(clusteringKey);
        pageMetaInfo.incrementCurrentNumberOfRecords();
    }
    public Record removeRecord(int index, String clusteringKey) {
        Record removed = remove(index);
        pageMetaInfo.decrementCurrentNumberOfRecords();
        if(! isEmpty()) {
            adjustCurrentMinAndMaxClusteringValue(clusteringKey);
        }
        return removed;
    }
    public int findRecordIndex(String clusteringKey, Comparable keyValue) {
        int left = 0, right = size() - 1;
        while(left <= right) {
            int mid = (left + right) / 2;
            Hashtable<String, Object> currentRecord = get(mid);
            Comparable currentClusteringKey = (Comparable) currentRecord.get(clusteringKey);
            if(currentClusteringKey.compareTo(keyValue) > 0) {
                right = mid - 1;
            }
            else if(currentClusteringKey.compareTo(keyValue) < 0) {
                left = mid + 1;
            }
            else {
                return mid;
            }
        }
        return -1;
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
