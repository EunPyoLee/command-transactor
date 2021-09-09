package transaction_command;

public abstract class  TransCommand {
    protected int amount;

    public abstract void execute();

    public abstract void undo();

    public abstract void print();
}
