package com.oxygenxml.account.security;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.oxygenxml.account.exception.EmailNotConfirmedException;
import com.oxygenxml.account.type.UrlAnchor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadCredentialsException) {
            setDefaultFailureUrl(UrlAnchor.INVALID_USER.getAnchor());
        }   else if (exception instanceof EmailNotConfirmedException) {
            setDefaultFailureUrl(UrlAnchor.UNCONFIRMED_USER.getAnchor());
        }

        super.onAuthenticationFailure(request, response, exception);
    }
}
