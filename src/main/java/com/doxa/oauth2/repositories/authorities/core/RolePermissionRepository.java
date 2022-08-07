//package com.doxa.oauth2.repositories.authorities.core;
//
//import com.doxa.oauth2.models.authorities.core.RbacRole;
//import com.doxa.oauth2.models.authorities.core.RolePermission;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.jpa.repository.Modifying;
//
//
//import java.util.List;
//
///**
// * @author vuducnoi
// */
//public interface RolePermissionRepository extends MongoRepository<RolePermission, Long> {
//    List<RolePermission> findAllByRole(RbacRole role);
//
//    @Modifying
//    @Query("DELETE FROM RolePermission rp WHERE rp.role.id=:id")
//    void removeAllByRole(Long id);
//
//    @Query("select rp from RolePermission rp " +
//            "where lower(rp.feature.featureCode) = :featureCode " +
//            "and (rp.role.companyUuid = :companyUuid or rp.role.companyUuid = '0')" +
//            "and rp.role.deleted = false " +
//            "and (:read is null or rp.read = :read) " +
//            "and (:write is null or rp.write = :write) " +
//            "and (:approve is null or rp.approve = :approve) ")
//    List<RolePermission> getRoleByCompanyUuidAndAction(String companyUuid, String featureCode, Boolean read, Boolean write, Boolean approve);
//}
