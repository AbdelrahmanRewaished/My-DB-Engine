package engine.operations.selection;

import engine.elements.TableRecordInfo;

public class BinarySearchInitialization implements SelectionInitializationStrategy {

    private final SelectFromTableParams sp;

    public BinarySearchInitialization(SelectFromTableParams sp) {
        this.sp = sp;
    }


    @Override
    public TableRecordInfo getInitialTableRecordInfo() {
        return null;
    }
}
