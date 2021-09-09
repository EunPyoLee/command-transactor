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
    private int balance;

    private Ledger(){
        writtenCommands = new ArrayList<>();
        commitStack = new ArrayList<>();
        balance = 0;
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

    // For test suite usage only
    public static Ledger getLedgerTest(){
        return new Ledger();
    }

    public synchronized int write(List<TransCommand> transToCommit){
        int begin = this.writtenCommands.size();
        for(TransCommand tc: transToCommit){
            this.writtenCommands.add(tc);
            balance += tc.getAmount();
        }
        int end = this.writtenCommands.size() - 1;
        commitStack.add(new CommitPoint(begin, end));
        return commitStack.size() - 1;
    }

    public synchronized void printAfterLastCommit(){
        if(commitStack.isEmpty()){
            return;
        }
        int end = commitStack.get(commitStack.size() - 1).getEndIdx();
        int begin = commitStack.get(commitStack.size() - 1).getBeginIdx();
        for(int i = begin; i <= end; ++i){
            this.writtenCommands.get(i).print();
        }
    }

    public synchronized void printAllHistory(){
        for(TransCommand transCommand: this.writtenCommands){
            transCommand.print();
        }
    }

    public synchronized int reverseCommittedTransaction(int commitID){
        // use committed command's undo to write their reversing command on top of ledger
        int end = commitStack.get(commitID).getEndIdx();
        int begin = commitStack.get(commitID).getBeginIdx();
        List<TransCommand> undoCommands = new ArrayList<>();
        for(int i = begin; i <= end; ++i){
            undoCommands.add(this.writtenCommands.get(i).undo());
        }
        return this.write(undoCommands);
    }

    public int getBalance(){
        return balance;
    }

}
