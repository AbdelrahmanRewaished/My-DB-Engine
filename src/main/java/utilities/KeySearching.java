package utilities;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Table;

import java.util.Hashtable;
import java.util.List;

public class KeySearching {
    private KeySearching(){}


    public static int findRecordIndex(Page page, String clusteringKey, Comparable keyValue) {
        int left = 0, right = page.size() - 1;
        while(left <= right) {
            int mid = (left + right) / 2;
            Hashtable<String, Object> currentRecord = page.get(mid);
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
    public static int findPageIndexToLookIn(Table table, Object element) {
        List<PageMetaInfo> pages = table.getPagesInfo();
        int left = 0, right = pages.size() - 1;
        while(left <= right) {
            int mid = (left + right) / 2;
            PageMetaInfo currentPage = pages.get(mid);
            Comparable min = currentPage.getMinimumContainedKey();
            Comparable max = currentPage.getMaximumContainedKey();
            if(min.compareTo(element) > 0) {
                right = mid - 1;
            }
            else if(max.compareTo(element) < 0) {
                left = mid + 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }
}
