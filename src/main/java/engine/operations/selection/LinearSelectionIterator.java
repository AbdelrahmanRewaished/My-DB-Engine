package engine.operations.selection;

import engine.elements.*;
import engine.elements.Record;

import java.util.*;


public class LinearSelectionIterator implements Iterator<Record> {

    private final Table selectedTable;
    private final SelectFromTableParams sp;
    private final TableRecordInfo tableRecordInfo;
    public LinearSelectionIterator(SelectFromTableParams sp, Table selectedTable) {
        this.sp = sp;
        this.selectedTable = selectedTable;
        tableRecordInfo = new TableRecordInfo(0, 0);
    }

    private boolean getConditionResult(boolean firstCondition, String logicalOperator, boolean secondCondition) {
        return switch (logicalOperator) {
            case "AND" -> firstCondition & secondCondition;
            case "OR" -> firstCondition | secondCondition;
            case "XOR" -> firstCondition ^ secondCondition;
            default -> false;
        };
    }
    private int getPrecedence(String operator) {
        return switch (operator) {
            case "AND" -> 1;
            case "XOR" -> 0;
            default -> -1;
        };
    }
    private List<Boolean> getSQLTermsEvaluationOfRecord(Record currentRecord, SQLTerm[] sqlTerms) {
        List<Boolean> sqlTermsEvaluations = new ArrayList<>();
        for(SQLTerm term: sqlTerms) {
            sqlTermsEvaluations.add(currentRecord.hasMatchingValues(term));
        }
        return sqlTermsEvaluations;
    }
    private boolean isMatchingRecord(Record currentRecord, SQLTerm[] sqlTerms) {
        List<Boolean> sqlTermsEvaluations = getSQLTermsEvaluationOfRecord(currentRecord, sqlTerms);
        Stack<Boolean> operands = new Stack<>();
        Stack<String> operators = new Stack<>();
        operands.push(sqlTermsEvaluations.remove(0));
        int logicalOperatorsIndex = 0;
        while(! sqlTermsEvaluations.isEmpty()) {
            String currentOperator = sp.getLogicalOperators()[logicalOperatorsIndex];
            boolean currentEvaluation = sqlTermsEvaluations.remove(0);
            while(! operators.isEmpty() && getPrecedence(currentOperator) <= getPrecedence(operators.peek())) {
                operands.push(getConditionResult(operands.pop(), operators.pop(), operands.pop()));
            }
            operands.push(currentEvaluation);
            operators.push(currentOperator);
            logicalOperatorsIndex++;
        }
        while(! operators.isEmpty()) {
            operands.push(getConditionResult(operands.pop(), operators.pop(), operands.pop()));
        }
        return operands.pop();
    }
    private TableRecordInfo getNextValidRecordInfo() {
        int currentPageInfoIndex = tableRecordInfo.getPageInfoIndex();
        int currentRecordIndex = tableRecordInfo.getRecordIndexInPage();
        if(tableRecordInfo.isInitialized()) {
            PageMetaInfo currentPageMetaInfo = selectedTable.getPagesInfo().get(currentPageInfoIndex);
            if(currentRecordIndex < currentPageMetaInfo.getCurrentNumberOfRecords()) {
                currentRecordIndex++;
            }
            else {
                currentRecordIndex = 0;
                currentPageInfoIndex++;
            }
        }
        while(currentPageInfoIndex < selectedTable.getPagesInfo().size()) {
            PageMetaInfo currentPageMetaInfo = selectedTable.getPagesInfo().get(currentPageInfoIndex);
            Page currentPage = Page.deserializePage(currentPageMetaInfo);
            while(currentRecordIndex < currentPage.size()) {
                Record currentRecord = currentPage.get(currentRecordIndex);
                if(isMatchingRecord(currentRecord, sp.getSqlTerms())){
                    return new TableRecordInfo(currentPageInfoIndex, currentRecordIndex);
                }
                currentRecordIndex++;
            }
            currentRecordIndex = 0;
            currentPageInfoIndex++;
        }
        // Record required is not found
        return null;
    }

    @Override
    public boolean hasNext() {
        return getNextValidRecordInfo() != null;
    }
    @Override
    public Record next() {
        TableRecordInfo nextRecordInfo = getNextValidRecordInfo();
        if(nextRecordInfo == null) {
            return null;
        }
        tableRecordInfo.saveNewState(nextRecordInfo);
        Page page = Page.deserializePage(selectedTable.getPagesInfo().get(nextRecordInfo.getPageInfoIndex()));
        return page.get(nextRecordInfo.getRecordIndexInPage());
    }
}
