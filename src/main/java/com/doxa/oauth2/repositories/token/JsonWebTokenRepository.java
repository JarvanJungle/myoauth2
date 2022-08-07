package com.doxa.oauth2.repositories.token;


import com.doxa.oauth2.models.token.JsonWebToken;

public interface JsonWebTokenRepository extends TokenRepository<JsonWebToken> {

  JsonWebToken findOneByValue(String value);

  JsonWebToken findOneByValueAndAccessToken(String value, boolean accessToken);
}
