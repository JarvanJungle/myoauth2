package com.doxa.oauth2.services;

import com.doxa.oauth2.responses.ApiResponse;

import java.util.List;

/**
 * This service is used to get data internally
 * It cant be accessed from public
 */
public interface IPrivateService {
    /**
     * Get company details privately
     * IMPORTANT NOTE: This function is used by other microservices to retrieve company information,
     * be careful when you change the output
     * @param companyUuid User uuid
     * @return Company details
     */
    ApiResponse getCompanyDetails(String companyUuid);

    /**
     * Get user details privately
     * IMPORTANT NOTE: This function is used by other microservices to retrieve user information,
     * be careful when you change the output
     * @param userUuid User uuid
     * @return User details
     */
    ApiResponse getUserDetails(String userUuid);

    /**
     * @param userUuids
     * @param companyUuid
     * @return
     */
    ApiResponse getUserRbacRoles(List<String> userUuids, String companyUuid);



    ApiResponse getUsersEmail(List<String> userUuid);

    /**
     * Get user details by email privately
     * IMPORTANT NOTE: This function is used by other microservices to retrieve user information,
     * be careful when you change the output
     * @param email user email
     * @param companyUuid
     *
     * @return User details
     */

//    ApiResponse getUserDetailsByEmail(String email, String companyUuid);

//    ApiResponse getUsersUuidByCompanyAndAuthority(String companyUuid, String[] authorities);
}
