package com.andrei.sasu.backend.rest;

import com.andrei.sasu.backend.model.AccountDTO;
import com.andrei.sasu.backend.model.CreateAccountRequest;
import com.andrei.sasu.backend.security.AuthenticationFacade;
import com.andrei.sasu.backend.service.AccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AccountsService accountsService;
    private final AuthenticationFacade authenticationFacade;

    public AccountController(AccountsService accountsService, AuthenticationFacade authenticationFacade) {
        this.accountsService = accountsService;
        this.authenticationFacade = authenticationFacade;
    }

    //only basic validation is performed at controller level, business logic validation is performed in the service layer;
    @PostMapping(path = "/accounts")
    public ResponseEntity<AccountDTO> createAccount(final @Valid @RequestBody CreateAccountRequest createAccountRequest) {
        logger.debug("{}", createAccountRequest);
        return ResponseEntity.ok().body(accountsService.createAccount(createAccountRequest, authenticationFacade.getLoggedInUserName()));
    }
}
