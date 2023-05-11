package engine.operations.selection;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableRecordInfo)) return false;
        TableRecordInfo that = (TableRecordInfo) o;
        return getPageInfoIndex() == that.getPageInfoIndex() && getRecordIndexInPage() == that.getRecordIndexInPage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageInfoIndex(), getRecordIndexInPage());
    }
}