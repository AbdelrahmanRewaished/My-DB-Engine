package engine.elements;


import java.io.Serial;
import java.util.*;

public class Record extends Hashtable<String, Object> {

    @Serial
    private static final long serialVersionUID = 208255039577813193L;
    public Record(Map<? extends String, ?> t) {
        super(t);
    }

    public Record() {
    }
    public boolean hasMatchingValues(Hashtable<String, Object> colNameValue) {
        for(String columnName: colNameValue.keySet()) {
            if(! get(columnName).equals(colNameValue.get(columnName))) {
                return false;
            }
        }
        return true;
    }
}
