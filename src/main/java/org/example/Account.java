package org.example;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

// todo: remove account balance from all thingies
@Getter
@Setter
public class Account {
    private String accountNumber;
    private BigDecimal accountBalance;
    private ArrayList<Client> clients = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Account(Client client) {
        // generates random uuid
        this.accountNumber = UUID.randomUUID().toString();

        // initializes balance at 0
//        this.accountBalance = BigDecimal.valueOf(0);
        // adds first transaction to set balance to null
        transactions.add(new Transaction(TransactionType.INITIAL, BigDecimal.ZERO,BigDecimal.ZERO));

        // creates new client (account holder) list for this account
        // and adds this (creating) client to list
        clients.add(client);

        // adds this account to client's account list
        // todo: is this necessary?
        client.clientAccounts().add(this);
    }

    public void addClientToAccount(Client client) {
        clients.add(client);
        client.clientAccounts().add(this);
    }

    public void deposit(BigDecimal amount) {
//        accountBalance = accountBalance.add(amount);
        transactions.add(new Transaction(TransactionType.DEPOSIT, amount, getAccountBalance().add(amount)));
    }

    public void creditInterest(BigDecimal amount) {
//        accountBalance = accountBalance.add(amount);
        transactions.add(new Transaction(TransactionType.INTEREST, amount, getAccountBalance().add(amount)));
    }

    public void withdraw(BigDecimal amount) {
        // if amount to withdraw is more than available, print error

        if (getAccountBalance().equals(BigDecimal.ZERO)) {
            System.out.println("Insufficient funds.");
        } else {
//            accountBalance = accountBalance.subtract(amount);
            transactions.add(new Transaction(TransactionType.WITHDRAW, amount, getAccountBalance().subtract(amount)));

            System.out.println(amount + "€ successfully withdrawn.");

        }
    }

    // fixme: make me less messy
    public BigDecimal getAccountBalance(){
        // gets newest timestamp
        Instant newest = transactions.stream().map(t -> t.timestamp()).max(Instant::compareTo).orElseThrow(NullPointerException::new);
        // gets transaction with newest timestamp
        Transaction transaction = transactions.stream().filter(t -> t.timestamp().equals(newest)).toList().getFirst();
        // returns newest transaction's balance
        return transaction.newBalance();
    }

    public void printTransactions(){
        transactions.forEach(System.out::println);
    }

}
