package com.andrei.sasu.backend.service;

import com.andrei.sasu.backend.model.CreateAccountRequest;
import com.andrei.sasu.backend.model.entities.Account;

public interface AccountsService {
    Account createAccount(final CreateAccountRequest createAccountRequest, final String userName);
}
