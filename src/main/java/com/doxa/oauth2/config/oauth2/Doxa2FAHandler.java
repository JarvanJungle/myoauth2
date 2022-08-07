package com.doxa.oauth2.config.oauth2;

import com.doxa.oauth2.models.user.User;
import com.doxa.oauth2.security.user.EndUserDetails;
import com.doxa.oauth2.serviceImpl.PasswordResetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class Doxa2FAHandler implements AuthenticationSuccessHandler {

    @Autowired
    DefaultClient defaultClient;

    @Autowired
    private PasswordResetService passwordResetService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (EndUserDetails) authentication.getPrincipal();
        String email = user.getEmail();
        String redirectUrl = "/";

//        if (user.getSettings().isMustSetPassword()) {
//            String token = passwordResetService.generateRandomToken(255);
//            passwordResetService.saveResetPassword(token, email);
//            redirectUrl = resetPasswordUrl(token);
//        } else {
            RequestCache requestCache = new HttpSessionRequestCache();
            SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
            if (savedRequest != null) {
                redirectUrl = savedRequest.getRedirectUrl();
                log.info("Saved request: " + redirectUrl);
            } else {
                redirectUrl = httpServletRequest.getRequestURL().toString().replace("/login", "") + defaultRedirectUrl();
            }
//        }

//        if (user.getSettings().getIs2FA()) {
//            log.info("2FA is enabled");
//            redirectUrl = "multifactor";
//        } else {
//            log.info("2FA is disabled");
//            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
//                    authentication.getPrincipal(),
//                    authentication.getCredentials(),
//                    new ArrayList<>()
//            );
//            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
//        }
        new DefaultRedirectStrategy().sendRedirect(httpServletRequest, httpServletResponse, redirectUrl);
    }

    private String defaultRedirectUrl() {
        return "/authorize?client_id="
                + defaultClient.getClientId()
                + "&redirect_uri="
                + defaultClient.getRedirectUri()
                + "&response_type="
                + defaultClient.getResponseType()
                + "&scope="
                + defaultClient.getScope()
                + "&state=4q6tdX";
    }

    private String resetPasswordUrl(String token) {
        return "/resetpassword-token?token=" + token;
    }
}
