package engine.operations.selection;

import java.util.Hashtable;

public class LinearSearchInitialization implements SelectionInitializationStrategy {
    private final SelectFromTableParams sp;

    public LinearSearchInitialization(SelectFromTableParams sp) {
        this.sp = sp;
    }
    @Override
    public Hashtable<String, SelectIteratorReferences.TableRecordInfo> getInitialTablesDataInfo() {
        Hashtable<String, SelectIteratorReferences.TableRecordInfo> result = new Hashtable<>();
        for(SQLTerm term: sp.getSqlTerms()) {
            result.put(term._strTableName, new SelectIteratorReferences.TableRecordInfo(0, 0));
        }
        return result;
    }
}
