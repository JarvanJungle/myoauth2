package com.doxa.oauth2.repositories.microfrontend;

import com.doxa.oauth2.models.microfrontend.MicroFrontEnd;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MicroFrontEndRepository extends MongoRepository<MicroFrontEnd, Long> {
}
