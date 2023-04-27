package engine.operations.selection;

import engine.elements.Page;
import engine.elements.Table;

import java.util.Hashtable;

public class BinarySearchInitialization implements SelectionInitializationStrategy {

    private final SelectFromTableParams sp;

    public BinarySearchInitialization(SelectFromTableParams sp) {
        this.sp = sp;
    }

    @Override
    public Hashtable<String, SelectIteratorReferences.TableRecordInfo> getInitialTablesDataInfo() {
        Hashtable<String, SelectIteratorReferences.TableRecordInfo> result = new Hashtable<>();
        Hashtable<String, Table> tableNameObject = sp.getTableNameObject();
        for(SQLTerm term: sp.getSqlTerms()) {
            String tableName = term._strTableName;
            if(result.containsKey(tableName)) {
                continue;
            }
            Table table = tableNameObject.get(tableName);
            int requiredPageIndex = table.findPageIndexToLookIn(term._objValue);
            Page page = Page.deserializePage(table.getPagesInfo().get(requiredPageIndex));
            int requiredRecordIndex = page.findRecordIndex(table.getClusteringKey(), (Comparable) term._objValue);
            result.put(term._strTableName, new SelectIteratorReferences.TableRecordInfo(requiredPageIndex, requiredRecordIndex));
        }
        return result;
    }
}
