package com.andrei.sasu.backend.rest;

import com.andrei.sasu.backend.BackendApplication;
import com.andrei.sasu.backend.TestUtils;
import com.andrei.sasu.backend.model.LoginRequest;
import com.andrei.sasu.backend.repository.UserRepository;
import com.andrei.sasu.backend.security.jwt.JWTUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestUtils testUtils;

    @Test
    public void testUserCanLogin() throws Exception {

        userRepository.saveAndFlush(testUtils.getUser("testuser", "test"));

        LoginRequest login = new LoginRequest();
        login.setUserName("testuser");
        login.setPassword("test");

        MvcResult result = mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isString())
                .andExpect(jsonPath("$.accessToken").isNotEmpty()).andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");

        Assertions.assertThat(jwtUtils.isValidToken(token)).isTrue();
    }

    @Test
    public void testInvalidLogin() throws Exception {
        LoginRequest login = new LoginRequest();
        login.setUserName("wrong-user");
        login.setPassword("wrong-password");

        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.accessToken").doesNotExist())
                .andExpect(header().doesNotExist("Authorization"));
    }
}
