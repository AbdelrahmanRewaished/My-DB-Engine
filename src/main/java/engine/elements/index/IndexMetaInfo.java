package engine.elements.index;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Objects;

public class IndexMetaInfo implements Serializable {
    private final String name;
    private final String[] indexedColumnNames;
    private final String indexFileLocation;

    @Serial
    private static final long serialVersionUID = 5523063582825930645L;

    public IndexMetaInfo(String name, String[] columnNames, String indexFileLocation) {
        this.name = name;
        indexedColumnNames = columnNames;
        this.indexFileLocation = indexFileLocation;
    }

    public String getName() {
        return name;
    }

    public String[] getIndexedColumns() {
        return indexedColumnNames;
    }

    public String getIndexFileLocation() {
        return indexFileLocation;
    }

    public boolean isContainingIndexColumn(String columnName) {
        for(String indexedColumnName: indexedColumnNames) {
            if(indexedColumnName.equals(columnName)) {
                return true;
            }
        }
        return false;
    }
    public boolean isContainingIndexedColumns(Hashtable<String, Object> colNameValue) {
        for(String columnName: colNameValue.keySet()){
            if(isContainingIndexColumn(columnName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndexMetaInfo that)) return false;
        return getIndexFileLocation().equals(that.getIndexFileLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndexFileLocation());
    }

    public static String getType() {
        return "Octree";
    }

    @Override
    public String toString() {
        return "IndexMetaInfo{" +
                "name='" + name + '\'' +
                ", indexedColumnNames=" + Arrays.toString(indexedColumnNames) +
                ", indexFileLocation='" + indexFileLocation + '\'' +
                '}';
    }
}
