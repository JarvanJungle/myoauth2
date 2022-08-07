package com.doxa.oauth2.repositories.user;

import com.doxa.oauth2.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmailAndStatus(String email, String status);


    Optional<User> findByEmail(String email);
//
//    @Query("SELECT du FROM DoxaUser du " +
//            "join UserCompanies uc on (du.id = uc.user.id) " +
//            "WHERE upper(du.email) = :email AND uc.companies.active = true AND du.isActive is true AND du.isDeleted is false")
//    Optional<DoxaUser> findValidUser(@Param("email") String email);
//    Boolean existsByEmail(String email);
//
//    //only retrieve ACTIVE users from the same entity
////    @Query("SELECT u FROM DoxaUser u WHERE u.entity.id = :id AND u.isDeleted = false")
////    List<DoxaUser> findUserByEntityId(@Param("id") Long id);
//
//    @Query("SELECT u FROM DoxaUser u WHERE u.uuid = :uuid AND u.isDeleted = false")
//    Optional<DoxaUser> findOneByIdentifier(@Param("uuid") String uuid);
////
//    @Query("SELECT u FROM DoxaUser u WHERE u.entity.id = :id AND u.isDeleted = false")
//    List<DoxaUser> findUserByEntityId(@Param("id") Long id);
//
//    @Query("SELECT u FROM DoxaUser u WHERE u.uuid = :uuid")
//    Optional<DoxaUser> findByUuid(@Param ("uuid") String uuid);
//
//    List<DoxaUser> findByUuidIn(List<String> uuids);
//
//    @Query("SELECT du FROM DoxaUser du " +
//            "join UserCompanies uc on (du.id = uc.user.id) " +
//            "WHERE uc.companies.uuid = :companyUuid AND du.uuid = :userUuid")
//    Optional<DoxaUser> findCompanyUser(@Param("companyUuid") String companyUuid, @Param("userUuid") String userUuid);
//
//    @Query("SELECT u FROM DoxaUser u WHERE u.fiUuid = :fiUuid")
//	Optional<DoxaUser> findByfiUuid(@Param ("fiUuid") String fiUuid);
//
//    @Query("SELECT p FROM DoxaUser p WHERE p.fiUuid=:fiuuid")
//    List<DoxaUser> findUserByFiUuid(@Param("fiuuid") String fiUuid);
}
