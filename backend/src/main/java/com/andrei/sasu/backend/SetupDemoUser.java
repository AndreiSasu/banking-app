package com.andrei.sasu.backend;

import com.andrei.sasu.backend.model.Roles;
import com.andrei.sasu.backend.model.entities.Role;
import com.andrei.sasu.backend.model.entities.User;
import com.andrei.sasu.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SetupDemoUser implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final String demoUserName;
    private final String demoPassword;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SetupDemoUser(final PasswordEncoder passwordEncoder, final UserRepository userRepository,
                         @Value("${app.demo.user}") final String demoUserName,
                         @Value("${app.demo.password}") final String demoPassword) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.demoUserName = demoUserName;
        this.demoPassword = demoPassword;
    }

    @Override
    public void run(ApplicationArguments args)  {
        final User user = new User();
        user.setUserName(demoUserName);
        user.setPassword(passwordEncoder.encode(demoPassword));
        final Role role = new Role();
        role.setName(Roles.USER.name());
        user.setRoles(Set.of(role));
        userRepository.save(user);
        logger.info("added demo user");
    }
}
