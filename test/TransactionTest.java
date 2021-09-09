import ledger.Ledger;
import ledger.Transactor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import transaction_command.TransInCommandFactory;
import transaction_command.TransOutCommandFactory;

public class TransactionTest {
    @Test
    public void testTransactionCommit(){
        Ledger uniqLedger = Ledger.getLedgerTest();
        Transactor transactor = new Transactor(uniqLedger);
        TransInCommandFactory inFactory = new TransInCommandFactory();
        TransOutCommandFactory outFactory = new TransOutCommandFactory();
        transactor.add(inFactory.createTransactionCommand(100)); //+100,  total: 100
        transactor.add(inFactory.createTransactionCommand(200)); //+200, total: 300
        transactor.add(outFactory.createTransactionCommand(250)); //-250, total 50
        int commitID = transactor.commit();
        Assertions.assertEquals(uniqLedger.getBalance(), 50, "Result of commitID: " + commitID + "\n");
        uniqLedger.printAfterLastCommit();
        System.out.println("-----------------");
        uniqLedger.printAllHistory();
    }
    @Test
    public void testTransactionCommitMulti(){
        Ledger uniqLedger = Ledger.getLedgerTest();
        Transactor transactor = new Transactor(uniqLedger);
        TransInCommandFactory inFactory = new TransInCommandFactory();
        TransOutCommandFactory outFactory = new TransOutCommandFactory();
        transactor.add(inFactory.createTransactionCommand(100)); //+100,  total: 100
        transactor.add(inFactory.createTransactionCommand(200)); //+200, total: 300
        transactor.add(outFactory.createTransactionCommand(250)); //-250, total 50
        int commitID = transactor.commit();
        Assertions.assertEquals(uniqLedger.getBalance(), 50, "Result of commitID: " + commitID + "\n");
        transactor.add(inFactory.createTransactionCommand(100)); //+100,  total: 150
        transactor.add(outFactory.createTransactionCommand(120)); //-120, total 30
        int commitID2 = transactor.commit();
        Assertions.assertEquals(uniqLedger.getBalance(), 30, "Result of commitID: " + commitID2 + "\n");
        uniqLedger.printAfterLastCommit();
        System.out.println("-----------------");
        uniqLedger.printAllHistory();
    }


    @Test
    public void testTransactionCommitRollback(){
        Ledger uniqLedger = Ledger.getLedgerTest();
        Transactor transactor = new Transactor(uniqLedger);
        TransInCommandFactory inFactory = new TransInCommandFactory();
        TransOutCommandFactory outFactory = new TransOutCommandFactory();
        transactor.add(inFactory.createTransactionCommand(100)); //+100,  total: 100
        transactor.add(inFactory.createTransactionCommand(200)); //+200, total: 300
        transactor.add(outFactory.createTransactionCommand(250)); //-250, total 50
        int commitID = transactor.commit();
        Assertions.assertEquals(uniqLedger.getBalance(), 50, "Result of commitID: " + commitID + "\n");
        transactor.add(inFactory.createTransactionCommand(100)); //+100,  total: 150
        transactor.add(outFactory.createTransactionCommand(120)); //-120, total 30
        transactor.rollback();
        Assertions.assertEquals(uniqLedger.getBalance(), 50, "Result of Rollback should stay at 50 still\n");
        uniqLedger.printAfterLastCommit();
        System.out.println("-----------------");
        uniqLedger.printAllHistory();
    }

    @Test
    public void testTransactionCommitReverse(){
        Ledger uniqLedger = Ledger.getLedgerTest();
        Transactor transactor = new Transactor(uniqLedger);
        TransInCommandFactory inFactory = new TransInCommandFactory();
        TransOutCommandFactory outFactory = new TransOutCommandFactory();
        transactor.add(inFactory.createTransactionCommand(100)); //+100,  total: 100
        transactor.add(inFactory.createTransactionCommand(200)); //+200, total: 300
        transactor.add(outFactory.createTransactionCommand(250)); //-250, total 50
        int commitID = transactor.commit();
        Assertions.assertEquals(uniqLedger.getBalance(), 50, "Result of commitID: " + commitID + "\n");
        transactor.add(inFactory.createTransactionCommand(100)); //+100,  total: 150
        transactor.add(outFactory.createTransactionCommand(120)); //-120, total 30
        int commitID2 = transactor.commit();
        Assertions.assertEquals(uniqLedger.getBalance(), 30, "Result of Rollback should stay at 50 still\n");
        uniqLedger.printAfterLastCommit();
        System.out.println("-----------------");
        uniqLedger.printAllHistory();
        int commitID3reverse = uniqLedger.reverseCommittedTransaction(commitID);
        Assertions.assertEquals(uniqLedger.getBalance(), -20, "Result of Reversing commitID=[" + commitID3reverse + "] should do -50 to our balance\n");
        uniqLedger.printAfterLastCommit();
        System.out.println("-----------------");
        uniqLedger.printAllHistory();
    }
}
