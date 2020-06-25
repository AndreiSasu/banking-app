package com.andrei.sasu.backend.service;

import com.andrei.sasu.backend.model.CreateAccountRequest;
import com.andrei.sasu.backend.model.entities.Account;
import com.andrei.sasu.backend.model.entities.User;
import com.andrei.sasu.backend.repository.AccountRepository;
import com.andrei.sasu.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountsServiceImpl implements AccountsService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountsServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Account createAccount(CreateAccountRequest createAccountRequest, String userName) {
        final User user = this.userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with name %s does not exist.", userName)));

        final Account account = new Account();
        account.setAccountType(createAccountRequest.getAccountType());
        account.setCurrency(createAccountRequest.getCurrency());
        account.setIban("random_iban");
        account.setOwner(user);

        return accountRepository.save(account);
    }
}
