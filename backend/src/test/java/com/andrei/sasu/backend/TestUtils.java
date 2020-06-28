package com.andrei.sasu.backend;

import com.andrei.sasu.backend.model.Roles;
import com.andrei.sasu.backend.model.entities.Role;
import com.andrei.sasu.backend.model.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TestUtils {

    private PasswordEncoder passwordEncoder;

    public TestUtils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(final String userName, final String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        Role role = new Role();
        role.setName(Roles.USER.name());
        user.setRoles(Set.of(role));
        return user;
    }
}
