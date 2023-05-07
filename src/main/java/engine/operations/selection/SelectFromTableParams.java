package engine.operations.selection;

import engine.DBApp;
import engine.exceptions.DBAppException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("ALL")
public class SelectFromTableParams {
    private final SQLTerm[] sqlTerms;
    private final String[] logicalOperators;

    public SelectFromTableParams(SQLTerm[] sqlTerms, String[] logicalOperators) throws DBAppException {
        checkIfValidSqlExpression(sqlTerms, logicalOperators);
        checkIfLogicalOperatorsAreValid(logicalOperators);
        checkIfContainsOnlyOneTable(sqlTerms);
        this.sqlTerms = sqlTerms;
        this.logicalOperators = logicalOperators;
    }
    private void checkIfValidSqlExpression(SQLTerm[] sqlTerms, String[] logicalOperators) throws DBAppException {
        for(SQLTerm term: sqlTerms) {
            if(! term.isValidSqlTerm()) {
                throw new DBAppException(String.format("Invalid SQL Term '%s'", term));
            }
        }
        if(sqlTerms.length - 1 != logicalOperators.length) {
            throw new DBAppException("Invalid SelectFromTable Parameters");
        }
    }
    private void checkIfLogicalOperatorsAreValid(String[] logicalOperators) throws DBAppException {
        capitalizeWords(logicalOperators);
        Set<String> set = new HashSet<>(Arrays.asList(logicalOperators));
        for(String logicalOperator: set) {
            if(! DBApp.getSupportedSqlLogicalOperators().contains(logicalOperator)) {
                throw new DBAppException(String.format("'%s' is an invalid logical operator. Supported operators are: %s", logicalOperator, DBApp.getSupportedSqlLogicalOperators()));
            }
        }
    }
    private void checkIfContainsOnlyOneTable(SQLTerm[] sqlTerms) throws DBAppException {
        for(int i = 0; i < sqlTerms.length - 1; i++) {
            String table1 = sqlTerms[i]._strTableName, table2 = sqlTerms[i + 1]._strTableName;
            if(! table1.equals(table2)) {
                throw new DBAppException("Select statement cannot have more than one table");
            }
        }
    }
    private void capitalizeWords(String[] words) {
        for(int i = 0; i < words.length; i++) {
            words[i] = words[i].toUpperCase();
        }
    }
    public SQLTerm[] getSqlTerms() {
        return sqlTerms;
    }

    public String[] getLogicalOperators() {
        return logicalOperators;
    }

    public String getSelectedTableName() {
        return sqlTerms[0]._strTableName;
    }
}
