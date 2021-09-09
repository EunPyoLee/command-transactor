package ledger;

import transaction_command.TransCommand;

import java.util.ArrayList;
import java.util.List;


class CommitPoint{
    private int l;
    private int r;

    public CommitPoint(int l, int r){
        this.l = l;
        this.r = r;
    }
    // Inclusive
    public int getBeginIdx() {
        return l;
    }
    // Inclusive
    public int getEndIdx(){
        return r;
    }
}

// Transaction record will be written here
// is leveraging lazy init + double-checking lock singleton pattern
public class Ledger {
    private static volatile Ledger uniqLedger;
    List<TransCommand> writtenCommands;
    private List<CommitPoint> commitStack;

    private Ledger(){
        writtenCommands = new ArrayList<>();
        commitStack = new ArrayList<>();
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
        int begin = this.writtenCommands.size();
        this.writtenCommands.addAll(transToCommit);
        int end = this.writtenCommands.size() - 1;
        commitStack.add(new CommitPoint(begin, end));
    }

    public synchronized void printAfterLastCommit(){
        if(commitStack.isEmpty()){
            return;
        }
        int end = commitStack.get(commitStack.size() - 1).getEndIdx();
        int begin = commitStack.get(commitStack.size() - 1).getBeginIdx()
        for(int i = begin; i <= end; ++i){
            this.writtenCommands.get(i).print();
        }
    }

    public synchronized void printAllHistory(){
        for(TransCommand transCommand: this.writtenCommands){
            transCommand.print();
        }
    }
}
