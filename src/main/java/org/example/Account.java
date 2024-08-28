package org.example;

import java.math.BigDecimal;

public class Account {
    private String accountNumber;
    private BigDecimal accountBalance;
    private Client client;

    public Account(String accountNumber, BigDecimal accountBalance, Client client) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.client = client;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void deposit(BigDecimal amount){
        accountBalance = accountBalance.add(amount);
    }

    public void withdraw(BigDecimal amount){
        // if amount to withdraw is more than available, print error
        if(accountBalance.compareTo(amount) < 0){
            System.out.println("Insufficient funds.");
        }
        else{
            accountBalance = accountBalance.subtract(amount);
        }
    }
}
