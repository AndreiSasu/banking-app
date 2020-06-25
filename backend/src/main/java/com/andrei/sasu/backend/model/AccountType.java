package com.andrei.sasu.backend.model;

public enum AccountType {
    SAVINGS("SAVINGS");

    private final String name;

    AccountType(final String name) {
        this.name = name;
    }
}
