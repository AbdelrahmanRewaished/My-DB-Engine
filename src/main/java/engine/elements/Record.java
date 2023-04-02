package engine.elements;


import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class Record extends Hashtable<String, Object> {

    private static final long serialVersionUID = 208255039577813193L;
    public Record(Map<? extends String, ?> t) {
        super(t);
    }

    public void putValue(String columnName, Object value) {
        super.put(columnName, value);
    }
    public Set<String> getColumnNames() {
        return keySet();
    }
    @Override
    public boolean remove(Object key, Object value) {
        return super.remove(key, value);
    }
    public Object get(String columnName) {
        return super.get(columnName);
    }

    public boolean containsColumn(String columnName) {
        return super.containsKey(columnName);
    }
}
