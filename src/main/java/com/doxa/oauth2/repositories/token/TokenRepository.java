package com.doxa.oauth2.repositories.token;

import com.doxa.oauth2.models.token.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository<T extends Token> extends MongoRepository<T, Long> {}
