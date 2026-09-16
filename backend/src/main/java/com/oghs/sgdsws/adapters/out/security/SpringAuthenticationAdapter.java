package com.oghs.sgdsws.adapters.out.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.oghs.sgdsws.application.port.out.AuthenticationPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringAuthenticationAdapter implements AuthenticationPort {
    private final AuthenticationManager authenticationManager;

    @Override
    public String authenticate(String username, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        return authentication.getName();
    }
}
