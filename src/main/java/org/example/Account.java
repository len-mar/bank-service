package org.example;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

public class Account {
    private String accountNumber;
    private BigDecimal accountBalance;
    private Client client;
    private BankService bankService;

    public Account(Client client, BankService bankService) {
        // generates random String with size 10, no letters, just random numbers
        this.accountNumber = RandomStringUtils.random(10, false, true);

        // initializes balance at 0
        this.accountBalance = BigDecimal.valueOf(0);

        // adds account to client
        this.client = client;
        client.clientAccounts().add(this);

        // add account to BankService
        this.bankService = bankService;
        bankService.openAccount(this);

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
