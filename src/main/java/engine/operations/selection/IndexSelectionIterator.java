package engine.operations.selection;

import engine.elements.Record;
import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.elements.index.Octree;

import java.util.Iterator;

public class IndexSelectionIterator implements Iterator<Record> {
    private final Iterator<Record> selectedRecordsIterator;

    public IndexSelectionIterator(SelectFromTableParams sp, Table selectedTable) {
        IndexMetaInfo indexSearchedValues = sp.getIndexSearchedValues(selectedTable);
        selectedRecordsIterator = Octree.deserializeIndex(indexSearchedValues).getMatchingRecordsIterator(selectedTable, sp);
    }

    @Override
    public boolean hasNext() {
        return selectedRecordsIterator.hasNext();
    }

    @Override
    public Record next() {
        return selectedRecordsIterator.next();
    }
}
