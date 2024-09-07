package org.example;

import lombok.Builder;
import lombok.With;

import java.util.HashSet;
import java.util.Set;

@With
public record Client(String firstName, String lastName, String clientNumber, Set<Account> clientAccounts) {
    public Client(String firstName, String lastName, String clientNumber) {
        this(firstName, lastName, clientNumber, new HashSet<>());
    }
}
