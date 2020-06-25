package com.andrei.sasu.backend.service;

import com.andrei.sasu.backend.model.CreateAccountRequest;
import com.andrei.sasu.backend.model.entities.Account;
import com.andrei.sasu.backend.model.entities.User;
import com.andrei.sasu.backend.repository.AccountRepository;
import com.andrei.sasu.backend.repository.UserRepository;
import com.andrei.sasu.backend.validation.OneSavingsAccountPerUser;
import com.andrei.sasu.backend.validation.ValidWorkingHours;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
public class AccountsServiceImpl implements AccountsService {

    private final Faker faker;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountsServiceImpl(Faker faker, AccountRepository accountRepository, UserRepository userRepository) {
        this.faker = faker;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Account createAccount(@ValidWorkingHours @OneSavingsAccountPerUser CreateAccountRequest createAccountRequest, String userName) {
        final User user = this.userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with name %s does not exist.", userName)));

        final Account account = new Account();
        account.setAccountType(createAccountRequest.getAccountType());
        account.setCurrency(createAccountRequest.getCurrency());
        account.setIban(faker.finance().iban());
        account.setOwner(user);

        return accountRepository.save(account);
    }
}
