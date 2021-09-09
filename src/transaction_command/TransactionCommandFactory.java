package transaction_command;

public interface TransactionCommandFactory {
    public TransCommand createTransactionCommand(int amount);
}
