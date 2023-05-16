package engine.operations.selection;

import engine.elements.Record;
import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.elements.index.Octree;

import java.util.*;

public class IndexSelectionIterator implements Iterator<Record> {
    private List<Iterator<Record>> selectedRecordsIterators;
    private int currentIteratorIndex;

    public IndexSelectionIterator(SelectFromTableParams sp, Table selectedTable) {
        selectedRecordsIterators = new Vector<>();
        for(IndexMetaInfo indexMetaInfo: sp.getIndexSearchedValues(selectedTable)) {
            selectedRecordsIterators.add(Octree.deserializeIndex(indexMetaInfo).getMatchingRecordsIterator(selectedTable, sp));
        }
        currentIteratorIndex = 0;
    }

    @Override
    public boolean hasNext() {
        while(currentIteratorIndex < selectedRecordsIterators.size() && ! selectedRecordsIterators.get(currentIteratorIndex).hasNext()) {
            currentIteratorIndex++;
        }
        if(currentIteratorIndex == selectedRecordsIterators.size()) {
            return false;
        }
        return true;
    }

    @Override
    public Record next() {
        return selectedRecordsIterators.get(currentIteratorIndex).next();
    }
}
