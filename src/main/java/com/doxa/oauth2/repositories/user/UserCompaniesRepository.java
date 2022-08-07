//package com.doxa.oauth2.repositories.user;
//
//
//import com.doxa.oauth2.models.user.User;
//import com.doxa.oauth2.models.user.UserCompanies;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//import java.util.Optional;
//
//
//public interface UserCompaniesRepository extends MongoRepository<UserCompanies, Long> {
//
//	@Query("SELECT u.user FROM UserCompanies u WHERE u.companies.id = :companyId")
//	List<User> findUserByCompanyId(@Param("companyId") Long companyId);
//
//	@Query("SELECT u, c.entityName FROM UserCompanies u join Companies c on (c.id = u.companies.id) WHERE u.user.id = :id")
//	List<UserCompanies> findUserCompaniesByUserId(@Param("id") Long id);
//
//	@Query("SELECT u.user.uuid FROM UserCompanies u WHERE u.companies.id = :companyId")
//	List<Long> findUserUuidIdByCompaniesId(@Param("companyId") Long companyId);
//
//	@Query("SELECT u FROM UserCompanies u WHERE u.companies.id =:companyId AND u.user.id =:userId")
//	Optional<UserCompanies> findUserCompaniesIdByCompaniesIdAndUserId(@Param("companyId") Long companyId, @Param("userId") Long userId);
//
//
//	@Query("SELECT u FROM UserCompanies u WHERE u.companies.uuid = :companyUuid and u.user.uuid = :userUuid")
//	UserCompanies findOneByUserUuidAndCompanyUuid(@Param("companyUuid") String companyUuid, @Param("userUuid") String userUuid);
//
//	@Query("SELECT u.user FROM UserCompanies u join User du on (du.id = u.user.id) WHERE u.companies.id = :companyId AND du.email= :email")
//	Optional<User> findUserByCompanyIdAndUserEmail(@Param("companyId") Long companyId, @Param("email") String email);
//
//}
