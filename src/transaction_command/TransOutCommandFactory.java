package transaction_command;

public class TransOutCommandFactory implements TransactionCommandFactory{
    @Override
    public TransCommand createTransactionCommand(int amount) {
        return new TransOutCommand(amount);
    }
}
