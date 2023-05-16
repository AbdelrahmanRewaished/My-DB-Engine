package engine.operations.selection;

import engine.DBApp;
import engine.elements.Record;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.serialization.Deserializer;
import utilities.validation.SelectionValidator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Selection {
    private final SelectFromTableParams params;
    private Iterator<Record> selectIterator;
    public Selection(SelectFromTableParams params) {
        this.params = params;
        Table table = (Table) Deserializer.deserialize(DBApp.getTableInfoFileLocation(params.getSelectedTableName()));
        if(params.isACompleteScanQuery() || ! params.isQueryCompatibleWithIndexing(table)) {
            selectIterator = new LinearSelectionIterator(params, table);
        }
        else {
            selectIterator = new IndexSelectionIterator(params, table);
        }
    }
    public Iterator<Record> select() throws DBAppException {
        SelectionValidator.validate(params);
        return selectIterator;
    }
    public static boolean getConditionResult(boolean firstCondition, String logicalOperator, boolean secondCondition) {
        return switch (logicalOperator) {
            case "AND" -> firstCondition & secondCondition;
            case "OR" -> firstCondition | secondCondition;
            case "XOR" -> firstCondition ^ secondCondition;
            default -> false;
        };
    }

}