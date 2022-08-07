package com.doxa.oauth2.config.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.Duration;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "auth-server")
public class AuthorizationServerConfigurationProperties {

    private URI issuer;
    private AccessToken accessToken;
    private IdToken idToken;
    private RefreshToken refreshToken;

    public enum TokenType {
        JWT,
        OPAQUE
    }

    public static abstract class Token {
        private Duration lifetime;

        public Duration getLifetime() {
            return lifetime;
        }

        public void setLifetime(Duration lifetime) {
            this.lifetime = lifetime;
        }
    }

    public static class AccessToken extends Token {
        private TokenType defaultFormat;

        public TokenType getDefaultFormat() {
            return defaultFormat;
        }

        public void setDefaultFormat(TokenType defaultFormat) {
            this.defaultFormat = defaultFormat;
        }
    }

    public static class IdToken extends Token {
    }

    public static class RefreshToken extends Token {
        private Duration maxLifetime;

        public Duration getMaxLifetime() {
            return maxLifetime;
        }

        public void setMaxLifetime(Duration maxLifetime) {
            this.maxLifetime = maxLifetime;
        }
    }
}
