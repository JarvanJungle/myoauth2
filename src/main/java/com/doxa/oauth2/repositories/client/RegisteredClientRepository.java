package com.doxa.oauth2.repositories.client;



import com.doxa.oauth2.models.client.RegisteredClient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface RegisteredClientRepository extends MongoRepository<RegisteredClient, String> {

//  Optional<RegisteredClient> findOneByClientId(String clientId);
//
//  Optional<RegisteredClient> findOneByIdentifier(UUID identifier);
//
//  void deleteOneByIdentifier(UUID identifier);
//
//  void deleteByClientId(String clientId);
}
