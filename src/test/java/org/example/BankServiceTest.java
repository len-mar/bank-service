package org.example;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {

    @Test
    void transferFunds_shouldTransferCorrectAmount_WithTwoSingleAccountHolders() {
        // Given two clients with one account each
        // And one of them has an amount to transfer
        // And a bank service to conduct the transfer
        Client maxi = new Client("Maxi", "Mustermann", "001");
        Client bernd = new Client("Bernd", "Das-Brot", "002");

        BankService testBS = new BankService();

        Account senderAccount = new Account(maxi, testBS);
        BigDecimal amountToTransfer = BigDecimal.valueOf(10000);
        senderAccount.deposit(amountToTransfer);
        Account recipientAccount = new Account(bernd, testBS);


        // When all funds are transferred from one account to the other
        testBS.transferFunds(senderAccount.getAccountNumber(), recipientAccount.getAccountNumber(), amountToTransfer);

        // Then the sender should no longer have anything
        // And the recipient should have received the whole amount
        assertEquals(senderAccount.getAccountBalance(), BigDecimal.valueOf(0));
        assertEquals(recipientAccount.getAccountBalance(), amountToTransfer);
    }


    @Test
    void split_shouldSplitEquallyIntoTwoSeparateAccounts_whenAmountIsEven() {
        // Given two clients sharing the same account
        Client maxi = new Client("Maxi", "Mustermann", "001");
        Client bernd = new Client("Bernd", "Das-Brot", "002");

        BankService testBS = new BankService();

        Account jointAccount = new Account(maxi, testBS);
        jointAccount.addClientToAccount(bernd);

        // And the account has an even account balance
        BigDecimal amountToTransfer = BigDecimal.valueOf(10000);
        jointAccount.deposit(amountToTransfer);

        // When the account is split
        List<String> newAccNumbers = testBS.split(jointAccount.getAccountNumber());

        // Then there should be two new accounts with two different account numbers
        assertNotEquals(newAccNumbers.getFirst(), newAccNumbers.getLast());

        // And both accounts should have half of the original balance in each account
        assertEquals(testBS.getAccounts().get(newAccNumbers.getFirst()).getAccountBalance(), testBS.getAccounts().get(newAccNumbers.getLast()).getAccountBalance());
        assertEquals(testBS.getAccounts().get(newAccNumbers.getFirst()).getAccountBalance(), BigDecimal.valueOf(5000).setScale(2));
    }

    @Test
    void split_shouldSplitAlmostEquallyIntoTwoSeparateAccounts_whenAmountIsOdd() {
        // todo: remember to allow for a one cent difference
        // Given two clients sharing the same account
        Client maxi = new Client("Maxi", "Mustermann", "001");
        Client bernd = new Client("Bernd", "Das-Brot", "002");

        BankService testBS = new BankService();

        Account jointAccount = new Account(maxi, testBS);
        jointAccount.addClientToAccount(bernd);

        // And the account has an even account balance
        BigDecimal amountToTransfer = BigDecimal.valueOf(10000.05);
        jointAccount.deposit(amountToTransfer);

        // When the account is split
        List<String> newAccNumbers = testBS.split(jointAccount.getAccountNumber());

        // Then there should be two new accounts with two different account numbers
        assertNotEquals(newAccNumbers.getFirst(), newAccNumbers.getLast());

        // And both accounts should have a bit less than half of the original balance in each account
        assertEquals(testBS.getAccounts().get(newAccNumbers.getFirst()).getAccountBalance(), testBS.getAccounts().get(newAccNumbers.getLast()).getAccountBalance());
        assertEquals(testBS.getAccounts().get(newAccNumbers.getFirst()).getAccountBalance(), BigDecimal.valueOf(5000.02));
    }

    @Test
    void split_shouldSplitEquallyIntoThreeSeparateAccounts_whenAmountIsDivisibleByThree(){
        // Given three clients sharing the same account
        Client maxi = new Client("Maxi", "Mustermann", "001");
        Client bernd = new Client("Bernd", "Das-Brot", "002");
        Client bob = new Client("Bob", "Meister", "003");


        BankService testBS = new BankService();

        Account jointAccount = new Account(maxi, testBS);
        jointAccount.addClientToAccount(bernd);
        jointAccount.addClientToAccount(bob);

        // And the account has an even account balance
        BigDecimal amountToTransfer = BigDecimal.valueOf(12000.12);
        jointAccount.deposit(amountToTransfer);

        // When the account is split
        List<String> newAccNumbers = testBS.split(jointAccount.getAccountNumber());

        // Then there should be three new accounts with three different account numbers
        assertNotEquals(newAccNumbers.get(0), newAccNumbers.get(1));
        assertNotEquals(newAccNumbers.get(1), newAccNumbers.get(2));
        assertNotEquals(newAccNumbers.get(0), newAccNumbers.get(2));


        // And all three accounts should have a third of the original balance in each account
        assertEquals(testBS.getAccounts().get(newAccNumbers.get(0)).getAccountBalance(), testBS.getAccounts().get(newAccNumbers.get(1)).getAccountBalance());
        assertEquals(testBS.getAccounts().get(newAccNumbers.get(1)).getAccountBalance(), testBS.getAccounts().get(newAccNumbers.get(2)).getAccountBalance());
        assertEquals(testBS.getAccounts().get(newAccNumbers.get(0)).getAccountBalance(), testBS.getAccounts().get(newAccNumbers.get(2)).getAccountBalance());

        assertEquals(testBS.getAccounts().get(newAccNumbers.getFirst()).getAccountBalance(), BigDecimal.valueOf(4000.04));
    }

    @Test
    void split_shouldSplitAlmostEquallyIntoThreeSeparateAccounts_whenAmountIsNotDivisibleByThree(){
// todo: remember to allow for a one cent difference

        // Given three clients sharing the same account
        Client maxi = new Client("Maxi", "Mustermann", "001");
        Client bernd = new Client("Bernd", "Das-Brot", "002");
        Client bob = new Client("Bob", "Meister", "003");


        BankService testBS = new BankService();

        Account jointAccount = new Account(maxi, testBS);
        jointAccount.addClientToAccount(bernd);
        jointAccount.addClientToAccount(bob);

        // And the account has an even account balance
        BigDecimal amountToTransfer = BigDecimal.valueOf(13000);
        jointAccount.deposit(amountToTransfer);

        // When the account is split
        List<String> newAccNumbers = testBS.split(jointAccount.getAccountNumber());

        // Then there should be three new accounts with three different account numbers
        assertNotEquals(newAccNumbers.get(0), newAccNumbers.get(1));
        assertNotEquals(newAccNumbers.get(1), newAccNumbers.get(2));
        assertNotEquals(newAccNumbers.get(0), newAccNumbers.get(2));


        // And all three accounts should have a bit less than a third of the original balance in each account
        assertEquals(testBS.getAccounts().get(newAccNumbers.get(0)).getAccountBalance(), testBS.getAccounts().get(newAccNumbers.get(1)).getAccountBalance());
        assertEquals(testBS.getAccounts().get(newAccNumbers.get(1)).getAccountBalance(), testBS.getAccounts().get(newAccNumbers.get(2)).getAccountBalance());
        assertEquals(testBS.getAccounts().get(newAccNumbers.get(0)).getAccountBalance(), testBS.getAccounts().get(newAccNumbers.get(2)).getAccountBalance());

        assertEquals(testBS.getAccounts().get(newAccNumbers.getFirst()).getAccountBalance(), BigDecimal.valueOf(4333.33));
    }
}