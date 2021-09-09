package transaction_command;

public class TransInCommandFactory implements  TransactionCommandFactory{
    @Override
    public TransCommand createTransactionCommand(int amount) {
        return new TransInCommand(amount);
    }
}
