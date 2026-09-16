package com.oghs.sgdsws.adapters.out.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.oghs.sgdsws.application.port.out.CurrentUserPort;

@Component
public class SpringCurrentUserAdapter implements CurrentUserPort {
    @Override
    public String username() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? "system" : authentication.getName();
    }
}
