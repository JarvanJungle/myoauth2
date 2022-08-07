//package com.doxa.oauth2.repositories.user;
//
//import com.doxa.oauth2.models.user.User;
//import com.doxa.oauth2.models.user.Role;
//import com.doxa.oauth2.models.user.UserRole;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface UserRoleRepository extends MongoRepository<UserRole, Long> {
//
//    @Query("SELECT u.role FROM UserRole u WHERE u.userCompanies.user.id = :id")
//    List<Role> findRoleByUserId(@Param("id") Long id);
//
//    //Only applicable to Entity Admin as One company can not have more than one EntityAdmin
//    @Query("SELECT u.userCompanies.user FROM UserRole u WHERE u.role.id = :roleid AND u.userCompanies.companies.id = :companyid")
//    User findUserByRoleIdCompanyId(@Param("roleid") Long roleid, @Param("companyid") Long companyid);
//
//    @Query("SELECT u.userCompanies.user FROM UserRole u WHERE u.userCompanies.user.entity.uuid = :entityuuid AND u.role.roleCode = :roleCode")
//    List<User> findUserByEntityIdAndRoleCode(@Param("roleCode") String roleCode, @Param("entityuuid") String entityuuid);
//
//    @Query("SELECT u FROM UserRole u WHERE u.role.id =:roleId AND u.userCompanies.id = :userCompanyId")
//    UserRole findUserRoleByRoleIdUserCompanyId(@Param("roleId") Long roleId, @Param("userCompanyId") Long userCompanyId);
//
//    @Query("SELECT u.role FROM UserRole u WHERE u.userCompanies.id = :usercompanyid")
//    List<Role> findRoleByUserCompanyId(@Param("usercompanyid") Long usercompanyid);
//
//    @Query("SELECT u FROM UserRole u WHERE u.userCompanies.id = :usercompanyid")
//    List<UserRole> findUserRoleByUserCompanyId(@Param("usercompanyid") Long usercompanyid);
//
//    @Query("SELECT u.role FROM UserRole u WHERE u.userCompanies.companies.uuid = :companyuuid AND u.userCompanies.user.uuid = :useruuid")
//    List<Role> findRoleByUserUUIDCompanyUUID(@Param("companyuuid") String companyuuid, @Param("useruuid") String useruuid);
//
//    @Query("SELECT u.userCompanies.user FROM UserRole u WHERE u.userCompanies.companies.uuid = :companyuuid AND u.role.roleCode = :roleCode")
//    List<User> findUsersByCompanyUuidAndRoleCode(@Param("companyuuid") String companyuuid, @Param("roleCode") String roleCode);
//
//    @Query("SELECT u.role FROM UserRole u WHERE u.userFinancialInstitution.user.uuid = :uuid")
//    List<Role> findRoleByFiUserUuid(@Param("uuid") String uuid);
//
//    @Query("SELECT u.role.roleCode FROM UserRole u WHERE u.role.roleCode = :roleCode AND u.userFinancialInstitution.user.uuid = :uuid")
//    String findRoleByFiUserUuid(@Param("roleCode") String roleCode,@Param("uuid") String uuid);
//
//}
