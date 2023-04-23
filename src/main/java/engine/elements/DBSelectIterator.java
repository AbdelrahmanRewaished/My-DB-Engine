package engine.elements;

import engine.DBApp;
import engine.operations.paramters.SelectFromTableParams;
import utilities.serialization.Deserializer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class DBSelectIterator implements Iterator<Record> {
    private List<PageInfo> pageInfoList;
    private int currentPageInfoIndex;
    private SelectFromTableParams sp;
    public DBSelectIterator(SelectFromTableParams sp) {
        this.sp = sp;
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>)Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        currentPageInfoIndex = 0;
    }

    @Override
    public boolean hasNext() {

        return false;
    }

    @Override
    public Record next() {
        return null;
    }

    @Override
    public void remove() {
        Iterator.super.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super Record> action) {
        Iterator.super.forEachRemaining(action);
    }
}
