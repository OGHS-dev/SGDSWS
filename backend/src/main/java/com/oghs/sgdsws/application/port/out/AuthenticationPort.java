package com.oghs.sgdsws.application.port.out;

public interface AuthenticationPort {
    String authenticate(String username, String password);
}
