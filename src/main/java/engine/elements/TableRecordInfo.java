package engine.elements;

import java.io.Serializable;
import java.util.Objects;

public class TableRecordInfo implements Serializable {
    private int pageInfoIndex;
    private int recordIndexInPage;

    private Page currentPage;

    public TableRecordInfo(int currentPageInfoIndex, int currentRecordIndex) {
        this.pageInfoIndex = currentPageInfoIndex;
        this.recordIndexInPage = currentRecordIndex;
    }

    public int getPageInfoIndex() {
        return pageInfoIndex;
    }

    public int getRecordIndexInPage() {
        return recordIndexInPage;
    }


    public void saveNewState(TableRecordInfo tableRecordInfo) {
        pageInfoIndex = tableRecordInfo.getPageInfoIndex();
        recordIndexInPage = tableRecordInfo.getRecordIndexInPage();
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
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

    @Override
    public String toString() {
        return "TableRecordInfo{" +
                "pageInfoIndex=" + pageInfoIndex +
                ", recordIndexInPage=" + recordIndexInPage +
                '}';
    }
}