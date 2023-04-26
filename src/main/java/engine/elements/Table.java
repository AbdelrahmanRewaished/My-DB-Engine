package engine.elements;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

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
