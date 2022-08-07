package com.doxa.oauth2.security.client;

import com.doxa.oauth2.models.client.RegisteredClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class RegisteredClientDetails extends RegisteredClient implements UserDetails {

    public RegisteredClientDetails(RegisteredClient registeredClient) {
        super(registeredClient.getId(),
                registeredClient.getClientSecret(), registeredClient.isConfidential(),
                registeredClient.getAccessTokenFormat(), registeredClient.getGrantTypes(),
                registeredClient.getRedirectUris());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        authorities.addAll(getGrantTypes().stream()
                .map(grantType -> new SimpleGrantedAuthority("ROLE_" + grantType.toUpperCase()))
                .collect(Collectors.toList()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return getClientSecret();
    }

    @Override
    public String getUsername() {
        return getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
