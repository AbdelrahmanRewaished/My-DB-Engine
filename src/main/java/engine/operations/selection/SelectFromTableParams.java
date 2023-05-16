package engine.operations.selection;

import engine.DBApp;
import engine.DBParser;
import engine.elements.Table;
import engine.elements.index.IndexMetaInfo;
import engine.exceptions.DBAppException;
import utilities.serialization.Deserializer;

import java.util.*;

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


    Set<IndexMetaInfo> getIndexSearchedValues(Table table) {
        Set<IndexMetaInfo> selectedIndexes = new HashSet<>();
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
                if(indexMetaInfo != null) {
                    selectedIndexes.add(indexMetaInfo);
                }
                continue;
            }
            if(indexMetaInfo.equals(nextIndexMetaInfo)) {
                colNameValue.put(term._strColumnName, term._objValue);
                colNameValue.put(nextTerm._strColumnName, nextTerm._objValue);
            }
            else if(maxSize <= colNameValue.size()){
                if(logicalOperators[i].equals("AND")) {
                    maxSize = colNameValue.size();
                    maxColumnsContainedIndex = indexMetaInfo;
                    colNameValue = new Hashtable<String, Object>();
                }
                else {
                    maxColumnsContainedIndex = maxColumnsContainedIndex == null? indexMetaInfo: maxColumnsContainedIndex;
                    selectedIndexes.add(maxColumnsContainedIndex);
                    selectedIndexes.add(nextIndexMetaInfo);
                    maxSize = 0;
                    maxColumnsContainedIndex = null;
                }
            }
        }
        SQLTerm term = sqlTerms[i];
        if(maxColumnsContainedIndex != null) {
            if(maxSize >= colNameValue.size()) {
                selectedIndexes.add(maxColumnsContainedIndex);
            }
            else {
                selectedIndexes.add(getCorrespondingTermIndex(table, term));
            }
            if(logicalOperators[i - 1].equals("AND")) {
                return selectedIndexes;
            }
        }
        IndexMetaInfo indexMetaInfo = getCorrespondingTermIndex(table, term);
        if(indexMetaInfo != null) {
            colNameValue.put(term._strColumnName, term._objValue);
            if(maxColumnsContainedIndex != null && maxSize < colNameValue.size()) {
                maxSize = colNameValue.size();
                maxColumnsContainedIndex = indexMetaInfo;
                selectedIndexes.add(maxColumnsContainedIndex);
            }
            else {
                selectedIndexes.add(indexMetaInfo);
            }
        }
        return selectedIndexes;
    }

    boolean isQueryCompatibleWithIndexing(Table table) {
        if(isContainingNegationQueries()) {
            return false;
        }
        if(! isContainingIndexedColumns(table)) {
            return false;
        }
        if(areTwoIndexesXored(table)) {
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
    private boolean areTwoIndexesXored(Table table) {
        for(int i = 0; i < sqlTerms.length - 1; i++) {
            SQLTerm term = sqlTerms[i];
            SQLTerm otherTerm = sqlTerms[i + 1];
            if(logicalOperators[i].equals("XOR")) {
                return true;
            }
        }
        return false;
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
}
