package com.doxa.oauth2.serviceImpl;

import com.doxa.oauth2.models.AuthorizationCode;
import com.doxa.oauth2.repositories.client.AuthorizationCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class AuthorizationCodeService {
  @Autowired
  private AuthorizationCodeRepository authorizationCodeRepository;
  private final Map<String, AuthorizationCode> codeMap = new HashMap<>();

  public AuthorizationCode getCode(String code) {
    log.info("Getting " + code);
    log.info(codeMap.toString());
//    return codeMap.get(code);
    return authorizationCodeRepository.findByCode(code);
  }

  public AuthorizationCode createAndStoreAuthorizationState(
          String clientId,
          URI redirectUri,
          Set<String> scopes,
          String userId,
          String nonce,
          String code_challenge,
          String code_challenge_method) {
    String code = RandomStringUtils.random(32, true, true);

    AuthorizationCode authorizationCode =
            new AuthorizationCode(
                    clientId,
                    redirectUri,
                    scopes,
                    code,
                    userId,
                    nonce,
                    code_challenge,
                    code_challenge_method);
//    codeMap.put(code, authorizationCode);
//    log.info(codeMap.toString());
    authorizationCode = authorizationCodeRepository.save(authorizationCode);
    return authorizationCode;
  }

  public void removeCode(String code) {
    log.info("Removing " + code);
    log.info(codeMap.toString());
//    codeMap.remove(code);
    authorizationCodeRepository.deleteByCode(code);
  }
}
