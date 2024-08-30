// TODO: Write a method public List<String> split(String accountNumber) in the service that splits an account equally.
//  From a joint account, individual accounts should be created for each account holder.
//  It should return the newly created account numbers.
//  Each account should receive the same amount of money after the split (+- 1 cent).
//  Make sure that the bank does not incur any cent gains or losses during the process.
// TODO: PS: as usual, our bank does not deal with half cents ;)
// TODO: Tip: Test Driven Development is also very useful for solving this task! (applies to the following tasks as well)
package org.example;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Account {
    private String accountNumber;
    private BigDecimal accountBalance;
    private ArrayList<Client> clients;
    private BankService bankService;

    public Account(Client client, BankService bankService) {
        // generates random String with size 10, no letters, just random numbers
        this.accountNumber = RandomStringUtils.random(10, false, true);

        // initializes balance at 0
        this.accountBalance = BigDecimal.valueOf(0);

        // creates new client (account holder) list for this account
        this.clients = new ArrayList<>();
        // adds this client to list
        clients.add(client);
        // adds this account to existing client
        client.clientAccounts().add(this);
        // adds account to BankService
        this.bankService = bankService;
        bankService.openAccount(this);

    }

    public void addClientToAccount(Client client) {
        clients.add(client);
        client.clientAccounts().add(this);
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

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClient(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public void deposit(BigDecimal amount) {
        accountBalance = accountBalance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        // if amount to withdraw is more than available, print error
        if (accountBalance.compareTo(amount) < 0) {
            System.out.println("Insufficient funds.");
        } else {
            accountBalance = accountBalance.subtract(amount);
        }
    }


}
