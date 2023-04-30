package engine.elements;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import static engine.DBApp.getFileExtension;

public class Table implements Serializable {
    private final String folderLocation;
    private final List<PageMetaInfo> pagesInfo;
    private final String name;
    private final String clusteringKey;
    @Serial
    private static final long serialVersionUID = 123456789L;
    public Table(String name, String folderLocation, String clusteringKey) {
        this.name = name;
        this.folderLocation = folderLocation;
        this.clusteringKey = clusteringKey;
        pagesInfo = new Vector<>();
    }

    public String getFolderLocation() {
        return folderLocation;
    }

    public List<PageMetaInfo> getPagesInfo() {
        return pagesInfo;
    }

    public String getName() {
        return name;
    }
    public void addPageInfo(PageMetaInfo pageMetaInfo) {
        pagesInfo.add(pageMetaInfo);
    }
    public void removePageInfo(int index) {
        pagesInfo.remove(index);
    }

    public String getClusteringKey() {
        return clusteringKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;
        Table table = (Table) o;
        return getName().equals(table.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    public int findPageIndexToLookIn(Object element) {
        int left = 0, right = pagesInfo.size() - 1;
        while(left <= right) {
            int mid = (left + right) / 2;
            PageMetaInfo currentPage = pagesInfo.get(mid);
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
    public String getMaxPageLocation() {
        return pagesInfo.get(pagesInfo.size() - 1).getLocation();
    }

    @Override
    public String toString() {
        return "Table{" +
                "folderLocation='" + folderLocation + '\'' +
                ", pagesInfo=" + pagesInfo +
                ", name='" + name + '\'' +
                ", clusteringKey='" + clusteringKey + '\'' +
                '}';
    }
}
