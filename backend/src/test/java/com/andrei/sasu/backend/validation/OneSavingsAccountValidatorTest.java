package com.andrei.sasu.backend.validation;


import com.andrei.sasu.backend.model.AccountType;
import com.andrei.sasu.backend.model.CreateAccountRequest;
import com.andrei.sasu.backend.model.entities.Account;
import com.andrei.sasu.backend.model.entities.User;
import com.andrei.sasu.backend.repository.UserRepository;
import com.andrei.sasu.backend.security.AuthenticationFacade;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Currency;
import java.util.Optional;
import java.util.Set;

public class OneSavingsAccountValidatorTest {

    @Mock
    private AuthenticationFacade authenticationFacade;

    @Mock
    private UserRepository userRepository;

    private OneSavingsAccountValidator oneSavingsAccountValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(authenticationFacade.getLoggedInUserName()).thenReturn("user");
        final User user = new User();
        user.setUserName("user");
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.of(user));

        this.oneSavingsAccountValidator = new OneSavingsAccountValidator(userRepository, authenticationFacade);
    }


    @Test
    public void verifyOneSavingsAccountPerUserHappyPath() {
        final CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountType(AccountType.SAVINGS);
        createAccountRequest.setCurrency(Currency.getInstance("USD"));
        Assertions.assertThat(oneSavingsAccountValidator.isValid(createAccountRequest, null)).isTrue();

    }

    @Test
    public void verifyOneSavingsAccountPerUserAlreadyHasSavingsAccount() {
        final User user = new User();
        user.setUserName("user");
        final Account account = new Account();
        account.setAccountType(AccountType.SAVINGS);
        final Set<Account> accounts = Set.of(account);
        user.setAccounts(accounts);
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.of(user));

        final CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountType(AccountType.SAVINGS);
        createAccountRequest.setCurrency(Currency.getInstance("USD"));
        Assertions.assertThat(oneSavingsAccountValidator.isValid(createAccountRequest, null)).isFalse();
    }

    @Test
    public void verifyOneSavingsAccountPerUserInvalidUser() {
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.empty());

        final CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountType(AccountType.SAVINGS);
        createAccountRequest.setCurrency(Currency.getInstance("USD"));

        final Throwable throwable = Assertions.catchThrowable(() -> oneSavingsAccountValidator.isValid(createAccountRequest, null));

        Assertions.assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User name does not exist.");
    }
}
