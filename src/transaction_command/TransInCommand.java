package transaction_command;

public class TransInCommand extends TransCommand{
    private TransCommand undoCommand;
    private TransNullCommandFactory nullCommandFactory = new TransNullCommandFactory();
    private TransInCommandFactory inCommandFactory = new TransInCommandFactory();
    private TransOutCommandFactory outCommandFactory = new TransOutCommandFactory();

    public TransInCommand(int amount){
        this.amount = amount;
        this.undoCommand = nullCommandFactory.createTransactionCommand(amount);

    }

    @Override
    public TransCommand execute() {
        undoCommand = outCommandFactory.createTransactionCommand(amount);
        return this;
    }

    @Override
    public TransCommand undo() {
        TransCommand ret = undoCommand;
        undoCommand = nullCommandFactory.createTransactionCommand(amount); // Allow undo only once
        return ret;
    }

    @Override
    public void print() {
        System.out.printf("Transfer IN: amount=[%d]\n", amount);
    }
}
