package engine.elements.index;

import engine.elements.Record;
import utilities.datatypes.DBAppNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Hashtable;

public class Boundary implements Serializable {
    private final Hashtable<String, Comparable> colNameMin;
    private final Hashtable<String, Comparable> colNameMax;
    @Serial
    private static final long serialVersionUID = 8156794580834820388L;

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

    boolean isRecordInBounds(Hashtable<String, Object> colNameValue) {
        for (String columnName : colNameMin.keySet()) {
            if(! colNameValue.containsKey(columnName) || colNameValue.get(columnName) instanceof DBAppNull) {
                continue;
            }
            if (colNameMin.get(columnName).compareTo(colNameValue.get(columnName)) > 0 || colNameMax.get(columnName).compareTo(colNameValue.get(columnName)) < 0) {
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
