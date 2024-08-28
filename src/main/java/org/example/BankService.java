package org.example;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.Set;

public class BankService {
private Set<Account> accounts;

public String openAccount(Client client){
    // generates random String with size 10, no letters, just random numbers
    String newAccountNumber = RandomStringUtils.random(10, false, true);

    // creates new Account object with balance 0
    Account newAccount = new Account(newAccountNumber, BigDecimal.valueOf(0), client);

    // adds new account to account set
    accounts.add(newAccount);

    // adds account to client
    client.accounts().add(newAccount);

    // returns account number
    return newAccountNumber;
}

public void transferFunds(String recipientAccountNumber, String senderAccountNumber){
// todo: implement
}


}
