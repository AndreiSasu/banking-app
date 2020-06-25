package com.andrei.sasu.backend;

import com.andrei.sasu.backend.model.entities.User;
import com.andrei.sasu.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetupDemoUser implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SetupDemoUser(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final User user = new User();
        user.setUserName("user");
        user.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(user);
        logger.info("added test user");
    }
}
