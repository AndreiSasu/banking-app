package com.andrei.sasu.backend.rest;

import com.andrei.sasu.backend.model.CreateAccountRequest;
import com.andrei.sasu.backend.model.CreateAccountResponse;
import com.andrei.sasu.backend.security.AuthenticationFacade;
import com.andrei.sasu.backend.service.AccountsService;
import com.andrei.sasu.backend.validation.OneSavingsAccountPerUser;
import com.andrei.sasu.backend.validation.ValidWorkingHours;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Validated
public class AccountController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AccountsService accountsService;
    private final AuthenticationFacade authenticationFacade;

    public AccountController(AccountsService accountsService, AuthenticationFacade authenticationFacade) {
        this.accountsService = accountsService;
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping(path = "/accounts")
    public ResponseEntity<CreateAccountResponse> createAccount(final @RequestBody @ValidWorkingHours @OneSavingsAccountPerUser CreateAccountRequest createAccountRequest) {
        return ResponseEntity.ok().body(new CreateAccountResponse());
//        throw new AccountAlreadyExistsException("Account already exists.");
    }
}
