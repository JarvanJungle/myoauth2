package com.doxa.oauth2.services;

import com.doxa.oauth2.DTO.authority.GrantUserAuthority;
import com.doxa.oauth2.exceptions.AccessDeniedException;
import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
import com.doxa.oauth2.responses.ApiResponse;

import java.util.List;

public interface IAuthorityService {
    /**
     * Role Allowed: DOXA_ADMIN
     * Get all the core modules
     *
     * @return ApiResponse
     */
//    ApiResponse getAllCoreModules() throws AccessDeniedException;
//
//    ApiResponse getCompanyModules(String companyUuid) throws AccessDeniedException;

    /**
     * Role Allowed: DOXA_ADMIN
     * Assign modules to company
     *
     * @param companyUuid company is assigned
     * @param moduleCodes list modules code
     * @return
     */
//    ApiResponse assignModuleToCompany(String companyUuid, List<String> moduleCodes) throws AccessDeniedException;
//
//    ApiResponse getCompanySubscription(String companyUuid) throws AccessDeniedException;

    ApiResponse getUserAuthority(String companyUuid, String userUuid) throws AccessDeniedException, ObjectDoesNotExistException;

    ApiResponse grantAuthorityToUser(String companyUuid, String userUuid, List<GrantUserAuthority> features) throws AccessDeniedException;

    /**
     * Called privately
     * Check if user has permission on feature
     *
     * @param companyUuid Which user belongs to
     * @param userUuid    User's uuid
     * @param featureCode feature which we want to check
     * @return ApiResponse
     */
    ApiResponse checkUserPermission(String companyUuid, String userUuid, String featureCode);

	/**
	 * Role Allowed: DOXA_ADMIN
	 * Assign modules to company
	 *
	 * @param companyId  company is assigned
	 * @param featureCodes list modules code
	 * @return
	 * @throws AccessDeniedException 
	 */
//	ApiResponse assignModuleToFinancialInstitution(String fiUuid, List<String> featureCodes) throws AccessDeniedException;
	
//	ApiResponse getFiSubscription(String fiUuid) throws AccessDeniedException;
	
//    ApiResponse grantUserAuthority(String fiUuid, String userUuid, List<GrantUserAuthority> features);
}
