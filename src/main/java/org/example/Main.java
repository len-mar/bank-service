package org.example;

import java.math.BigDecimal;
import java.util.NoSuchElementException;


// todo: put this whole thing into a test
public class Main {
    public static void main(String[] args) {
        BankService bankService = new BankService();
        Client maxi = new Client("Maxi", "Mustermann", "001");
        Client bernd = new Client("Bernd", "Das-Brot", "002");


        String accountNumber = bankService.openAccount(maxi);
    Account account = bankService.getAccounts().values()
            .stream().filter(acc -> acc.getAccountNumber().equals(accountNumber))
            .findFirst().orElseThrow(NoSuchElementException::new);

    // tries to withdraw 10 (should fail and output error)
    bankService.withdraw(accountNumber, BigDecimal.valueOf(10));

      //  deposits 25 (should succeed)
        bankService.deposit(accountNumber, BigDecimal.valueOf(25));

        // prints all transactions
        account.printTransactions();

}}