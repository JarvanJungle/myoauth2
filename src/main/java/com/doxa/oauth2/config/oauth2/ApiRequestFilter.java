package com.doxa.oauth2.config.oauth2;

import com.doxa.oauth2.serviceImpl.JsonWebTokenService;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;

@Slf4j
public class ApiRequestFilter extends OncePerRequestFilter {
    private JsonWebTokenService jsonWebTokenService;

    public ApiRequestFilter(JsonWebTokenService jsonWebTokenService) {
        this.jsonWebTokenService = jsonWebTokenService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || request.getRequestURI().contains("private")) {
            log.debug("Header Authorization is null");
            filterChain.doFilter(request, response);        // If not valid, go to the next filter.
            return;
        }
        String token = header.replace("Bearer ", "");
        boolean valid = jsonWebTokenService.isSignatureValid(token);
        if (!valid) {
            throw new AccessDeniedException("Invalid token");
        }
        // Get claims
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token);
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(signedJWT.getJWTClaimsSet(), null, new ArrayList<>());
//        authenticationToken.setAuthenticated(true);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.debug("Authenticated");
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            throw e;
        }


    }
}
