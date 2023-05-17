package com.oghs.sgdsws.util;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessMesage extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SessionFlashMapManager sfmm = new SessionFlashMapManager();
        FlashMap fm = new FlashMap();

        fm.put("success", "Sesión iniciada con éxito, bienvenido " + authentication.getName());
        sfmm.saveOutputFlashMap(fm, request, response);

        super.onAuthenticationSuccess(request, response, authentication);
    }
    
}
