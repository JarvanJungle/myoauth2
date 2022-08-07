//package com.doxa.oauth2.repositories.authorities;
//
//
//import com.doxa.oauth2.models.authorities.Administratives;
//import com.doxa.oauth2.models.authorities.UserAdministratives;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface UserAdministrativesRepository extends MongoRepository<UserAdministratives, Long> {
//
//	 @Query("SELECT u FROM UserAdministratives u WHERE u.userCompanies.id = :usercompanyid")
//	 List<UserAdministratives> findUserAdministrativesByUserCompanyId(@Param("usercompanyid") Long usercompanyid);
//
//
//
//	@Query("SELECT DISTINCT u.userCompanies.user.uuid from UserAdministratives u WHERE u.userCompanies.companies.uuid =:companyUuid")
//	List<String> findSubAdminUserList(@Param("companyUuid") String companyUuid);
//
//	//return a list of permission for a userId
//	@Query("SELECT u.administratives FROM UserAdministratives u WHERE u.userCompanies.user.uuid =:userUuid")
//	List<Administratives> findSubAdminPermission(@Param("userUuid")String userUuid);
//
//	@Query("SELECT u.administratives FROM UserAdministratives u WHERE u.userCompanies.user.id =:userId")
//	List<Administratives> findSubAdminPermissionUsingId(@Param("userId")Long userId);
//
//	//return all the userAdministrative that contains the user
//	@Query("SELECT u FROM UserAdministratives u WHERE u.userCompanies.user.uuid =:userUuid")
//	List<UserAdministratives> findSubAdminRows(@Param("userUuid")String userUuid);
//
//	@Query("SELECT u.administratives FROM UserAdministratives u WHERE u.userCompanies.user.uuid =:userUuid AND u.userCompanies.companies.uuid= :companyUuid")
//	List<Administratives> findSubAdminPermissionUsingUuidAndCompanyUuid(@Param("userUuid")String userUuid,@Param("companyUuid")String companyUuid);
//
//
//
//
//}
