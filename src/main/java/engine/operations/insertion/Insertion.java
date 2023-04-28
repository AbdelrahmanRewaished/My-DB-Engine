package engine.operations.insertion;

import engine.DBApp;
import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import engine.exceptions.insertion_exceptions.PrimaryKeyAlreadyExistsException;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;
import utilities.validation.InsertionValidator;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static engine.DBApp.getFileExtension;

public class Insertion {
    private final InsertIntoTableParams params;
    private final String tableName;
    private final Record record;
    public Insertion(InsertIntoTableParams params) {
        this.params = params;
        tableName = params.getTableName();
        record = params.getRecord();
    }

    public int getRequiredRecordIndex(Page page, String clusteringKey, Object keyValue) {
        int ELEMENT_FOUND = -1;
        int left = 0, right = page.size() - 1;
        while(left <= right) {
            int mid = (left + right) / 2;
            Hashtable<String, Object> currentRecord = page.get(mid);
            Comparable currentClusteringKey = (Comparable) currentRecord.get(clusteringKey);
            if(currentClusteringKey.compareTo(keyValue) > 0) {
                right = mid - 1;
            }
            else if(currentClusteringKey.compareTo(keyValue) < 0) {
                left = mid + 1;
            }
            else {
                return ELEMENT_FOUND;
            }
        }
        return left;
    }
    public int getRequiredPageInfoIndex(Table table, Object keyValue) {
        int ELEMENT_FOUND = -1;
        List<PageMetaInfo> pages = table.getPagesInfo();
        int left = 0, right = pages.size() - 1;
        // To track the last position the pointers moved in
        int currentDirection = -1;

        while(left <= right) {
            int mid = (left + right) / 2;
            PageMetaInfo currentPage = pages.get(mid);
            Comparable min = currentPage.getMinimumContainedKey();
            Comparable max = currentPage.getMaximumContainedKey();
            if(min.compareTo(keyValue) > 0) {
                right = mid - 1;
                currentDirection = right;
            }
            else if(max.compareTo(keyValue) < 0) {
                left = mid + 1;
                currentDirection = left;
            }
            else if(max.compareTo(keyValue) == 0 || min.compareTo(keyValue) == 0) {
                return ELEMENT_FOUND;
            }
            else if(min.compareTo(keyValue) < 0 && max.compareTo(keyValue) > 0) {
                return mid;
            }
        }

        return getAdjustedSearchedPageIndex(currentDirection, pages, keyValue);
    }
    // To avoid inserting in a full page as possible
    private int getAdjustedSearchedPageIndex(int currentIndex, List<PageMetaInfo> pages, Object keyValue) {
        int FIRST_ELEMENT_INDEX = 0;
        if(currentIndex == -1) {
            return FIRST_ELEMENT_INDEX;
        }
        if(currentIndex == pages.size()) {
            if(! pages.get(currentIndex - 1).isFull()) {
                currentIndex--;
            }
        }
        else if(pages.get(currentIndex).getMinimumContainedKey().compareTo(keyValue) > 0) {
            if(pages.get(currentIndex).isFull() || ! pages.get(currentIndex - 1).isFull()) {
                currentIndex--;
            }
        }
        else if(pages.get(currentIndex).getMaximumContainedKey().compareTo(keyValue) < 0) {
            if(pages.get(currentIndex).isFull() && ! pages.get(currentIndex + 1).isFull()) {
                currentIndex++;
            }
        }
        return currentIndex;
    }
    private String getNextPageFileLocation(List<PageMetaInfo> pagesInfo) {
        if(pagesInfo.isEmpty()) {
            return "/0" + getFileExtension();
        }
        String[] splitter = pagesInfo.get(pagesInfo.size() - 1).getLocation().split("/");
        int lastPageNumber = Integer.parseInt(splitter[splitter.length - 1].split(getFileExtension())[0]);
        return "/" + (lastPageNumber + 1) + getFileExtension();
    }
    private Page getNewPage(Table table) {
        PageMetaInfo pageMetaInfo = new PageMetaInfo(table.getFolderLocation() + getNextPageFileLocation(table.getPagesInfo()));
        return new Page(pageMetaInfo);
    }

    private Page getPageToInsertIn(Table table, int requiredPageInfoIndex) {
        if(requiredPageInfoIndex == table.getPagesInfo().size()) {
            Page newPage = getNewPage(table);
            table.addPageInfo(newPage.getPageInfo());
            return newPage;
        }
        PageMetaInfo requiredPageMetaInfo = table.getPagesInfo().get(requiredPageInfoIndex);
        return Page.deserializePage(requiredPageMetaInfo);
    }
    private void checkIfPrimaryKeyAlreadyExists(Table table, Object clusteringValue, int index) throws DBAppException {
        if(index == -1) {
            throw new PrimaryKeyAlreadyExistsException(clusteringValue.toString(), table.getName());
        }
    }
    private void adjustTablePages(Table table, int nextPageIndex, Page currentPage) {
        List<PageMetaInfo> pagesInfo = table.getPagesInfo();
        boolean isOutOfBound = true;
        while(nextPageIndex < pagesInfo.size() && isOutOfBound) {
            Record nextRecord = currentPage.removeRecord(currentPage.size() - 1, table.getClusteringKey());
            Serializer.serialize(currentPage.getPageInfo().getLocation(), currentPage);
            PageMetaInfo nextPageMetaInfo = pagesInfo.get(nextPageIndex);
            currentPage = Page.deserializePage(nextPageMetaInfo);
            isOutOfBound = ! currentPage.addRecord(0, table.getClusteringKey(), nextRecord);
            nextPageIndex++;
        }
        if(isOutOfBound) {
            Record nextRecord = currentPage.removeRecord(currentPage.size() - 1, table.getClusteringKey());
            Serializer.serialize(currentPage.getPageInfo().getLocation(), currentPage);
            currentPage = getNewPage(table);
            table.addPageInfo(currentPage.getPageInfo());
            currentPage.addRecord(0, table.getClusteringKey(), nextRecord);
        }
        Serializer.serialize(currentPage.getPageInfo().getLocation(), currentPage);
    }
    public synchronized void insertIntoTable() throws DBAppException {
        InsertionValidator.validate(params);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(tableName);
        Object clusteringValue = record.get(table.getClusteringKey());
        int requiredPageInfoIndex = getRequiredPageInfoIndex(table, clusteringValue);
        checkIfPrimaryKeyAlreadyExists(table, clusteringValue, requiredPageInfoIndex);
        Page page = getPageToInsertIn(table, requiredPageInfoIndex);
        int requiredRecordIndex = getRequiredRecordIndex(page, table.getClusteringKey(), clusteringValue);
        checkIfPrimaryKeyAlreadyExists(table, clusteringValue, requiredRecordIndex);
        boolean isOverFullPage = ! page.addRecord(requiredRecordIndex, table.getClusteringKey(), record);
        if(isOverFullPage) {
            adjustTablePages(table, requiredPageInfoIndex + 1, page);
        }
        else {
            Serializer.serialize(page.getPageInfo().getLocation(), page);
        }
        Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), serializedTablesInfo);
    }
}
