package engine.operations.selection;

import engine.DBApp;
import engine.DBParser;
import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.exceptions.DBAppException;
import utilities.serialization.Deserializer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

@SuppressWarnings("ALL")
public class SelectFromTableParams {
    private final SQLTerm[] sqlTerms;
    private final String[] logicalOperators;

    public boolean isTermConditionExpressionNullable(SQLTerm term) {
        return term._strColumnName == null && term._strOperator == null && term._objValue == null;
    }
    public SelectFromTableParams(SQLTerm[] sqlTerms, String[] logicalOperators) throws DBAppException {
        checkIfValidSqlExpression(sqlTerms, logicalOperators);
        checkIfLogicalOperatorsAreValid(logicalOperators);
        checkIfContainsOnlyOneTable(sqlTerms);
        this.sqlTerms = sqlTerms;
        this.logicalOperators = logicalOperators;
    }
    boolean isACompleteScanQuery() {
        for(SQLTerm term: sqlTerms) {
            if(! isTermConditionExpressionNullable(term)) {
                return false;
            }
        }
        return true;
    }
    private void checkIfValidSqlExpression(SQLTerm[] sqlTerms, String[] logicalOperators) throws DBAppException {
        for(SQLTerm term: sqlTerms) {
            if(! term.isValidSqlTerm()) {
                throw new DBAppException(String.format("Invalid SQL Term '%s'", term));
            }
            if(sqlTerms.length > 1 && isTermConditionExpressionNullable(term)) {
                throw new DBAppException("Invalid SelectFromTable Statement");
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


    IndexMetaInfo getIndexSearchedValues(Table table) {
        Hashtable<String, Object> colNameValue = new Hashtable<>();
        int i;
        int maxSize = 0;
        IndexMetaInfo maxColumnsContainedIndex = null;
        for(i = 0; i < sqlTerms.length - 1; i++) {
            SQLTerm term = sqlTerms[i];
            SQLTerm nextTerm = sqlTerms[i + 1];
            IndexMetaInfo indexMetaInfo = getCorrespondingTermIndex(table, term);
            IndexMetaInfo nextIndexMetaInfo = getCorrespondingTermIndex(table, nextTerm);
            if(indexMetaInfo == null || nextIndexMetaInfo == null) {
                continue;
            }
            if(indexMetaInfo.equals(nextIndexMetaInfo)) {
                colNameValue.put(term._strColumnName, term._objValue);
                colNameValue.put(nextTerm._strColumnName, nextTerm._objValue);
            }
            else if(maxSize < colNameValue.size()){
                maxSize = colNameValue.size();
                maxColumnsContainedIndex = indexMetaInfo;
                colNameValue = new Hashtable<String, Object>();
            }
        }
        SQLTerm term = sqlTerms[i];
        IndexMetaInfo indexMetaInfo = getCorrespondingTermIndex(table, term);
        if(indexMetaInfo != null) {
            colNameValue.put(term._strColumnName, term._objValue);
            if(maxSize < colNameValue.size()) {
                maxSize = colNameValue.size();
                maxColumnsContainedIndex = indexMetaInfo;
            }
        }
        return maxColumnsContainedIndex;
    }

    boolean isQueryCompatibleWithIndexing(Table table) {
        if(isContainingNegationQueries()) {
            return false;
        }
        if(! isContainingIndexedColumns(table)) {
            return false;
        }
        if(! areAllIndexedColumnsOfTheSameIndexAnded(table)) {
            return false;
        }
        if(! areAllNonIndexedColumnsAnded(table)) {
            return false;
        }
        return true;
    }
    private boolean isContainingNegationQueries() {
        for(SQLTerm term: sqlTerms) {
            if(term.isANegationQuery()) {
                return true;
            }
        }
        return false;
    }
    private boolean isContainingIndexedColumns(Table table) {
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            for(SQLTerm term: sqlTerms) {
                if(indexMetaInfo.isContainingIndexColumn(term._strColumnName)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean areAllIndexedColumnsOfTheSameIndexAnded(Table table) {
        Hashtable<IndexMetaInfo, Set<String>> indexColumnsQueried = new Hashtable<>();
        IndexMetaInfo indexMetaInfo = null;
        for(int i = 0; i < sqlTerms.length - 1; i++) {
            SQLTerm term = sqlTerms[i];
            SQLTerm otherTerm = sqlTerms[i + 1];
            indexMetaInfo = getCorrespondingTermIndex(table, term);
            IndexMetaInfo otherIndexMetaInfo = getCorrespondingTermIndex(table, otherTerm);
            if(indexMetaInfo == null || otherIndexMetaInfo == null || ! indexMetaInfo.equals(otherIndexMetaInfo)) {
                if(indexMetaInfo != null) {
                    if (! indexColumnsQueried.containsKey(indexMetaInfo) || indexColumnsQueried.get(indexMetaInfo).size() < indexMetaInfo.getIndexedColumns().length - 1) {
                        return false;
                    }
                    indexColumnsQueried.remove(indexMetaInfo);
                }
                indexMetaInfo = null;
                if(logicalOperators[i].equals("AND")) {
                    continue;
                }
                else {
                    return false;
                }
            }
            if(! logicalOperators[i].equals("AND")) {
                return false;
            }
            if(! indexColumnsQueried.containsKey(indexMetaInfo)) {
                indexColumnsQueried.put(indexMetaInfo, new HashSet<>());
            }
            Set<String> columnsCovered = indexColumnsQueried.get(indexMetaInfo);
            columnsCovered.add(term._strColumnName);
            columnsCovered.add(otherTerm._strColumnName);
            indexColumnsQueried.put(indexMetaInfo, columnsCovered);
        }
        if(indexMetaInfo != null) {
            if(!indexColumnsQueried.containsKey(indexMetaInfo) || indexColumnsQueried.get(indexMetaInfo).size() < indexMetaInfo.getIndexedColumns().length - 1) {
                return false;
            }
        }
        return true;
    }
    private boolean areAllNonIndexedColumnsAnded(Table table) {
        for(int i = 0; i < sqlTerms.length - 1; i++) {
            SQLTerm term = sqlTerms[i];
            SQLTerm otherTerm = sqlTerms[i + 1];
            IndexMetaInfo indexMetaInfo = getCorrespondingTermIndex(table, term);
            IndexMetaInfo otherIndexMetaInfo = getCorrespondingTermIndex(table, otherTerm);
            if(indexMetaInfo != null && otherIndexMetaInfo != null) {
                continue;
            }
            if(! logicalOperators[i].equals("AND")) {
                return false;
            }
        }
        return true;
    }
    private IndexMetaInfo getCorrespondingTermIndex(Table table, SQLTerm term) {
        for(IndexMetaInfo indexMetaInfo: table.getIndicesInfo()) {
            if(indexMetaInfo.isContainingIndexColumn(term._strColumnName)) {
                return indexMetaInfo;
            }
        }
        return null;
    }

    public static void main(String[] args) throws DBAppException {
        Table table = (Table) Deserializer.deserialize(DBApp.getTableInfoFileLocation("Test"));
        DBParser parser = new DBParser("select * from Test where id = 0 or d = 1 and a = 2 and b = 3");
        System.out.println(parser.getSelectionParams().getIndexSearchedValues(table));
    }
}
