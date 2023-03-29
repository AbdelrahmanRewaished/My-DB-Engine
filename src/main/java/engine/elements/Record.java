package engine.elements;


import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class Record extends Hashtable<String, Object> {

    public Record(Map<? extends String, ?> t) {
        super(t);
    }

    public void addValue(String columnName, Object value) {
        put(columnName, value);
    }
    public Set<String> getColumnNames() {
        return keySet();
    }
}
