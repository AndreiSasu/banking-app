package com.andrei.sasu.backend.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CurrentAuthentication implements AuthenticationFacade {

    @Override
    public String getLoggedInUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return  ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }
}
