package org.example;

import java.util.HashSet;
import java.util.Set;

public record Client(String firstName, String lastName, String clientNumber, Set<Account> clientAccounts) {
    public Client(String firstName, String lastName, String clientNumber) {
        this(firstName, lastName, clientNumber, new HashSet<>());

    }
}
