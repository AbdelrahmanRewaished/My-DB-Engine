package engine.operations.deletion;

import engine.exceptions.DBAppException;

public class DeletionWithIndex extends Deletion{

    public DeletionWithIndex(DeleteFromTableParams params) {
        super(params);
    }
    @Override
    public synchronized int deleteFromTable() throws DBAppException {
        return super.deleteFromTable();
    }
}
