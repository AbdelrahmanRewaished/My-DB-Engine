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

    public static void main(String[] args) throws DBAppException {
        DBApp dbApp = new DBApp();
        SQLTerm[] terms = new SQLTerm[]{new SQLTerm("Manager", "id", "=", 2), new SQLTerm("Employee", "name", "=", "hossam")};
        String[] operators = new String[]{"AND"};
        Iterator iterator = dbApp.selectFromTable(terms, operators);
        PrintWriter pw = new PrintWriter(System.out);

        while(iterator.hasNext()) {
            Object record = iterator.next();
            pw.println(record);
        }
        pw.flush();
    }
}