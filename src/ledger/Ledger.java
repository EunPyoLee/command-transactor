package ledger;

import transaction_command.TransCommand;

import java.util.ArrayList;
import java.util.List;

// Transaction record will be written here
// is leveraging lazy init + double-checking lock singleton pattern
public class Ledger {
    private static volatile Ledger uniqLedger;
    List<TransCommand> writtenCommands;
    private int lastReadIdx;

    private Ledger(){
        writtenCommands = new ArrayList<>();
        lastReadIdx = 0;
    }

    public static Ledger getLedger(){
        if(uniqLedger == null){
            synchronized (Ledger.class) {
                if(uniqLedger == null){
                    uniqLedger = new Ledger();
                }
            }
        }
        return uniqLedger;
    }

    public synchronized void write(List<TransCommand> transToCommit){
        this.writtenCommands.addAll(transToCommit);
    }

    public synchronized void printAfterLastCommit(){
        for(int i = lastReadIdx; i < this.writtenCommands.size(); ++i){
            this.writtenCommands.get(i).print();
        }
    }

    public synchronized void printAllHistory(){
        for(TransCommand transCommand: this.writtenCommands){
            transCommand.print();
        }
    }
}
