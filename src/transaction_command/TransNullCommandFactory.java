package transaction_command;

public class TransNullCommandFactory implements TransactionCommandFactory{
    @Override
    public TransCommand createTransactionCommand(int amount) {
        return new NullTransCommand();
    }
}
