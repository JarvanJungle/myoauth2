//package com.doxa.oauth2.repositories.user;
//
//import com.doxa.oauth2.models.authorities.core.RbacRole;
//import com.doxa.oauth2.models.user.RbacUserRole;
//import com.doxa.oauth2.models.user.Role;
//import com.doxa.oauth2.models.user.UserCompanies;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
///**
// * @author vuducnoi
// */
//public interface UserRbacRoleRepository extends MongoRepository<RbacUserRole, Long> {
//
//    void deleteAllByUserCompanies(UserCompanies userCompanies);
//
//    List<RbacUserRole> findAllByRole(RbacRole role);
//
//    List<RbacUserRole> findAllByRole_UuidIn(List<String> rolesUuid);
//
//    List<RbacUserRole> findAllByUserCompanies(UserCompanies userCompanies);
//
//    @Query("SELECT u.role FROM RbacUserRole u WHERE u.userCompanies.user.id = :id")
//    List<Role> findRoleByUserId(@Param("id") Long id);
//
//}
