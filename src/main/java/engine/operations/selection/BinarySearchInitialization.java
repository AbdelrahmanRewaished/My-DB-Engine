package engine.operations.selection;

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
