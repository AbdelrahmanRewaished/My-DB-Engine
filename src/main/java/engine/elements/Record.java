package engine.elements;

import java.util.HashMap;
import java.util.Set;

public class Record extends HashMap<String, Object> {
    public void addValue(String columnName, Object value) {
        put(columnName, value);
    }
    public Set<String> getColumnNames() {
        return keySet();
    }
}
