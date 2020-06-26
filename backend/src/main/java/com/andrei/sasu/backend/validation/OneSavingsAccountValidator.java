package com.andrei.sasu.backend.validation;

import com.andrei.sasu.backend.model.AccountType;
import com.andrei.sasu.backend.model.CreateAccountRequest;
import com.andrei.sasu.backend.model.entities.Account;
import com.andrei.sasu.backend.model.entities.User;
import com.andrei.sasu.backend.repository.UserRepository;
import com.andrei.sasu.backend.security.AuthenticationFacade;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class OneSavingsAccountValidator implements ConstraintValidator<OneSavingsAccountPerUser, CreateAccountRequest> {

    private UserRepository userRepository;
    private AuthenticationFacade authenticationFacade;
    public OneSavingsAccountValidator(UserRepository userRepository, AuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public void initialize(OneSavingsAccountPerUser constraintAnnotation) {

    }

    @Override
    @Transactional
    public boolean isValid(CreateAccountRequest createAccountRequest, ConstraintValidatorContext context) {
        if(AccountType.SAVINGS.equals(createAccountRequest.getAccountType())) {
            final String username = authenticationFacade.getLoggedInUserName();
            final User user = userRepository.findByUserName(username).orElseThrow(() -> new IllegalArgumentException("User name does not exist."));
            final Set<Account> accounts = user.getAccounts();
            if(!CollectionUtils.isEmpty(accounts)) {
                return accounts.stream().noneMatch(account -> AccountType.SAVINGS.equals(account.getAccountType()));
            }
        }
        return true;
    }
}
