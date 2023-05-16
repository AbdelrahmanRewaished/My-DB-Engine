package engine.elements.index;

import engine.elements.Record;
import engine.operations.selection.SQLTerm;
import engine.operations.selection.SelectFromTableParams;
import engine.operations.selection.Selection;
import utilities.datatypes.DBAppNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Hashtable;

public class Boundary implements Serializable {
    private final Hashtable<String, Comparable> colNameMin;
    private final Hashtable<String, Comparable> colNameMax;
    @Serial
    private static final long serialVersionUID = 8156794580834820388L;

    public Boundary(Hashtable<String, Comparable> colNameMin, Hashtable<String, Comparable> colNameMax) {
        this.colNameMin = colNameMin;
        this.colNameMax = colNameMax;
    }

    Hashtable<String, Comparable> getLowBound() {
        return colNameMin;
    }

    Hashtable<String, Comparable> getHighBound() {
        return colNameMax;
    }

    boolean isRecordInBounds(Hashtable<String, Object> colNameValue) {
        for (String columnName : colNameMin.keySet()) {
            if(! colNameValue.containsKey(columnName) || colNameValue.get(columnName) instanceof DBAppNull) {
                continue;
            }
            if (colNameMin.get(columnName).compareTo(colNameValue.get(columnName)) > 0 || colNameMax.get(columnName).compareTo(colNameValue.get(columnName)) < 0) {
                return false;
            }
        }
        return true;
    }
    private boolean isSqlTermInBounds(SQLTerm term) {
        if(! colNameMin.containsKey(term._strColumnName)) {
            return true;
        }
        if(term._objValue instanceof DBAppNull) {
            return false;
        }
        if(colNameMin.get(term._strColumnName).compareTo(term._objValue) > 0) {
            if(term._strOperator.contains(">")) {
                return true;
            }
            else {
                return false;
            }
        }
        else if(colNameMax.get(term._strColumnName).compareTo(term._objValue) < 0) {
            if(term._strOperator.contains("<")) {
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }
    boolean isSqlTermsInBounds(SelectFromTableParams params) {
        SQLTerm term = params.getSqlTerms()[0];
        if(term._objValue instanceof DBAppNull) {
            return false;
        }
        boolean isInBounds = isSqlTermInBounds(term);
        for(int i = 1; i < params.getSqlTerms().length; i++) {
            SQLTerm currentTerm = params.getSqlTerms()[i];
            if(currentTerm._objValue instanceof DBAppNull) {
                return false;
            }
            isInBounds = Selection.getConditionResult(isInBounds, params.getLogicalOperators()[i - 1], isSqlTermInBounds(currentTerm));
        }
        return isInBounds;
    }

    @Override
    public String toString() {
        return "Boundary{" +
                "colNameMin=" + colNameMin +
                ", colNameMax=" + colNameMax +
                '}';
    }
}
