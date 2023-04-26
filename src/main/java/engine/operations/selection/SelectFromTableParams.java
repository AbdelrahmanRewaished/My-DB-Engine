package engine.operations.selection;

import engine.DBApp;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.serialization.Deserializer;

import java.util.*;

public class SelectFromTableParams {
    private final SQLTerm[] sqlTerms;
    private final String[] logicalOperators;

    public SelectFromTableParams(SQLTerm[] sqlTerms, String[] logicalOperators) throws DBAppException {
        if(sqlTerms.length - 1 != logicalOperators.length) {
            throw new DBAppException("Invalid SelectFromTable Parameters");
        }
        for(String logicalOperator: logicalOperators) {
            if(! DBApp.getSupportedSqlLogicalOperators().contains(logicalOperator)) {
                throw new DBAppException(String.format("'%s' is an invalid logical operator. Supported operators are: %s", logicalOperator, DBApp.getSupportedSqlLogicalOperators()));
            }
        }
        this.sqlTerms = sqlTerms;
        this.logicalOperators = logicalOperators;
    }

    public SQLTerm[] getSqlTerms() {
        return sqlTerms;
    }

    public String[] getLogicalOperators() {
        return logicalOperators;
    }
    Hashtable<String, Table> getTableNameObject() {
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Hashtable<String, Table> tableNameObject = new Hashtable<>();
        for(SQLTerm term: sqlTerms) {
            tableNameObject.put(term._strTableName, serializedTablesInfo.get(term._strTableName));
        }
        return tableNameObject;
    }

    List<SQLTerm> getCorrespondingTableSqlTerms(String tableName) {
        List<SQLTerm> result = new ArrayList<>();
        for(SQLTerm sqlTerm: sqlTerms) {
            if(sqlTerm._strTableName.equals(tableName)) {
                result.add(sqlTerm);
            }
        }
        return result;
    }

}
