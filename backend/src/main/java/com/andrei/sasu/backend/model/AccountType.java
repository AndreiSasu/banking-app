package com.andrei.sasu.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccountType {
    SAVINGS("SAVINGS");

    private final String name;

    AccountType(final String name) {
        this.name = name;
    }
}
