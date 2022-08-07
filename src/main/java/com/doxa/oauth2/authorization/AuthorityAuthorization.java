package com.doxa.oauth2.authorization;

import com.doxa.oauth2.DTO.authority.GrantUserAuthority;
import com.doxa.oauth2.common.DoxaAuthenticationManager;
import com.doxa.oauth2.config.Message;
import com.doxa.oauth2.exceptions.AccessDeniedException;
import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
import com.doxa.oauth2.responses.ApiResponse;
import com.doxa.oauth2.serviceImpl.AuthorityService;
import com.doxa.oauth2.services.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.validation.Valid;

@Service
public class AuthorityAuthorization implements IAuthorityService {
    private final static String FEATURE_MATRIX_READ = "featureMatrix:read";
    private final static String FEATURE_MATRIX_WRITE = "featureMatrix:write";
    private final static String MANAGE_COMPANY_USER_READ = "cpUser:read";

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private DoxaAuthenticationManager authenticationManager;

    /**
     * Role Allowed: DOXA_ADMIN
     * Get all the core modules
     *
     * @return ApiResponse
     */
//    @Override
//    public ApiResponse getAllCoreModules() throws AccessDeniedException {
//        if (authenticationManager.isDoxaAdmin()) {
//            return authorityService.getAllCoreModules();
//        }
//        throw new AccessDeniedException(Message.ACCESS_DENIED.getValue());
//    }
//
//    @Override
//    public ApiResponse getCompanyModules(String companyUuid) throws AccessDeniedException {
//        return authorityService.getCompanyModules(companyUuid);
//    }

    /**
     * Role Allowed: DOXA_ADMIN
     * Assign modules to company
     *
     * @param companyUuid company is assigned
     * @param moduleCodes list modules code
     * @return
     */
//    @Override
//    public ApiResponse assignModuleToCompany(String companyUuid, List<String> moduleCodes) throws AccessDeniedException {
//        if (authenticationManager.isDoxaAdmin()) {
//            return authorityService.assignModuleToCompany(companyUuid, moduleCodes);
//        }
//        throw new AccessDeniedException(Message.ACCESS_DENIED.getValue());
//    }

//    @Override
//    public ApiResponse getCompanySubscription(String companyUuid) throws AccessDeniedException {
//        return authorityService.getCompanySubscription(companyUuid);
//    }

    @Override
    public ApiResponse getUserAuthority(String companyUuid, String userUuid) throws AccessDeniedException, ObjectDoesNotExistException {
        // Company admin or user view their permission
        if (authenticationManager.hasAdminRole(companyUuid)
                || authenticationManager.hasAuthority(companyUuid, FEATURE_MATRIX_READ) // user who has feature matrix permission
                || authenticationManager.hasAuthority(companyUuid, MANAGE_COMPANY_USER_READ) // user who has manage users permission
                || authenticationManager.getUserIdentifier().equals(userUuid)) { // user view their authority
            return authorityService.getUserAuthority(companyUuid, userUuid);
        }
        throw new AccessDeniedException(Message.ACCESS_DENIED.getValue());
    }

    @Override
    public ApiResponse grantAuthorityToUser(String companyUuid, String userUuid, List<GrantUserAuthority> features) throws AccessDeniedException {
        if (authenticationManager.hasAdminRole(companyUuid)
                || authenticationManager.hasAuthority(companyUuid, FEATURE_MATRIX_WRITE)) {
            return authorityService.grantAuthorityToUser(companyUuid, userUuid, features);
        }
        throw new AccessDeniedException(Message.ACCESS_DENIED.getValue());
    }

    /**
     * Called privately
     * Check if user has permission on feature
     *
     * @param companyUuid Which user belongs to
     * @param userUuid    User's uuid
     * @param featureCode feature which we want to check
     * @return ApiResponse
     */
    @Override
    public ApiResponse checkUserPermission(String companyId, String userId, String featureCode) {
        return authorityService.checkUserPermission(companyId, userId, featureCode);
    }

//	@Override
//	public ApiResponse assignModuleToFinancialInstitution(String fiUuid, List<String> featureCodes) throws AccessDeniedException {
//        if (authenticationManager.isDoxaAdmin()) {
//            return authorityService.assignModuleToFinancialInstitution(fiUuid, featureCodes);
//        }
//        throw new AccessDeniedException(Message.ACCESS_DENIED.getValue());
//    }

//	@Override
//	public ApiResponse getFiSubscription(String fiUuid) throws AccessDeniedException {
//        return authorityService.getFiSubscription(fiUuid);
//        }

//	@Override
//	public ApiResponse grantUserAuthority(String fiUuid, String userUuid, List<GrantUserAuthority> features) {
//        return authorityService.grantUserAuthority(fiUuid, userUuid, features);
//        }

}
