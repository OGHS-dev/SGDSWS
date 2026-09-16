package com.oghs.sgdsws.application.port.in.result;

public record AuthenticationResult(String token, long expiresIn) {
}
