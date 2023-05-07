package engine.operations.selection;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TableRecordInfo {
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

    public void saveNewState(TableRecordInfo tableRecordInfo) {
        pageInfoIndex = tableRecordInfo.getPageInfoIndex();
        recordIndexInPage = tableRecordInfo.getRecordIndexInPage();
        initialized = true;
    }

}