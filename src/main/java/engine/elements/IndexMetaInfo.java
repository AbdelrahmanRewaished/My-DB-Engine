package engine.elements;

public class IndexMetaInfo {
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
}
