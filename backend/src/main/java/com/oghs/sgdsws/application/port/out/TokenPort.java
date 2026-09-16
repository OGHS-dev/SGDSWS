package com.oghs.sgdsws.application.port.out;

public interface TokenPort {
    String issue(String username);

    long expirationTime();

    String username(String token);

    boolean valid(String token, String username);
}
