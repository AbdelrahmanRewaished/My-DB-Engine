package engine.elements.index;

import engine.elements.Record;

import java.io.Serializable;
import java.util.Hashtable;

public class Boundary implements Serializable {
    private final Hashtable<String, Comparable> colNameMin;
    private final Hashtable<String, Comparable> colNameMax;

    public Boundary(Hashtable<String, Comparable> colNameMin, Hashtable<String, Comparable> colNameMax) {
        this.colNameMin = colNameMin;
        this.colNameMax = colNameMax;
    }

    Hashtable<String, Comparable> getLowBound() {
        return colNameMin;
    }

    Hashtable<String, Comparable> getHighBound() {
        return colNameMax;
    }

    boolean isRecordInBounds(Record record) {
        for (String columnName : record.keySet()) {
            if (colNameMin.get(columnName).compareTo(record.get(columnName)) > 0 || colNameMax.get(columnName).compareTo(record.get(columnName)) < 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Boundary{" +
                "colNameMin=" + colNameMin +
                ", colNameMax=" + colNameMax +
                '}';
    }
}
