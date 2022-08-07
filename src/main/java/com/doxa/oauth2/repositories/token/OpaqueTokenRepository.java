package com.doxa.oauth2.repositories.token;


import com.doxa.oauth2.models.token.OpaqueToken;

public interface OpaqueTokenRepository extends TokenRepository<OpaqueToken> {

  OpaqueToken findOneByValue(String value);

  OpaqueToken findOneByValueAndRefreshToken(String value, boolean refreshToken);

}
