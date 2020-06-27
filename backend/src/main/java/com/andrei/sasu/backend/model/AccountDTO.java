package com.andrei.sasu.backend.model;

import java.util.Currency;

public class AccountDTO {
    private final String iban;
    private final Currency currency;
    private final AccountType accountType;

    public AccountDTO(String iban, Currency currency, AccountType accountType) {
        this.iban = iban;
        this.currency = currency;
        this.accountType = accountType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getIban() {
        return iban;
    }

    public Currency getCurrency() {
        return currency;
    }
}
