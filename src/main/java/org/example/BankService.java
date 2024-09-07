// todo: add bi annual interest payments

package org.example;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class BankService {
    private Map<String, Account> accounts;

    public BankService() {
        this.accounts = new HashMap<>();
    }

    public String openAccount(Client client) {
        Account newAccount = new Account(client);
        accounts.put(newAccount.getAccountNumber(), newAccount);
        return newAccount.getAccountNumber();
    }

    public void transferFunds(String senderAccountNumber, String recipientAccountNumber, BigDecimal amount) {
        if (accounts.get(senderAccountNumber).getAccountBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient funds.");
        } else {
            accounts.get(senderAccountNumber).withdraw(amount);
            accounts.get(recipientAccountNumber).deposit(amount);
            System.out.println(amount + "€ successfully transferred.");
        }

    }

    public List<String> split(String accountNumber) {
        // generates as many new accounts as there are clients
        int numberOfAccountHolders = accounts.get(accountNumber).getClients().size();

        // gets remainder to be distributed afterwards
        BigDecimal remainder = (accounts.get(accountNumber).getAccountBalance()
                .remainder(BigDecimal.valueOf(numberOfAccountHolders, 2)));

        // divides account balance by number of accounts
        BigDecimal equalSplitBalance = (accounts.get(accountNumber).getAccountBalance()
                .divide(BigDecimal.valueOf(numberOfAccountHolders), 2, RoundingMode.FLOOR));
        List<String> temp = new ArrayList<>();
        // creates one new account per client and adds equal split balance first
        for (Client c : accounts.get(accountNumber).getClients()) {
            String newAccountNumber = this.openAccount(c);
            Account newAccount = accounts.get(newAccountNumber);
            newAccount.deposit(equalSplitBalance);
            temp.add(newAccountNumber);

        }
        // then adds remainder one by one until it's run out
        for (String acc : temp) {
            if (remainder.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
            accounts.get(acc).deposit(BigDecimal.valueOf(0.01));
            remainder = remainder.subtract(BigDecimal.valueOf(0.01));
        }

        // returns list of new account numbers
        return temp;
    }

    public void creditInterest(String accountNumber, double interestRate) {
        // calculates interest
        BigDecimal interest = accounts.get(accountNumber).getAccountBalance()
                .multiply(BigDecimal.valueOf(interestRate));
        // credits interest by calling account method
        accounts.get(accountNumber).creditInterest(interest);
        System.out.println(
                "Interest rate: " + interestRate +
                        "\nInterest: " + interest +
                        "\nUpdated balance: " + accounts.get(accountNumber).getAccountBalance());
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        accounts.get(accountNumber).deposit(amount);
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        accounts.get(accountNumber).withdraw(amount);
    }
}
