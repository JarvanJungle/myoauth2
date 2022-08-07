package com.doxa.oauth2.common;

import com.doxa.oauth2.exceptions.BadRequestException;
import com.doxa.oauth2.serviceImpl.AuthorityService;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
@Slf4j
public class DoxaAuthenticationManager {
    private Map<String, Object> claims;
    private Collection<? extends GrantedAuthority> authorities;
    @Autowired
    private AuthorityService authorityService;

    public String getCurrentUserId() {
        initClaims();
        return (String) claims.get("user_id");
    }

    public String getUserIdentifier() {
        initClaims();
        return (String) claims.get("sub");
    }

    public String getByKey(String key) {
        initClaims();
        return (String) claims.get(key);
    }


    private JSONObject findCompanyByUuid(String compUuid) {
        JSONArray companies = getCompanies();
        for (Object company : companies) {
            JSONObject object = (JSONObject) company;
            String uuid = (String) object.get("uuid");
            if (uuid.equals(compUuid)) {
                return object;
            }
        }
        return null;
    }

    public boolean userInCompany(String companyUuid) {
        JSONObject company = findCompanyByUuid(companyUuid);
        return company != null;
    }

    private JSONArray getCompanies() {
        initClaims();
        return (JSONArray) claims.get("companies");
    }

    public boolean hasAdminRole(String compUuid) {
        JSONObject company = findCompanyByUuid(compUuid);
        if (company == null) {
            return false;
        }
        String roles = (String) company.get("roles");

        return roles.contains("ENTITY_ADMIN") || roles.contains("COMPANY_ADMIN");
    }

    public boolean isDoxaAdmin() {
        initClaims();
        String roles = (String) claims.get("roles");
        return roles.contains("DOXA_ADMIN");
    }
    
    public boolean isFiEntityAdmin() {
        initClaims();
        String roles = (String) claims.get("roles");
        return roles.contains("FI_ENTITY_ADMIN");
    }

    public boolean isEntityAdmin() {
        initClaims();
        String roles = (String) claims.get("roles");
        return roles.contains("ENTITY_ADMIN");
    }

    private void initClaims() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTClaimsSet principal = (JWTClaimsSet) authentication.getPrincipal();
        claims = principal.getClaims();
    }

    public boolean hasAuthority(String compUuid, String... authorities) {
        if (hasAdminRole(compUuid)) return true;
        String userUuid = (String) claims.get("sub");
        try {
            for (String authority : authorities)
                if (!authorityService.checkAuthority(compUuid, userUuid, authority))
                    return false;
        } catch (Exception ex) {
            throw new BadRequestException("Authorities happen error");
        }
        return true;
    }
}