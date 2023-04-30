package engine.operations.selection;

import engine.DBApp;
import engine.elements.Record;
import engine.exceptions.DBAppException;
import utilities.validation.SelectionValidator;

import java.io.PrintWriter;
import java.util.Iterator;

public class Selection {
    private final SelectFromTableParams params;
    private final Iterator<Record> selectIterator;
    public Selection(SelectFromTableParams params) {
        this.params = params;
        selectIterator = new LinearSelectionIterator(params, params.getTableNameObject());
    }
    public Iterator<Record> select() throws DBAppException {
        SelectionValidator.validate(params);
        return selectIterator;
    }
}