package ledger;

import transaction_command.TransCommand;

import java.util.ArrayList;
import java.util.List;

public class Transactor {
    private Ledger ledger;
    private List<TransCommand> tempCommands;
    public Transactor(Ledger l){
        ledger = l;
        tempCommands = new ArrayList<>();
    }

    public void add(TransCommand t){
        t.execute();
        tempCommands.add(t);
    }

    // Returns commit id
    public int commit(){
        System.out.println("Committing....");
        int commitID = ledger.write(tempCommands);
        System.out.println("Commit finish");
        tempCommands.clear();
        return commitID;
    }

    public void rollback(){
        System.out.println("Rollback triggered. No current transactions will be written to the ledger");
    }
}
