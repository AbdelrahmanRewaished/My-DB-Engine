package engine.operations.update;

import engine.exceptions.DBAppException;

public interface UpdateStrategy {
    int updateTable() throws DBAppException;
}
