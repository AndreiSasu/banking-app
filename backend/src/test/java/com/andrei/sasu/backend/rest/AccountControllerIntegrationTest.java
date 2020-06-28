package com.andrei.sasu.backend.rest;

import com.andrei.sasu.backend.BackendApplication;
import com.andrei.sasu.backend.model.AccountType;
import com.andrei.sasu.backend.model.CreateAccountRequest;
import com.andrei.sasu.backend.model.entities.Account;
import com.andrei.sasu.backend.model.entities.User;
import com.andrei.sasu.backend.repository.UserRepository;
import com.andrei.sasu.backend.security.jwt.JWTUtils;
import com.andrei.sasu.backend.validation.WorkingDays;
import com.andrei.sasu.backend.validation.WorkingHours;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountControllerIntegrationTest {

    @Autowired
    private AccountController accountController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @MockBean
    private WorkingHours workingHours;

    @MockBean
    private WorkingDays workingDays;

    @BeforeEach
    public void setup() {
        final User userNoSavingsAccount = new User();
        userNoSavingsAccount.setUserName("user_no_account");
        userNoSavingsAccount.setPassword("password");

        final User userWithSavingsAccount = new User();
        userWithSavingsAccount.setUserName("user_savings_account");
        userWithSavingsAccount.setPassword("password");
        final Account account = new Account();
        account.setIban(faker.finance().iban());
        account.setAccountType(AccountType.SAVINGS);
        account.setCurrency(Currency.getInstance("USD"));
        account.setOwner(userWithSavingsAccount);
        userWithSavingsAccount.setAccounts(Set.of(account));

        userRepository.saveAndFlush(userWithSavingsAccount);
        userRepository.saveAndFlush(userNoSavingsAccount);

        MockitoAnnotations.initMocks(this);
        Mockito.when(workingHours.isOpen(Mockito.any(LocalDateTime.class))).thenReturn(true);
        Mockito.when(workingDays.isOpen(Mockito.any(LocalDateTime.class))).thenReturn(true);
    }


    @Test
    public void testCreateAccountUserHasNoSavingsAccount() throws Exception {
        final String jwtToken = generateTokenForUserName("user_no_account");

        final CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setCurrency(Currency.getInstance("USD"));
        createAccountRequest.setAccountType(AccountType.SAVINGS);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
                .content(objectMapper.writeValueAsString(createAccountRequest))
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.iban").isNotEmpty())//TODO: validate actual iban using regexp
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    public void testCreateAccountUserAlreadyHasSavingsAccount() throws Exception {
        final String jwtToken = generateTokenForUserName("user_savings_account");

        final CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setCurrency(Currency.getInstance("USD"));
        createAccountRequest.setAccountType(AccountType.SAVINGS);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
                .content(objectMapper.writeValueAsString(createAccountRequest))
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(StringContains.containsString("Only one savings account is allowed per user")))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.statusReason").value("Bad Request"));
    }

    @Test
    public void testInvalidWorkingHours() throws Exception {
        Mockito.when(workingHours.isOpen(Mockito.any(LocalDateTime.class))).thenReturn(false);
        Mockito.when(workingDays.isOpen(Mockito.any(LocalDateTime.class))).thenReturn(false);

        final String jwtToken = generateTokenForUserName("user_no_account");

        final CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setCurrency(Currency.getInstance("USD"));
        createAccountRequest.setAccountType(AccountType.SAVINGS);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
                .content(objectMapper.writeValueAsString(createAccountRequest))
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(StringContains.containsString("Invalid working hours.")))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.statusReason").value("Bad Request"));
    }

    private String generateTokenForUserName(final String userName) {
        final UserDetails noSavingsAccount = userDetailsService.loadUserByUsername(userName);
        return jwtUtils.generateJwtToken(noSavingsAccount);
    }
}
