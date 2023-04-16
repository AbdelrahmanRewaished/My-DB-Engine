package engine.operations;

import engine.SQLTerm;
import engine.elements.Record;
import engine.operations.paramters.SelectFromTableParams;

import java.util.Iterator;

public class Selection {
    SQLTerm[] sqlTerms;
    String[] operators;
    private final SelectFromTableParams params;

    public Selection(SelectFromTableParams params) {
        this.params = params;
        this.sqlTerms = params.getSqlTerms();
        this.operators = params.getLogicalOperators();
    }
    public Iterator<Record> select() {
        return null;
    }
}
