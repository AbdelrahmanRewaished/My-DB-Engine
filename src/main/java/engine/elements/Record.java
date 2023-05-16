package engine.elements;


import engine.elements.index.IndexRecordInfo;
import engine.operations.selection.SQLTerm;
import engine.operations.selection.SelectFromTableParams;
import utilities.datatypes.DBAppNull;

import java.io.Serial;
import java.util.*;

import static engine.operations.selection.Selection.getConditionResult;

public class Record extends Hashtable<String, Object> {

    @Serial
    private static final long serialVersionUID = 208255039577813193L;
    public Record(Map<? extends String, ?> t) {
        super(t);
    }

    public Record() {
    }
    public boolean hasMatchingNonNullValues(String clusteringKey, Hashtable<String, Object> colNameValue) {
        for(String columnName: colNameValue.keySet()) {
            if(colNameValue.get(columnName) instanceof DBAppNull || this.get(columnName) instanceof DBAppNull || columnName.equals(clusteringKey)) {
                continue;
            }
            if(! colNameValue.get(columnName).equals(this.get(columnName))) {
                return false;
            }
        }
        return true;
    }
    public boolean hasMatchingValues(Hashtable<String, Object> colNameValue) {
        if(colNameValue == null) {
            return true;
        }
        for(String columnName: colNameValue.keySet()) {
            if(get(columnName) instanceof DBAppNull && colNameValue.get(columnName) instanceof DBAppNull) {
                continue;
            }
            if(! get(columnName).equals(colNameValue.get(columnName))) {
                return false;
            }
        }
        return true;
    }
    public boolean hasMatchingValues(SQLTerm term) {
        // SELECT * FROM Table_name;  ->   always matches as there is no WHERE condition
        if(term._strColumnName == null && term._strOperator == null && term._objValue == null) {
            return true;
        }
        if(term._objValue instanceof DBAppNull) {
            return false;
        }
        if(this.containsKey(term._strColumnName) && this.get(term._strColumnName) instanceof DBAppNull) {
            return false;
        }
        assert term._strOperator != null;
        return switch (term._strOperator) {
            case "=" -> get(term._strColumnName).equals(term._objValue);
            case "<" -> ((Comparable) get(term._strColumnName)).compareTo(term._objValue) < 0;
            case "<=" -> ((Comparable) get(term._strColumnName)).compareTo(term._objValue) <= 0;
            case ">" -> ((Comparable) get(term._strColumnName)).compareTo(term._objValue) > 0;
            case ">=" -> ((Comparable) get(term._strColumnName)).compareTo(term._objValue) >= 0;
            case "!=" -> ! get(term._strColumnName).equals(term._objValue);
            default -> false;
        };
    }
    public int countNonNullValues() {
        int count = 0;
        for(String columnName: this.keySet()) {
            if(! (this.get(columnName) instanceof DBAppNull)) {
                count++;
            }
        }
        return count;
    }
    public void updateValues(Hashtable<String, Object> colNameNewValue) {
        for(String columnName: colNameNewValue.keySet()) {
            Object newValue = colNameNewValue.get(columnName);
            this.put(columnName, newValue);
        }
    }

    private List<Boolean> getSQLTermsEvaluationOfRecord(SQLTerm[] sqlTerms) {
        List<Boolean> sqlTermsEvaluations = new ArrayList<>();
        for(SQLTerm term: sqlTerms) {
            sqlTermsEvaluations.add(this.hasMatchingValues(term));
        }
        return sqlTermsEvaluations;
    }
    public boolean isMatchingRecord(SelectFromTableParams sp) {
        List<Boolean> sqlTermsEvaluations = getSQLTermsEvaluationOfRecord(sp.getSqlTerms());
        int logicalOperatorsIndex = 0;
        boolean currentEvaluation = sqlTermsEvaluations.remove(0);
        while(! sqlTermsEvaluations.isEmpty()) {
            String currentOperator = sp.getLogicalOperators()[logicalOperatorsIndex++];
            currentEvaluation = getConditionResult(currentEvaluation, currentOperator, sqlTermsEvaluations.remove(0));
        }
        return currentEvaluation;
    }
    public static Record getRecord(Table table, IndexRecordInfo info) {
        PageMetaInfo pageMetaInfo = table.getPagesInfo().get(info.getPageNumber());
        Page page = Page.deserializePage(pageMetaInfo);
        int storedRecordIndex = page.findRecordIndex(table.getClusteringKey(), info.getClusteringKeyValue());
        return page.get(storedRecordIndex);
    }

}
