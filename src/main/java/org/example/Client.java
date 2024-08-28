package org.example;

import java.util.Set;

public record Client(String firstName, String lastName, String clientNumber, Set<Account> accounts) {

}
