package engine.elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable {
    private final String folderLocation;
    private List<PageInfo> pagesInfo;
    private final String name;
    private String clusteringKey;
    private static final long serialVersionUID = 123456789L;
    public Table(String name, String folderLocation, String clusteringKey) {
        this.name = name;
        this.folderLocation = folderLocation;
        this.clusteringKey = clusteringKey;
        pagesInfo = new ArrayList<>();
    }

    public String getFolderLocation() {
        return folderLocation;
    }

    public List<PageInfo> getPagesInfo() {
        return pagesInfo;
    }

    public String getName() {
        return name;
    }
    public void addPageInfo(PageInfo pageInfo) {
        pagesInfo.add(pageInfo);
    }
    public PageInfo removePageInfo(int index) {
        return pagesInfo.remove(index);
    }

    public String getClusteringKey() {
        return clusteringKey;
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
