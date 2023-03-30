package engine.elements;

import utilities.serialization.Deserializer;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

public class Page extends Vector<Hashtable<String, Object>> {
    private PageInfo pageInfo;
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

    private void adjustCurrentMaxClusteringValue(String clusteringKey, Hashtable<String, Object> record) {
        Object clusteringValue = record.get(clusteringKey);
        Comparable currentMaximum = pageInfo.getMaximumContainedKey();
        Comparable newMaximum = currentMaximum != null && currentMaximum.compareTo(clusteringValue) > 0? currentMaximum: (Comparable) record.get(clusteringKey);
        pageInfo.setMaximumContainedKey(newMaximum);
    }
    private void adjustCurrentMinClusteringValue(String clusteringKey, Hashtable<String, Object> record) {
        Object clusteringValue = record.get(clusteringKey);
        Comparable currentMinimum = pageInfo.getMinimumContainedKey();
        Comparable newMinimum = currentMinimum != null && currentMinimum.compareTo(clusteringValue) < 0? currentMinimum: (Comparable) record.get(clusteringKey);
        pageInfo.setMinimumContainedKey(newMinimum);
    }
    public boolean addRecord(int index, String clusteringKey, Hashtable<String, Object> record) {
        boolean isFull = pageInfo.isFull();
        add(index, record);
        adjustCurrentMaxClusteringValue(clusteringKey, record);
        adjustCurrentMinClusteringValue(clusteringKey, record);
        pageInfo.incrementCurrentNumberOfRecords();
        return ! isFull;
    }
    public Hashtable<String, Object> removeLast(String clusteringKey) {
        Hashtable<String, Object> removed = remove(size() - 1);
        pageInfo.decrementCurrentNumberOfRecords();
        if(! isEmpty()) {
            pageInfo.setMaximumContainedKey((Comparable) get(size() - 1).get(clusteringKey));
        }
        return removed;
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
