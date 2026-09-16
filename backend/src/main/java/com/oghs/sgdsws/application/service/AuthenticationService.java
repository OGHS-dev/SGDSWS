package com.oghs.sgdsws.application.service;

import org.springframework.stereotype.Service;

import com.oghs.sgdsws.application.port.in.AuthenticateUserUseCase;
import com.oghs.sgdsws.application.port.in.command.AuthenticateUserCommand;
import com.oghs.sgdsws.application.port.in.result.AuthenticationResult;
import com.oghs.sgdsws.application.port.out.AuthenticationPort;
import com.oghs.sgdsws.application.port.out.TokenPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticateUserUseCase {
    private final AuthenticationPort authenticationPort;
    private final TokenPort tokenPort;

    @Override
    public AuthenticationResult authenticate(AuthenticateUserCommand command) {
        String username = authenticationPort.authenticate(command.username(), command.password());
        return new AuthenticationResult(tokenPort.issue(username), tokenPort.expirationTime());
    }
}
