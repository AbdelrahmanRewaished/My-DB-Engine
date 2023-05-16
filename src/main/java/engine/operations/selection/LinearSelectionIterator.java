package engine.operations.selection;

import engine.elements.*;
import engine.elements.Record;

import java.util.*;


public class LinearSelectionIterator implements Iterator<Record> {

    private final Table selectedTable;
    private final SelectFromTableParams sp;
    private final TableRecordInfo tableRecordInfo;
    private Page currentPage;
    public LinearSelectionIterator(SelectFromTableParams sp, Table selectedTable) {
        this.sp = sp;
        this.selectedTable = selectedTable;
        tableRecordInfo = new TableRecordInfo(0, -1);
    }


    private TableRecordInfo getNextValidRecordInfo() {
        int currentPageInfoIndex = tableRecordInfo.getPageInfoIndex();
        int currentRecordIndex = tableRecordInfo.getRecordIndexInPage();
        PageMetaInfo currentPageMetaInfo = selectedTable.getPagesInfo().get(currentPageInfoIndex);
        if(currentRecordIndex < currentPageMetaInfo.getCurrentNumberOfRecords() - 1) {
            currentRecordIndex++;
        }
        else {
            currentRecordIndex = 0;
            currentPageInfoIndex++;
        }
        while(currentPageInfoIndex < selectedTable.getPagesInfo().size()) {
            currentPageMetaInfo = selectedTable.getPagesInfo().get(currentPageInfoIndex);
            currentPage = Page.deserializePage(currentPageMetaInfo);
            while(currentRecordIndex < currentPage.size()) {
                Record currentRecord = currentPage.get(currentRecordIndex);
                if(currentRecord.isMatchingRecord(sp)){
                    return new TableRecordInfo(currentPageInfoIndex, currentRecordIndex);
                }
                currentRecordIndex++;
            }
            currentRecordIndex = 0;
            currentPageInfoIndex++;
        }
        // Record required is not found
        return null;
    }

    @Override
    public boolean hasNext() {
        return getNextValidRecordInfo() != null;
    }
    @Override
    public Record next() {
        TableRecordInfo nextRecordInfo = getNextValidRecordInfo();
        if(nextRecordInfo == null) {
            return null;
        }
        tableRecordInfo.saveNewState(nextRecordInfo);
        tableRecordInfo.setCurrentPage(currentPage);
        return tableRecordInfo.getCurrentPage().get(nextRecordInfo.getRecordIndexInPage());
    }
}
