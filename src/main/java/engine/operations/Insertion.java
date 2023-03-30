package engine.operations;

import engine.DBApp;
import engine.DBAppException;
import engine.elements.Page;
import engine.elements.PageInfo;
import engine.elements.Table;
import utilities.metadata.Validator;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;

import java.util.*;

public class Insertion {
    private Insertion(){}

    private static int getRequiredPageInfoIndex(Table table, Comparable element) throws DBAppException{
        List<PageInfo> pages = table.getPagesInfo();
        int left = 0, right = pages.size() - 1;
        int mid = 0;
        while(left <= right) {
            mid = (left + right) / 2;
            PageInfo currentPage = pages.get(mid);
            Comparable min = currentPage.getMinimumContainedKey();
            Comparable max = currentPage.getMaximumContainedKey();
            if(element.compareTo(min) < 0) {
                right = mid - 1;
            }
            else if(element.compareTo(max) > 0 && currentPage.isFull()) {
                left = mid + 1;
            }
            else if(element.compareTo(max) > 0) {
                return mid;
            }
            else if(element.compareTo(max) == 0 || element.compareTo(min) == 0) {
                throw new DBAppException(String.format("Primary key inserted already exists in Table '%s'", table.getName()));
            }
            else {
                return mid;
            }
        }
        if(left > 0) {
            return left;
        }
        if(left == 0) {
            return mid;
        }
        if(pages.get(left - 1).getCurrentNumberOfRecords() < PageInfo.getMaxNumberOfRecords()) {
            return left - 1;
        }
        return left;
    }
    private static String getNextPageFileLocation(List<PageInfo> pagesInfo) {
        if(pagesInfo.isEmpty()) {
            return "/0.txt";
        }
        String[] splitter = pagesInfo.get(pagesInfo.size() - 1).getLocation().split("/");
        int lastPageNumber = Integer.parseInt(splitter[splitter.length - 1].split(".txt")[0]);
        return "/" + (lastPageNumber + 1) + ".txt";
    }
    private static Page addNewPage(Table table) {
        PageInfo pageInfo = new PageInfo(table.getFolderLocation() + getNextPageFileLocation(table.getPagesInfo()));
        table.addPageInfo(pageInfo);
        return new Page(pageInfo);
    }
    private static int getRequiredRecordIndex(Page page, String clusteringKey, Object element) {
        int left = 0, right = page.size() - 1;
        while(left <= right) {
            int mid = (left + right) / 2;
            Hashtable<String, Object> currentRecord = page.get(mid);
            Comparable currentClusteringKey = (Comparable) currentRecord.get(clusteringKey);
            if(currentClusteringKey.compareTo(element) > 0) {
                right = mid - 1;
            }
            else if(currentClusteringKey.compareTo(element) < 0) {
                left = mid + 1;
            }
            else {
                return -1;
            }
        }
        return left;
    }
    private static Page getPageToInsertIn(Table table, int requiredPageInfoIndex) {
        if(requiredPageInfoIndex == table.getPagesInfo().size()) {
            return addNewPage(table);
        }
        PageInfo requiredPageInfo = table.getPagesInfo().get(requiredPageInfoIndex);
        return Page.deserializePage(requiredPageInfo);
    }
    private static void checkIfPrimaryKeyAlreadyExists(Table table, Object clusteringValue, int requiredRecordIndex) throws DBAppException {
        if(requiredRecordIndex == -1) {
            throw new DBAppException(String.format("Primary Key of value '%s' already exists in Table '%s'", clusteringValue.toString(), table.getName()));
        }
    }
    private static void adjustTablePages(Table table, int nextPageIndex, Page currentPage) {
        List<PageInfo> pagesInfo = table.getPagesInfo();
        boolean isOutOfBound = true;
        while(nextPageIndex < pagesInfo.size() && isOutOfBound) {
            Hashtable<String, Object> nextRecord = currentPage.removeLast(table.getClusteringKey());
            Serializer.serialize(currentPage.getPageInfo().getLocation(), currentPage);
            PageInfo nextPageInfo = pagesInfo.get(nextPageIndex);
            currentPage = Page.deserializePage(nextPageInfo);
            isOutOfBound = ! currentPage.addRecord(0, table.getClusteringKey(), nextRecord);
            nextPageIndex++;
        }
        if(isOutOfBound) {
            Hashtable<String, Object> nextRecord = currentPage.removeLast(table.getClusteringKey());
            Serializer.serialize(currentPage.getPageInfo().getLocation(), currentPage);
            currentPage = addNewPage(table);
            currentPage.addRecord(0, table.getClusteringKey(), nextRecord);
        }
        Serializer.serialize(currentPage.getPageInfo().getLocation(), currentPage);
    }
    public static void insertIntoTable(String tableName, Hashtable<String, Object> colNameValue) throws DBAppException {
        Validator.checkInsertionInputsValidity(tableName, colNameValue);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(tableName);
        Object clusteringValue = colNameValue.get(table.getClusteringKey());
        int requiredPageInfoIndex = getRequiredPageInfoIndex(table, (Comparable)clusteringValue);
        Page page = getPageToInsertIn(table, requiredPageInfoIndex);
        int requiredRecordIndex = getRequiredRecordIndex(page, table.getClusteringKey(), clusteringValue);
        checkIfPrimaryKeyAlreadyExists(table, clusteringValue, requiredRecordIndex);
        boolean isOverFullPage = ! page.addRecord(requiredRecordIndex, table.getClusteringKey(), colNameValue);
        if(isOverFullPage) {
            adjustTablePages(table, requiredPageInfoIndex + 1, page);
        }
        else {
            Serializer.serialize(page.getPageInfo().getLocation(), page);
        }
        Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), serializedTablesInfo);
    }
}
