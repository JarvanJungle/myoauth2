package com.doxa.oauth2.serviceImpl;

import com.doxa.oauth2.DTO.manageCompany.CompanyDetailDto;
import com.doxa.oauth2.DTO.privateAPI.Company;
//import com.doxa.oauth2.models.authorities.core.RolePermission;
//import com.doxa.oauth2.models.authorities.core.UserPrivilege;
//import com.doxa.oauth2.repositories.authorities.core.RolePermissionRepository;
//import com.doxa.oauth2.repositories.authorities.core.UserPrivilegeRepository;
//import com.doxa.oauth2.repositories.user.UserRbacRoleRepository;
import com.doxa.oauth2.responses.ApiResponse;
import com.doxa.oauth2.services.IPrivateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PrivateService implements IPrivateService {

    @Autowired
    private CompaniesServiceImpl companiesService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private UserPrivilegeRepository userPrivilegeRepository;

//    @Autowired
//    private RolePermissionRepository rolePermissionRepository;

//    @Autowired
//    private UserRbacRoleRepository rbacRoleRepository;

    /**
     * Get company details privately
     * IMPORTANT NOTE: This function is used by other microservices to retrieve company information,
     * be careful when you change the output
     *
     * @param companyUuid User uuid
     * @return Company details
     */
    @Override
    public ApiResponse getCompanyDetails(String companyUuid) {
        ApiResponse apiResponse = companiesService.getCompanyDetails(companyUuid);
        CompanyDetailDto company = (CompanyDetailDto) apiResponse.getData();
        Company returnCompany = new Company();
        returnCompany.setCompanyName(company.getEntityName());
        returnCompany.setGstRegNo(company.getGstNo());
        returnCompany.setUen(company.getCompanyRegistrationNumber());
        returnCompany.setCountryOfOrigin(company.getCountry());
        returnCompany.setUuid(company.getUuid());
        returnCompany.setLogo(company.getLogoUrl());
        returnCompany.setContactPersonEmail(company.getContactPersonEmail());
        returnCompany.setContactPersonWorkNumber(company.getContactPersonWorkNumber());
        returnCompany.setContactPersonName(company.getContactPersonName());
        returnCompany.setCountryCode(company.getCountryCode());
        apiResponse.setData(returnCompany);
        return apiResponse;
    }

    /**
     * Get user details privately
     * IMPORTANT NOTE: This function is used by other microservices to retrieve user information,
     * be careful when you change the output
     *
     * @param userUuid User uuid
     * @return User details
     */
    @Override
    public ApiResponse getUserDetails(String userUuid) {
        log.info("Getting user details privately {}", userUuid);
        return userService.getUserDetails(userUuid);
    }

    /**
     * Get user rbac roles privately
     * IMPORTANT NOTE: This function is used by other microservices to retrieve user information,
     * be careful when you change the output
     *
     * @param userUuids
     * @param companyUuid
     * @return
     */
    @Override
    public ApiResponse getUserRbacRoles(List<String> userUuids, String companyUuid) {
        log.info("Getting user rbac roles privately {}", userUuids);
        return userService.getUserRbacRoles(userUuids, companyUuid);
    }

    /**
     * Get user email privately
     * NOTE: This function is used by other microservices to retrieve user email only.
     *
     * @param uuids users' uuid
     * @return Array of User DTO with uuid and email only.
     */

    @Override
    public ApiResponse getUsersEmail(List<String> uuids) {
        return userService.getUserEmail(uuids);
    }


//    @Override
//    public ApiResponse getUserDetailsByEmail(String email, String companyUuid) {
//        log.info("Getting user details privately {}", email);
//        return userService.getUserDetailsByEmail(email, companyUuid);
//    }

    //For non TRANSACTION & ENTITY settings only.
//    private List<UserPrivilege> getUserPrivilegesByCompanyFeatureAction(String companyUuid, String featureCode, String action) {
//        switch (action) {
//            case "read":
//                return userPrivilegeRepository.getUserPrivilegeByCompanyUuidAndAction(companyUuid, featureCode, true, null, null);
//            case "write":
//                return userPrivilegeRepository.getUserPrivilegeByCompanyUuidAndAction(companyUuid, featureCode, null, true, null);
//            case "approve":
//                return userPrivilegeRepository.getUserPrivilegeByCompanyUuidAndAction(companyUuid, featureCode, null, null, true);
//            default:
//                return Collections.emptyList();
//        }
//    }

//    private List<RolePermission> getRolePermissionsByCompanyFeatureAction(String companyUuid, String featureCode, String action) {
//        switch (action) {
//            case "read":
//                return rolePermissionRepository.getRoleByCompanyUuidAndAction(companyUuid, featureCode, true, null, null);
//            case "write":
//                return rolePermissionRepository.getRoleByCompanyUuidAndAction(companyUuid, featureCode, null, true, null);
//            case "approve":
//                return rolePermissionRepository.getRoleByCompanyUuidAndAction(companyUuid, featureCode, null, null, true);
//            default:
//                return Collections.emptyList();
//        }
//    }

//    @Override
//    public ApiResponse getUsersUuidByCompanyAndAuthority(String companyUuid, String[] authorities) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            List<String> allAuthoritiesUserUuid = null;
//            for (String authority : authorities) {
//                String featureCode = authority.split(":")[0].toLowerCase();
//                String action = authority.split(":")[1].toLowerCase(Locale.ROOT);
//                List<String> usersOfCurrentAuthority = getUserPrivilegesByCompanyFeatureAction(companyUuid, featureCode, action)
//                        .stream().map(UserPrivilege::getUserUuid)
//                        .collect(Collectors.toList());
//                if (allAuthoritiesUserUuid == null) {
//                    allAuthoritiesUserUuid = new ArrayList<>(usersOfCurrentAuthority);
//                } else {
//                    allAuthoritiesUserUuid.removeIf(userUuid -> !usersOfCurrentAuthority.contains(userUuid));
//                    if (CollectionUtils.isEmpty(allAuthoritiesUserUuid)) {
//                        break;
//                    }
//                }
//            }
//
//            Map<String, List<String>> permissionByRoles = new HashMap<>();
//            for (String authority : authorities) {
//                String featureCode = authority.split(":")[0].toLowerCase();
//                String action = authority.split(":")[1].toLowerCase(Locale.ROOT);
//                List<String> rolesUuidOfCurrentAction = getRolePermissionsByCompanyFeatureAction(companyUuid, featureCode, action)
//                        .stream().map(rp -> rp.getRole().getUuid())
//                        .collect(Collectors.toList());
//                permissionByRoles.put(authority, rolesUuidOfCurrentAction);
//            }
//            List<String> allAuthoritiesUserUuidByRole = null;
//            for (Map.Entry<String, List<String>> entry : permissionByRoles.entrySet()) {
//                List<String> value = entry.getValue();
//                List<String> userUuidsOfRoleSet = getUserUuidsOfRoles(companyUuid, value);
//                if (allAuthoritiesUserUuidByRole == null) {
//                    allAuthoritiesUserUuidByRole = new ArrayList<>(userUuidsOfRoleSet);
//                } else {
//                    allAuthoritiesUserUuidByRole.removeIf(userUuid -> !userUuidsOfRoleSet.contains(userUuid));
//                }
//            }
//            allAuthoritiesUserUuid.addAll(allAuthoritiesUserUuidByRole);
//            apiResponse.setData(allAuthoritiesUserUuid);
//            apiResponse.setStatus(HttpStatus.OK);
//            return apiResponse;
//        } catch (Exception ex) {
//            apiResponse.setData(Collections.emptyList());
//            apiResponse.setStatus(HttpStatus.OK);
//            log.error("Error while getting users by Authority of company {}: {}", companyUuid, ex.getMessage());
//            return apiResponse;
//        }
//    }

//    private List<String> getUserUuidsOfRoles(String companyUuid, List<String> rolesUuid) {
//        return rbacRoleRepository.findAllByRole_UuidIn(rolesUuid).stream()
//                .filter(rur -> rur.getUserCompanies().getCompanies().getUuid().equals(companyUuid))
//                .map(rur -> rur.getUserCompanies().getUser().getUuid())
//                .collect(Collectors.toList());
//    }
}
