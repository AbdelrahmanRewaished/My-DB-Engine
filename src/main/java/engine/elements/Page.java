package engine.elements;

import java.util.Vector;

public class Page extends Vector<Record> {
    private String locationPath;

    public Page(String locationPath) {
        super();
        this.locationPath = locationPath;
    }

    public String getLocationPath() {
        return locationPath;
    }
    public void addRecord(Record record) {
        add(record);
    }
}
