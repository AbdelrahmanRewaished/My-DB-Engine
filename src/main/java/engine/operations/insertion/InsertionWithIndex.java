package engine.operations.insertion;

import engine.exceptions.DBAppException;

public class InsertionWithIndex extends Insertion{
    public InsertionWithIndex(InsertIntoTableParams params) {
        super(params);
    }

    @Override
    public synchronized void insertIntoTable() throws DBAppException {
        super.insertIntoTable();
    }
}
