package com.doxa.oauth2.components;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class DoxaWebAuthenticationDetails extends WebAuthenticationDetails {

    private String verificationCode;

    public DoxaWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        verificationCode = request.getParameter("code");
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
