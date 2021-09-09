package transaction_command;

public class TransInCommand extends TransCommand{
    public TransInCommand(int amount){
        this.amount = amount;
    }

    @Override
    public TransCommand execute() {
        return this;
    }

    @Override
    public TransCommand undo() {
        return new TransOutCommand(amount);
    }

    @Override
    public void print() {
        System.out.printf("Transfer IN: amount=[%d]\n", amount);
    }
}
