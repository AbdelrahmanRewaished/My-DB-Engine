package engine.operations.update;

import engine.exceptions.DBAppException;

public class UpdateWithIndex extends Update{

    public UpdateWithIndex(UpdateTableParams params) {
        super(params);
    }

    @Override
    public synchronized int updateTable() throws DBAppException {
        return super.updateTable();
    }
}
