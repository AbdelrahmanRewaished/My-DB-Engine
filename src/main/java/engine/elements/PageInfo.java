package engine.elements;

import utilities.PropertiesReader;

import java.io.Serial;
import java.io.Serializable;

public class PageInfo implements Serializable {
    private static final int maxNumberOfRecords = PropertiesReader.getProperty("MaximumRowsCountinTablePage");
    private int currentNumberOfRecords;
    private final String location;
    private Comparable minimumContainedKey;
    private Comparable maximumContainedKey;

    @Serial
    private static final long serialVersionUID = 3147410295215563180L;

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
    public void decrementCurrentNumberOfRecords() {
        this.currentNumberOfRecords--;
    }

    public Comparable getMinimumContainedKey() {
        return minimumContainedKey;
    }

    public void setMinimumContainedKey(Comparable minimumContainedKey) {
        this.minimumContainedKey = minimumContainedKey;
    }

    public Comparable getMaximumContainedKey() {
        return maximumContainedKey;
    }

    public void setMaximumContainedKey(Comparable maximumContainedKey) {
        this.maximumContainedKey = maximumContainedKey;
    }
    public boolean isFull() {
        return currentNumberOfRecords == maxNumberOfRecords;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "currentNumberOfRecords=" + currentNumberOfRecords +
                ", location='" + location + '\'' +
                ", minimumContainedKey=" + minimumContainedKey +
                ", maximumContainedKey=" + maximumContainedKey +
                ", isFull=" + isFull() +
                '}';
    }
}
