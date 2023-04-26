package engine.operations.selection;

import java.util.Hashtable;

public interface SelectionInitializationStrategy {
    Hashtable<String, SelectIteratorReferences.TableRecordInfo> getInitialTablesDataInfo();
}
