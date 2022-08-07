package com.doxa.oauth2.components;


import com.doxa.oauth2.config.oauth2.AuthorizationServerConfigurationProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Map;

@Component
@Getter
@Setter
public class JwtPki {
  private static final String KEY_STORE_FILE = "jwks/doxa-jwt.jks";
  private static final String KEY_STORE_PASSWORD = "doxa-pass";
  private static final String KEY_ALIAS = "doxa-oauth-jwt";
  private static final String JWK_KID = "doxa-connex2";

  private RSAPublicKey publicKey;

  private JWKSet jwkSet;

  private JWSSigner signer;

  private JWSVerifier verifier;

  private final String issuer;

  public String getJwkKid() {
    return JWK_KID;
  }

  public JwtPki(AuthorizationServerConfigurationProperties authorizationServerProperties) {
    this.issuer = authorizationServerProperties.getIssuer().toString();
  }

  @PostConstruct
  public void initPki() throws JOSEException {
    this.publicKey = (RSAPublicKey) keyPair().getPublic();
    this.signer = new RSASSASigner(keyPair().getPrivate());
    this.jwkSet = jwkSet();
    this.verifier = new RSASSAVerifier(this.publicKey);
  }

  private JWKSet jwkSet() {
    RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic() ).keyUse(KeyUse.SIGNATURE)
            .algorithm(JWSAlgorithm.RS256)
            .keyID(JWK_KID);
    return new JWKSet(builder.build());
  }

  public KeyPair keyPair() {
    ClassPathResource ksFile = new ClassPathResource(KEY_STORE_FILE);
    KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, KEY_STORE_PASSWORD.toCharArray());
    return ksFactory.getKeyPair(KEY_ALIAS);
  }

}
