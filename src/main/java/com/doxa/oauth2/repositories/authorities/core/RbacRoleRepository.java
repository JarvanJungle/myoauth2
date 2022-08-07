//package com.doxa.oauth2.repositories.authorities.core;
//
//import com.doxa.oauth2.enums.RbacRoleStatus;
//import com.doxa.oauth2.models.authorities.core.RbacRole;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import java.util.List;
//
///**
// * @author vuducnoi
// */
//public interface RbacRoleRepository extends MongoRepository<RbacRole, Long> {
//	List<RbacRole> findAllByStatusAndDeletedIsFalse(RbacRoleStatus reserved);
//
//	/**
//	 * Find all the roles by companyUuid
//	 *
//	 * @param companyUuid Company uuid
//	 *
//	 * @return List of Roles
//	 */
//	List<RbacRole> findAllByCompanyUuidAndDeletedIsFalse(String companyUuid);
//
//	List<RbacRole> findAllByCompanyUuidAndUuidInAndDeletedIsFalse(String companyUuid, List<String> rbacRoles);
//
//	RbacRole findByCompanyUuidAndUuidAndDeletedIsFalse(String companyUuid, String roleUuid);
//}
