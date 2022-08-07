//package com.doxa.oauth2.repositories.financialInstitution;
//
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import org.springframework.data.repository.query.Param;
//
//import com.doxa.oauth2.models.financialInstitution.UserFinancialInstitution;
//
//public interface FiUserRepository extends MongoRepository<UserFinancialInstitution, Long> {
//
//	@Query("SELECT u FROM UserFinancialInstitution u WHERE u.financialInstitution.fiUuid = :fiUuid and u.user.uuid = :userUuid")
//	UserFinancialInstitution findOneByUserUuidAndfiUuid(@Param("fiUuid") String fiUuid, @Param("userUuid")String userUuid);
//
//}
