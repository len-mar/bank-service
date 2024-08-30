package org.example;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankService {
    private Map<String, Account> accounts;

    public BankService() {
        this.accounts = new HashMap<>();
    }

    public String openAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
        return account.getAccountNumber();
    }

    public void transferFunds(String senderAccountNumber, String recipientAccountNumber, BigDecimal amount) {
        accounts.get(senderAccountNumber).withdraw(amount);
        accounts.get(recipientAccountNumber).deposit(amount);

    }

    public List<String> split(String accountNumber) {
        // generates as many new accounts as there are clients
        int numberOfAccountHolders = accounts.get(accountNumber).getClients().size();
        // divides account balance by three and rounds down to the cent
        BigDecimal newAccountBalance = (accounts.get(accountNumber).getAccountBalance()
                .divide(BigDecimal.valueOf(numberOfAccountHolders), 2, RoundingMode.FLOOR));
        List<String> temp = new ArrayList<>();
        // creates one new account per client and sets new account balance for each
        for (Client c : accounts.get(accountNumber).getClients()) {
            Account newAccount = new Account(c, this);
            newAccount.setAccountNumber(RandomStringUtils.random(10, false, true));
            this.accounts.put(newAccount.getAccountNumber(), newAccount);
            c.clientAccounts().add(newAccount);
            newAccount.setAccountBalance(newAccountBalance);
            temp.add(newAccount.getAccountNumber());
        }
        // returns list of new account numbers
        return temp;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

//    public void setAccounts(Map<String, Account> accounts) {
//        this.accounts = accounts;
//    }
}
