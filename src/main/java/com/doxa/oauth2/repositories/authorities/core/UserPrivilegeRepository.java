//package com.doxa.oauth2.repositories.authorities.core;
//
//import com.doxa.oauth2.models.authorities.core.UserPrivilege;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.jpa.repository.Modifying;
//
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//public interface UserPrivilegeRepository extends MongoRepository<UserPrivilege, Long> {
//    @Query("delete from UserPrivilege u where u.companyUuid = :companyUuid and u.userUuid = :userUuid")
//    @Modifying
//    @Transactional
//    void deleteAllByUserUuidAndCompanyUuid(String companyUuid, String userUuid);
//
//    @Query("select u from UserPrivilege u where u.userUuid = :userUuid and u.companyUuid = :companyUuid and u.featureCode in (select s.featureCode from Subscription s where s.companyUuid=:companyUuid)")
//    List<UserPrivilege> getUserPrivileges(String companyUuid, String userUuid);
//
//    @Query("select u from UserPrivilege u where u.userUuid = :userUuid and u.companyUuid = :companyUuid and u.featureCode = :featureCode")
//    UserPrivilege findByCompanyUuidAndUserUuid(String companyUuid, String userUuid, String featureCode);
//
//    @Query("select up from UserPrivilege up " +
//            "where lower(up.featureCode) = :featureCode " +
//            "and company_uuid = :companyUuid " +
//            "and (:read is null or up.actions.read = :read) " +
//            "and (:write is null or up.actions.write = :write) " +
//            "and (:approve is null or up.actions.approve = :approve) ")
//    List<UserPrivilege> getUserPrivilegeByCompanyUuidAndAction(String companyUuid, String featureCode, Boolean read, Boolean write, Boolean approve);
//
//    @Query("delete from UserPrivilege u where u.fiUuid = :fiUuid and u.userUuid = :userUuid")
//    @Modifying
//    @Transactional
//	void deleteAllByUserUuidAndfiUuid(String fiUuid, String userUuid);
//}
