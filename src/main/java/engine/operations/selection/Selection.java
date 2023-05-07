package engine.operations.selection;

import engine.DBApp;
import engine.elements.Record;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.serialization.Deserializer;
import utilities.validation.SelectionValidator;

import java.util.Iterator;

public class Selection {
    private final SelectFromTableParams params;
    private final Iterator<Record> selectIterator;
    public Selection(SelectFromTableParams params) {
        this.params = params;
        Table table = (Table) Deserializer.deserialize(DBApp.getTableInfoFileLocation(params.getSelectedTableName()));
        selectIterator = new LinearSelectionIterator(params, table);
    }
    public Iterator<Record> select() throws DBAppException {
        SelectionValidator.validate(params);
        return selectIterator;
    }
}