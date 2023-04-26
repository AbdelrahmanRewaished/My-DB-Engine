package engine.operations.selection;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;

import java.util.*;


public class LinearSelectionIterator implements Iterator<Record> {

    private final Hashtable<String, Table> tableNameObject;
    private final SelectFromTableParams sp;
    private final SelectIteratorReferences references;
    public LinearSelectionIterator(SelectFromTableParams sp, Hashtable<String, Table> tableNameObject) {
        this.sp = sp;
        this.tableNameObject = tableNameObject;
        references = new SelectIteratorReferences(getInitializationStrategy());
    }

    private SelectionInitializationStrategy getInitializationStrategy() {
        return new LinearSearchInitialization(sp);
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
            case "XOR", "OR" -> 0;
            default -> -1;
        };
    }
    private List<Boolean> getSQLTermsEvaluationOfRecord(Record currentRecord, List<SQLTerm> sqlTerms) {
        List<Boolean> sqlTermsEvaluations = new ArrayList<>();
        for(SQLTerm term: sqlTerms) {
            sqlTermsEvaluations.add(currentRecord.hasMatchingValues(term));
        }
        return sqlTermsEvaluations;
    }
    private boolean isMatchingRecord(Record currentRecord, List<SQLTerm> sqlTerms) {
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
    private SelectIteratorReferences.TableRecordInfo getNextValidRecordInfo(Table table) {
        Hashtable<String, SelectIteratorReferences.TableRecordInfo> tablesRecordInfo = references.getCurrentTableDataInfo();
        SelectIteratorReferences.TableRecordInfo currentTableRecordInfo = tablesRecordInfo.get(table.getName());
        int currentPageInfoIndex = currentTableRecordInfo.getPageInfoIndex();
        int currentRecordIndex = currentTableRecordInfo.getRecordIndexInPage();
        if(currentTableRecordInfo.isInitialized()) {
            PageMetaInfo currentPageMetaInfo = table.getPagesInfo().get(currentPageInfoIndex);
            if(currentRecordIndex < currentPageMetaInfo.getCurrentNumberOfRecords()) {
                currentRecordIndex++;
            }
            else {
                currentRecordIndex = 0;
                currentPageInfoIndex++;
            }
        }
        List<SQLTerm> sqlTermList = sp.getCorrespondingTableSqlTerms(table.getName());
        while(currentPageInfoIndex < table.getPagesInfo().size()) {
            PageMetaInfo currentPageMetaInfo = table.getPagesInfo().get(currentPageInfoIndex);
            Page currentPage = Page.deserializePage(currentPageMetaInfo);
            while(currentRecordIndex < currentPage.size()) {
                Record currentRecord = currentPage.get(currentRecordIndex);
                if(isMatchingRecord(currentRecord, sqlTermList)){
                    return new SelectIteratorReferences.TableRecordInfo(currentPageInfoIndex, currentRecordIndex);
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
        int logicalOperatorIndex = 0;
        Boolean result = null;
        Hashtable<String, Boolean> tableHavingNextValidRecord = new Hashtable<>();
        for(SQLTerm term: sp.getSqlTerms()) {
            String tableName = term._strTableName;
            if(tableHavingNextValidRecord.containsKey(tableName)) {
                String logicalOperator = sp.getLogicalOperators()[logicalOperatorIndex++];
                result = getConditionResult(result, logicalOperator, tableHavingNextValidRecord.get(tableName));
                continue;
            }
            Table table = tableNameObject.get(tableName);
            if(result == null) {
                result = getNextValidRecordInfo(table) != null;
            }
            else {
                String logicalOperator = sp.getLogicalOperators()[logicalOperatorIndex++];
                result = getConditionResult(result, logicalOperator, getNextValidRecordInfo(table) != null);
            }
            tableHavingNextValidRecord.put(tableName, result);
        }
        return Boolean.TRUE.equals(result);
    }
    @Override
    public Record next() {
        Record resultingRecord = new Record();
        for(String tableName: tableNameObject.keySet()) {
            Table table = tableNameObject.get(tableName);
            SelectIteratorReferences.TableRecordInfo nextRecordInfo = getNextValidRecordInfo(table);
            if(nextRecordInfo == null) {
                return null;
            }
            references.saveNewTableDataInfo(tableName, nextRecordInfo);
            Page page = Page.deserializePage(table.getPagesInfo().get(nextRecordInfo.getPageInfoIndex()));
            resultingRecord.combineRecords(tableName, page.get(nextRecordInfo.getRecordIndexInPage()));
        }
        return resultingRecord;
    }
}
