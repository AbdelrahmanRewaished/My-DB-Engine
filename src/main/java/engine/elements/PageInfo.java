package engine.elements;

import utilities.PropertiesReader;
public class PageInfo {
    private static int maxNumberOfRecords = PropertiesReader.getProperty("MaximumRowsCountinTablePage");
    private int currentNumberOfRecords;
    private String location;
    private Comparable<Object> minimumContainedKey;
    private Comparable<Object> maximumContainedKey;

    public PageInfo(String location) {
        this.location = location;
        currentNumberOfRecords = 0;
    }

    public static int getMaxNumberOfRecords() {
        return maxNumberOfRecords;
    }

    public String getLocation() {
        return location;
    }

    public int getCurrentNumberOfRecords() {
        return currentNumberOfRecords;
    }

    public void incrementCurrentNumberOfRecords() {
        this.currentNumberOfRecords++;
    }

    public Comparable<Object> getMinimumContainedKey() {
        return minimumContainedKey;
    }

    public void setMinimumContainedKey(Comparable<Object> minimumContainedKey) {
        this.minimumContainedKey = minimumContainedKey;
    }

    public Comparable<Object> getMaximumContainedKey() {
        return maximumContainedKey;
    }

    public void setMaximumContainedKey(Comparable<Object> maximumContainedKey) {
        this.maximumContainedKey = maximumContainedKey;
    }
}
