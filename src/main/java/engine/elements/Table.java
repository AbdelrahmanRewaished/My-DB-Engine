package engine.elements;

import engine.elements.index.IndexMetaInfo;
import engine.elements.index.IndexRecordInfo;
import engine.elements.index.Octree;
import utilities.serialization.Serializer;

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
    private final List<IndexMetaInfo> indicesInfo;
    @Serial
    private static final long serialVersionUID = 123456789L;
    public Table(String name, String folderLocation, String clusteringKey) {
        this.name = name;
        this.folderLocation = folderLocation;
        this.clusteringKey = clusteringKey;
        pagesInfo = new Vector<>();
        indicesInfo = new Vector<>();
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
    public void addIndexInfo(IndexMetaInfo indexMetaInfo) {indicesInfo.add(indexMetaInfo);}
    public void removePageInfo(int index) {
        pagesInfo.remove(index);
    }

    public String getClusteringKey() {
        return clusteringKey;
    }

    public List<IndexMetaInfo> getIndicesInfo() {
        return indicesInfo;
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
    private String getMaxPageLocation() {
        return pagesInfo.get(pagesInfo.size() - 1).getLocation();
    }
    private String getMaxIndexLocation(){return indicesInfo.get(indicesInfo.size() - 1).getIndexFileLocation();}
    public String getNextPageFileLocation() {
        if(pagesInfo.isEmpty()) {
            return "/0" + getFileExtension();
        }
        String[] splitter = getMaxPageLocation().split("/");
        int lastPageNumber = Integer.parseInt(splitter[splitter.length - 1].split(getFileExtension())[0]);
        return "/" + (lastPageNumber + 1) + getFileExtension();
    }
    public String getNextIndexFileLocation() {
        if(indicesInfo.isEmpty()) {
            return "/0" + getFileExtension();
        }
        String[] splitter = getMaxIndexLocation().split("/");
        int lastIndexNumber = Integer.parseInt(splitter[splitter.length - 1].split(getFileExtension())[0]);
        return "/" + (lastIndexNumber + 1) + getFileExtension();
    }
    public boolean isHavingIndices() {
        return ! indicesInfo.isEmpty();
    }
    public void updateAllRecordsPageNumberInIndices(int startingOverflownPageIndex) {
        for(IndexMetaInfo indexMetaInfo: indicesInfo) {
            Octree index = Octree.deserializeIndex(indexMetaInfo);
            index.updateAllRecordsPageNumber(startingOverflownPageIndex);
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
    }
    public void updateRecordPageNumberInIndices(Record nextRecord, int nextPageIndex) {
        for(IndexMetaInfo indexMetaInfo: indicesInfo) {
            Octree index =  Octree.deserializeIndex(indexMetaInfo);
            index.updateRecordPageNumber(clusteringKey, nextRecord, nextPageIndex);
            Serializer.serialize(indexMetaInfo.getIndexFileLocation(), index);
        }
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
