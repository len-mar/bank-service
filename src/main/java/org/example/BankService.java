// TODO: add centwise split so that the bank doesn't keep anything,
//  but there's no more than a 1ct difference between new account balances
// TODO: add interest

package org.example;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

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

    // todo: implement transaction logic here as well
    public void transferFunds(String senderAccountNumber, String recipientAccountNumber, BigDecimal amount) {
        if(accounts.get(senderAccountNumber).getAccountBalance().compareTo(amount) < 0){
            System.out.println("Insufficient funds.");
        }
        else{
            accounts.get(senderAccountNumber).withdraw(amount);
            accounts.get(recipientAccountNumber).deposit(amount);
            System.out.println(amount + "€ successfully transferred.");
        }

    }

    // fixme: make sure bank doesn't keep any cents and
    //  account holders are ±1ct different in balance
    public List<String> split(String accountNumber) {
        // generates as many new accounts as there are clients
        int numberOfAccountHolders = accounts.get(accountNumber).getClients().size();
        // divides account balance by three and rounds down to the cent
        BigDecimal newAccountBalance = (accounts.get(accountNumber).getAccountBalance()
                .divide(BigDecimal.valueOf(numberOfAccountHolders), 2, RoundingMode.FLOOR));
        List<String> temp = new ArrayList<>();
        // creates one new account per client and sets new account balance for each
        for (Client c : accounts.get(accountNumber).getClients()) {
            Account newAccount = new Account(c);
            newAccount.setAccountNumber(RandomStringUtils.random(10, false, true));
            this.accounts.put(newAccount.getAccountNumber(), newAccount);
            c.clientAccounts().add(newAccount);
            newAccount.setAccountBalance(newAccountBalance);
            temp.add(newAccount.getAccountNumber());
        }
        // returns list of new account numbers
        return temp;
    }

    // todo: what's the difference between accounts and using getAccounts?
    public void creditInterest(String accountNumber, double interestRate){
        // calculates interest
        BigDecimal interest = accounts.get(accountNumber).getAccountBalance()
                .multiply(BigDecimal.valueOf(interestRate / 100));
        // credits interest
        accounts.get(accountNumber).setAccountBalance(accounts.get(accountNumber).getAccountBalance().add(interest));
        System.out.println(
                "Interest rate: " + interestRate +
                "\nInterest: " + interest +
                "\nUpdated balance: " + accounts.get(accountNumber).getAccountBalance());
    }


}
