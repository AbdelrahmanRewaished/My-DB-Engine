package engine.elements;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class Table implements Serializable {
    private final String folderLocation;
    private final List<PageInfo> pagesInfo;
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

    public List<PageInfo> getPagesInfo() {
        return pagesInfo;
    }

    public String getName() {
        return name;
    }
    public void addPageInfo(PageInfo pageInfo) {
        pagesInfo.add(pageInfo);
    }
    public void removePageInfo(int index) {
        pagesInfo.remove(index);
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
