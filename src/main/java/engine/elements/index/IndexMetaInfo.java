package engine.elements.index;

import java.io.Serializable;
import java.util.Hashtable;

public class IndexMetaInfo implements Serializable {
    private final String name;
    private final String[] indexedColumnNames;
    private final String indexFileLocation;

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
    public static String getType() {
        return "Octree";
    }
}
