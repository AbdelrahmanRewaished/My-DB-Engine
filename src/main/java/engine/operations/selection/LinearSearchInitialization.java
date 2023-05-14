package engine.operations.selection;

import engine.elements.TableRecordInfo;

public class LinearSearchInitialization implements SelectionInitializationStrategy {
    private final SelectFromTableParams sp;

    public LinearSearchInitialization(SelectFromTableParams sp) {
        this.sp = sp;
    }
    @Override
    public TableRecordInfo getInitialTableRecordInfo() {
        return new TableRecordInfo(0, 0);
    }
}
