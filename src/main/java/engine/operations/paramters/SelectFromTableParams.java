package engine.operations.paramters;

import engine.SQLTerm;

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
}
