package com.andrei.sasu.backend.model.entities;

import com.andrei.sasu.backend.model.AccountType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Currency;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private AccountType accountType;

    @NotNull
    private Currency currency;

    @NotNull
    private String iban;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountType=" + accountType +
                ", currency=" + currency +
                ", iBan='" + iban + '\'' +
                '}';
    }
}
