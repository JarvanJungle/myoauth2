package com.doxa.oauth2.repositories.user;

import com.doxa.oauth2.models.user.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoleRepository extends MongoRepository<Role, Long> {

//	Role findByRoleCode(String roleCode);
//
//	Role findByRoleName(String roleName);
	List<Role> findByCompanyId(String companyId);

}
