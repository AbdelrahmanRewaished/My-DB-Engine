package engine.operations.paramters;

import engine.SQLTerm;

import java.util.HashSet;
import java.util.Set;

public class SelectFromTableParams extends Params {
    private SQLTerm[] sqlTerms;
    private String[] logicalOperators;

    public SelectFromTableParams(SQLTerm[] sqlTerms, String[] logicalOperators) {
        this.sqlTerms = sqlTerms;
        this.logicalOperators = logicalOperators;
    }

    public SQLTerm[] getSqlTerms() {
        return sqlTerms;
    }

    public String[] getLogicalOperators() {
        return logicalOperators;
    }

    public Set<String> getTableNames() {
        Set set = new HashSet();
        for(SQLTerm term: sqlTerms) {
            set.add(term._strTableName);
        }
        return set;
    }
}
