package com.allaroundjava.authentication;

import com.allaroundjava.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalFinder {
    public User getAuthenticatedUser() {
        return ((DhUserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
    }
}
