package com.oghs.sgdsws.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oghs.sgdsws.application.port.in.command.AuthenticateUserCommand;
import com.oghs.sgdsws.application.port.out.AuthenticationPort;
import com.oghs.sgdsws.application.port.out.TokenPort;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationPort authenticationPort;

    @Mock
    private TokenPort tokenPort;

    @Test
    void authenticatesUserThroughPortsAndReturnsToken() {
        when(authenticationPort.authenticate("alice", "password")).thenReturn("alice");
        when(tokenPort.issue("alice")).thenReturn("jwt-token");
        when(tokenPort.expirationTime()).thenReturn(300000L);

        var result = new AuthenticationService(authenticationPort, tokenPort)
                .authenticate(new AuthenticateUserCommand("alice", "password"));

        assertEquals("jwt-token", result.token());
        assertEquals(300000L, result.expiresIn());
        verify(authenticationPort).authenticate("alice", "password");
        verify(tokenPort).issue("alice");
    }
}
