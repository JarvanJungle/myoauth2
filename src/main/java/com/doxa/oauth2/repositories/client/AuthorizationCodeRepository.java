package com.doxa.oauth2.repositories.client;

import com.doxa.oauth2.models.AuthorizationCode;
import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorizationCodeRepository extends MongoRepository<AuthorizationCode, Long> {
    AuthorizationCode findByCode(String code);
//    @Modifying
    @Transactional
    void deleteByCode(String code);
}
