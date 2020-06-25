package com.andrei.sasu.backend.model;

import javax.validation.constraints.NotNull;
import java.util.Currency;

public class CreateAccountRequest {

    @NotNull
    private AccountType accountType;

    @NotNull
    private Currency currency;

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "CreateAccountRequest{" +
                "accountType=" + accountType +
                ", currency=" + currency +
                '}';
    }
}
