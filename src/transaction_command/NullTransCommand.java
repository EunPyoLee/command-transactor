package transaction_command;

public class NullTransCommand extends TransCommand{
    @Override
    public TransCommand execute() {
        return this;
    }

    @Override
    public TransCommand undo() {
        return this;
    }

    @Override
    public void print() {
        System.out.println("This is null transaction command");
    }
}
