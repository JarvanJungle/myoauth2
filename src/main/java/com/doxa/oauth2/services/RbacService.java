package com.doxa.oauth2.services;

import com.doxa.oauth2.DTO.authority.CreateRoleDto;
import com.doxa.oauth2.responses.ApiResponse;

import java.util.List;

/**
 * @author vuducnoi
 */
public interface RbacService {
	/**
	 * Create new role
	 *
	 * @param companyUuid Current company
	 * @param roleDto     Role details
	 *
	 * @return ApiResponse
	 */
	ApiResponse createRole(String companyUuid, CreateRoleDto roleDto);

	/**
	 * Update an existing role
	 *
	 * @param companyUuid Current company
	 * @param roleUuid    Role uuid to update
	 * @param roleDto     Role details
	 *
	 * @return ApiResponse
	 */
	ApiResponse updateRole(String companyUuid, String roleUuid, CreateRoleDto roleDto);

	/**
	 * List all roles under the company
	 *
	 * @param companyUuid Current company
	 *
	 * @return List of Roles under company
	 */
	ApiResponse listRoles(String companyUuid);

	/**
	 * Get role details
	 *
	 * @param companyUuid Current company
	 * @param roleUuid    Current role
	 *
	 * @return ApiResponse
	 */
	ApiResponse roleDetails(String companyUuid, String roleUuid);

	/**
	 * Delete a role, if role is being used then it can't be deleted
	 *
	 * @param companyUuid Current company
	 * @param roleUuid    Role uuid to delete
	 *
	 * @return ApiResponse
	 */
	ApiResponse deleteRole(String companyUuid, String roleUuid);

	/**
	 * Assign list of role to user
	 *
	 * @param companyUuid Curren company
	 * @param userUuid    User to assign
	 * @param rbacRoles   List of role uuid
	 *
	 * @return ApiResponse
	 */
	ApiResponse assignRoleToUser(String companyUuid, String userUuid, List<String> rbacRoles);

	/**
	 * List all roles assigned to user
	 *
	 * @param companyUuid Current company
	 * @param userUuid    User
	 *
	 * @return ApiResponse
	 */
	ApiResponse listRoleOfUser(String companyUuid, String userUuid);

	/**
	 * Doxa admin will define the list of default role that can be used by all the entities under the system
	 *
	 * @param roleDto     Role details
	 *
	 * @return ApiResponse
	 */
	ApiResponse createDefaultRoles( CreateRoleDto roleDto);

	/**
	 * List all default roles
	 *
	 * @param companyUuid Doxa company
	 *
	 * @return List of default role
	 */
	ApiResponse listDefaultRole(String companyUuid);
}
