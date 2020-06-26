package com.andrei.sasu.backend.model;

import java.util.Currency;

public class AccountDTO {
    private final String iban;
    private final Currency currency;

    public AccountDTO(String iban, Currency currency) {
        this.iban = iban;
        this.currency = currency;
    }

    public String getIban() {
        return iban;
    }

    public Currency getCurrency() {
        return currency;
    }
}
