package transaction_command;

import ledger.Ledger;

import java.util.List;

public abstract class  TransCommand {
    protected int amount;
    public abstract TransCommand execute();

    public abstract TransCommand undo();

    public int getAmount(){
        return amount;
    }

    public abstract void print();
}
