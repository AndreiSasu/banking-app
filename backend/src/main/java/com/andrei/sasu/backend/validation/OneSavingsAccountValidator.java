package com.andrei.sasu.backend.validation;

import com.andrei.sasu.backend.exceptions.AccountAlreadyExistsException;
import com.andrei.sasu.backend.model.AccountType;
import com.andrei.sasu.backend.model.CreateAccountRequest;
import com.andrei.sasu.backend.model.entities.Account;
import com.andrei.sasu.backend.model.entities.User;
import com.andrei.sasu.backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class OneSavingsAccountValidator implements ConstraintValidator<OneSavingsAccountPerUser, CreateAccountRequest> {

    private UserRepository userRepository;
    protected UserDetails userDetails;

    public OneSavingsAccountValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void initialize(OneSavingsAccountPerUser constraintAnnotation) {

    }

    @Override
    @Transactional
    public boolean isValid(CreateAccountRequest createAccountRequest, ConstraintValidatorContext context) {
        if(AccountType.SAVINGS.equals(createAccountRequest.getAccountType())) {
            final String username = userDetails.getUsername();
            final User user = userRepository.findByUserName(username).orElseThrow(() -> new IllegalStateException("User name does not exist."));
            final Set<Account> accounts = user.getAccounts();
            if(!CollectionUtils.isEmpty(accounts)) {
                if (accounts.stream().anyMatch(account -> AccountType.SAVINGS.equals(account.getAccountType()))) {
                    throw new AccountAlreadyExistsException("Savings account already exists.");
                }
            }
        }
        return true;
    }
}
