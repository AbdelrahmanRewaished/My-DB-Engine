package engine.operations.selection;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SelectIteratorReferences {
    static class TableRecordInfo {
        private int pageInfoIndex;
        private int recordIndexInPage;

        private boolean initialized;
        public TableRecordInfo(int currentPageInfoIndex, int currentRecordIndex) {
            this.pageInfoIndex = currentPageInfoIndex;
            this.recordIndexInPage = currentRecordIndex;
            this.initialized = false;
        }

        public int getPageInfoIndex() {
            return pageInfoIndex;
        }

        public int getRecordIndexInPage() {
            return recordIndexInPage;
        }

        public boolean isInitialized() {
            return initialized;
        }

    }
    private Hashtable<String, TableRecordInfo> currentTableDataInfo;
    private final List<String> combinedTables;
    private int currentTableIndexToIterateOn;

    public SelectIteratorReferences(SelectionInitializationStrategy strategy) {
        this.currentTableDataInfo = strategy.getInitialTablesDataInfo();
        this.combinedTables = new ArrayList<>();
        this.currentTableIndexToIterateOn = 0;
    }

    public void setCurrentTableDataInfo(Hashtable<String, TableRecordInfo> currentTableDataInfo) {
        this.currentTableDataInfo = currentTableDataInfo;
    }

    public Hashtable<String, TableRecordInfo> getCurrentTableDataInfo() {
        return currentTableDataInfo;
    }

    public List<String> getCombinedTables() {
        return combinedTables;
    }

    public void setCurrentTableIndexToIterateOn(int currentTableIndexToIterateOn) {
        this.currentTableIndexToIterateOn = currentTableIndexToIterateOn;
    }

    public void saveNewTableDataInfo(String tableName, TableRecordInfo info) {
        currentTableDataInfo.get(tableName).pageInfoIndex = info.pageInfoIndex;
        currentTableDataInfo.get(tableName).recordIndexInPage = info.recordIndexInPage;
        currentTableDataInfo.get(tableName).initialized = true;
    }
}