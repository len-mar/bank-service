package org.example;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BankService {
private Map<String,Account> accounts;

public BankService(){
    this.accounts = new HashMap<>();
}

public String openAccount(Account account){
//    Account newAccount = new Account(client, this);
    accounts.put(account.getAccountNumber(), account);
    return account.getAccountNumber();
}

public void transferFunds(String senderAccountNumber, String recipientAccountNumber, BigDecimal amount){
// todo: implement from one account to the other via number
    // todo: find accounts by number and store them somewhere
    // todo: access sender and recipient account balances and modify them
    // später noch der check mit genug cash aber hey
    accounts.get(senderAccountNumber).withdraw(amount);
    accounts.get(recipientAccountNumber).deposit(amount);

}

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }
}
