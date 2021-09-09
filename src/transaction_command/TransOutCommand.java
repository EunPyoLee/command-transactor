package transaction_command;

public class TransOutCommand extends TransCommand{
    public TransOutCommand(int amount){
        this.amount = -amount;
    }

    @Override
    public TransCommand execute() {
        return this;
    }

    @Override
    public TransCommand undo() {
        return new TransInCommand(amount);
    }

    @Override
    public void print() {
        System.out.printf("Transfer IN: amount=[%d]\n", -amount);
    }
}
