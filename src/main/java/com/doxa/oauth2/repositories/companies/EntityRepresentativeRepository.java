//package com.doxa.oauth2.repositories.companies;
//
//import com.doxa.oauth2.models.companies.EntityRepresentative;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//
//import java.util.Optional;
//
//public interface EntityRepresentativeRepository extends MongoRepository<EntityRepresentative, Long> {
//
//    @Query("SELECT e FROM EntityRepresentative  e WHERE e.userRole=:userRole AND e.entityId = :entityId")
//    Optional<EntityRepresentative> findByUserRoleAndEntityId(String userRole, Long entityId);
//
//}
