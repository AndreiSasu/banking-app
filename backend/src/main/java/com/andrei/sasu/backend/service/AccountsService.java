package com.andrei.sasu.backend.service;

import com.andrei.sasu.backend.model.AccountDTO;
import com.andrei.sasu.backend.model.CreateAccountRequest;
import com.andrei.sasu.backend.validation.OneSavingsAccountPerUser;
import com.andrei.sasu.backend.validation.ValidWorkingHours;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AccountsService {
    AccountDTO createAccount(@ValidWorkingHours
                          @OneSavingsAccountPerUser final CreateAccountRequest createAccountRequest, final String userName);
}
