package com.doxa.oauth2.controllers.privateAPI;

//import com.doxa.oauth2.DTO.financialInstitution.FIUserCreateDto;
//import com.doxa.oauth2.DTO.financialInstitution.FiUserDto;
import com.doxa.oauth2.common.ControllerPath;
import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
import com.doxa.oauth2.responses.ApiResponse;
import com.doxa.oauth2.serviceImpl.AuthorityService;
import com.doxa.oauth2.serviceImpl.CompaniesServiceImpl;
import com.doxa.oauth2.serviceImpl.PrivateService;
import com.doxa.oauth2.serviceImpl.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(ControllerPath.PRIVATE)
@RequiredArgsConstructor
public class PrivateController {

	private static final Logger LOG = LoggerFactory.getLogger(PrivateController.class);

    private final CompaniesServiceImpl companyService;
    private final UserService userService;
	private final PrivateService privateService;
	private final AuthorityService authorityService;


    /**
     * @api {get} /api/private/company-details?isCompanyUuid=:isCompanyUuid&companyUuidOrUEN=:companyUuidOrUEN&country=:country Get company details from companyUEN or companyUuid
     * @apiGroup Private Management
     * @apiVersion 0.0.1
     *
     * @apiParam {boolean} isCompanyUuid True for companyUuid while false for companyUEN
     * @apiParam {String} companyUuidOrUEN CompanyUuid or UEN based on the boolean isCompanyUuid
     * @apiParam {String} country Country of origin (Only needed when UEN is used)
     *
     * @apiSuccess  {String} uuid Company unique uuid
     * @apiSuccess  {String} entityName Company's name
     * @apiSuccess  {String} gstNo Company gst number (if gstApplicable = true)
     * @apiSuccess  {boolean} active Is company active?
     * @apiSuccess  {String} createdAt Created date
     * @apiSuccess  {String} updatedAt Updated date
     * @apiSuccess  {String} companyRegistrationNumber Company's UEN
     * @apiSuccess  {String} entityType Company's type
     * @apiSuccess  {String} industryType Company's industry type
     * @apiSuccess  {boolean} gstApplicable Is company gst applicable?
     * @apiSuccess  {String} country Country of the company
     * @apiSuccess  {boolean} mainCompany Is company main company?
     * @apiSuccess  {String} onboardingStatus On boarding status of the company
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *	 {
     *	    "status": "OK",
     *      "data": {
	 *	        "uuid": "129618fe-4bfb-4c79-b4db-44787bd3cb1f",
	 *	        "entityName": "Company1",
	 *	        "gstNo": "11111",
	 *	        "active": true,
	 *	        "createdAt": "2021-03-24",
	 *	        "updatedAt": "2021-03-24T00:00:00",
	 *	        "companyRegistrationNumber": "123123",
	 *	        "entityType": "PRIVATE LIMITED",
	 *	        "industryType": "CONSTRUCTION",
	 *	        "gstApplicable": true,
	 *	        "country": "Singapore",
	 *	        "mainCompany": true,
	 *	        "onboardingStatus": "APPROVED"
	 *	    },
     *	    "timestamp": 1619083490713,
     *	    "statusCode": 0
     *	 }
     *
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 200 OK
     *	{
     *   	"status": "BAD_REQUEST",
     *   	"message": "Error",
     *   	"timestamp": 1619083958882,
     *   	"statusCode": 0
     *	}
     *
     * @apiDescription Get company details from companyUEN or companyUuid @author: Melvin
     *
     */
	@GetMapping(ControllerPath.PRIVATE_COMPANY_DETAILS)
    public ResponseEntity<ApiResponse> findCompanyDetailsFromCompanyUuidOrUEN(@RequestParam String companyUuidOrUEN, @RequestParam(required = false) String country, @RequestParam boolean isCompanyUuid) {
		//if isCompanyUuid = false, means the input is companyRegistrationNumber
        ApiResponse apiResponse = companyService.findCompanyDetailsFromCompanyUuidOrUEN(companyUuidOrUEN, isCompanyUuid, country);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

	@GetMapping(ControllerPath.PRIVATE_COMPANY_DETAILS + "/{uuid}")
	public ResponseEntity<ApiResponse> getCompanyDetails(@PathVariable String uuid) {
		//if isCompanyUuid = false, means the input is companyRegistrationNumber
		ApiResponse apiResponse = privateService.getCompanyDetails(uuid);
		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
	}

	@GetMapping(ControllerPath.PRIVATE_USER_DETAILS)
	public ResponseEntity<ApiResponse> getUserDetails(@PathVariable String uuid) {
		//if isCompanyUuid = false, means the input is companyRegistrationNumber
		ApiResponse apiResponse = privateService.getUserDetails(uuid);
		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
	}

	@PostMapping(ControllerPath.PRIVATE_USER_RBAC_ROLES)
	public ResponseEntity<ApiResponse> getUserRbacRoles(@PathVariable @NotEmpty String companyUuid, @RequestBody List<String> userUuids) {
		ApiResponse apiResponse = privateService.getUserRbacRoles(userUuids, companyUuid);
		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
	}



	/**
	 * @api {get} /api/private/user-emails/:uuids Get user email from delimited uuids string
	 * @apiGroup Private Management
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {String} uuids: Comma delimited users' uuid strings.
	 * @apiSuccess  (List) {String} uuid: uuid of user
	 * @apiSuccess  (List) {String} email: email of user
	 */
	@GetMapping(ControllerPath.PRIVATE_USER_EMAIL + "/{uuids}")
	public ResponseEntity<ApiResponse> getUserEmail(@PathVariable String uuids) {
		List<String> listUuids = Arrays.asList(uuids.split(","));
		ApiResponse apiResponse = privateService.getUsersEmail(listUuids);
		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
	}

    /**
     * @api {get} /api/private/entity-admin-details?companyUuid=:companyUuid Get entity admin details of a company
     * @apiGroup Private Management
     * @apiVersion 0.0.1
     *
     * @apiParam {String} companyUuid Unique company's uuid
     *
     * @apiSuccess  {String} name Entity admin's name
     * @apiSuccess  {String} email Entity admin's email
     * @apiSuccess  {String} uuid Entity admin user uuid
     * @apiSuccess  {String} workNumber Entity admin worknumber
     * @apiSuccess  {String} designation Entity admin designation
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *	 {
     *	    "status": "OK",
     *      "data": {
	 *	        "name": "Entity Admin",
	 *	        "email": "entityadmin@getnada.com",
	 *	        "uuid": "e5ff2cf2-22d4-4544-8a92-c61128b7e5d6",
	 *	        "workNumber": "98989898",
	 *	        "designation": "manager"
	 *	    },
     *	    "timestamp": 1619083490713,
     *	    "statusCode": 0
     *	 }
     *
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 200 OK
     *	{
     *   	"status": "BAD_REQUEST",
     *   	"message": "Error",
     *   	"timestamp": 1619083958882,
     *   	"statusCode": 0
     *	}
     *
     * @apiDescription Get entity admin details of a company @author: Melvin
     *
     */
//	@GetMapping(ControllerPath.PRIVATE_ENTITY_ADMIN_DETAILS)
//    public ResponseEntity<ApiResponse> findEntityAdminDetails(@RequestParam String companyUuid) {
//        ApiResponse apiResponse = userService.findEntityAdminDetails(companyUuid);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    /**
     * @api {get} /api/private/company-admin-details?companyUuidList=:companyUuid&companyUuidList=:companyUuid Retrieve the company and entity admin details of a list of company through companyUuid
     * @apiGroup Private Management
     * @apiVersion 0.0.1
     *
     * @apiParam {String} companyUuid Unique company's uuid
     *
     * @apiSuccess  {Array} companyUserDetailsList List of company and entity admin details
     * @apiSuccess  (List) {String} name Name of entity admin
     * @apiSuccess  (List) {String} email Email of entity admin
     * @apiSuccess  (List) {String} userUuid Unique uuid of entity admin
     * @apiSuccess  (List) {String} workNumber Worknumber of entity admin
     * @apiSuccess  (List) {String} designation Designation of entity admin
     * @apiSuccess  (List) {String} companyUuid Unique uuid of company
     * @apiSuccess  (List) {String} entityName Company name
     * @apiSuccess  (List) {String} gstNo Gst number of company if applicable
     * @apiSuccess  (List) {boolean} active Is company active?
     * @apiSuccess  (List) {String} createdAt Created date of the company
     * @apiSuccess  (List) {String} updatedAt Last updated date of the company
     * @apiSuccess  (List) {String} companyRegistrationNumber Company UEN
     * @apiSuccess  (List) {String} entityType Company's entity type
     * @apiSuccess  (List) {String} industryType Company's industry type
     * @apiSuccess  (List) {boolean} gstApplicable Is gst applicable for the company
     * @apiSuccess  (List) {String} country Company's country
     * @apiSuccess  (List) {boolean} mainCompany Is company main company?
     * @apiSuccess  (List) {String} onboardingStatus On boarding status of the company
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *	 {
     *	    "status": "OK",
     *      "data": {
	 *	        "companyUserDetailsList": [
	 *	            {
	 *	                "userDetails": {
	 *	                    "name": "Entity Admin",
	 *	                    "email": "entityadmin@getnada.com",
	 *	                    "uuid": "e5ff2cf2-22d4-4544-8a92-c61128b7e5d6",
	 *	                    "workNumber": "98989898",
	 *	                    "designation": "manager",
	 *						"countryCode": "65"
	 *	                },
	 *	                "companyDetails": {
	 *	                    "uuid": "129618fe-4bfb-4c79-b4db-44787bd3cb1f",
	 *	                    "entityName": "Company1",
	 *	                    "gstNo": "11111",
	 *	                    "active": true,
	 *	                    "createdAt": "2021-03-24",
	 *	                    "updatedAt": "2021-03-24T00:00:00",
	 *	                    "companyRegistrationNumber": "123123",
	 *	                    "entityType": "PRIVATE LIMITED",
	 *	                    "industryType": "CONSTRUCTION",
	 *	                    "gstApplicable": true,
	 *	                    "country": "Singapore",
	 *	                    "mainCompany": true,
	 *	                    "onboardingStatus": "APPROVED"
	 *	                }
	 *	            },
	 *	            {
	 *	                "userDetails": {
	 *	                    "name": "Entity Admin",
	 *	                    "email": "entityadmin@getnada.com",
	 *	                    "uuid": "e5ff2cf2-22d4-4544-8a92-c61128b7e5d6",
	 *	                    "workNumber": "98989898",
	 *	                    "designation": "manager",
	 *						"countryCode": "65"
	 *	                },
	 *	                "companyDetails": {
	 *	                    "uuid": "95b5cf62-2f9f-4eaf-9d52-39e23dd020b8",
	 *	                    "entityName": "Company2",
	 *	                    "gstNo": "22222",
	 *	                    "active": true,
	 *	                    "createdAt": "2021-03-24",
	 *	                    "updatedAt": "2021-03-24T00:00:00",
	 *	                    "companyRegistrationNumber": "223223",
	 *	                    "entityType": "PRIVATE LIMITED",
	 *	                    "industryType": "CONSTRUCTION",
	 *	                    "gstApplicable": true,
	 *	                    "country": "Singapore",
	 *	                    "mainCompany": false,
	 *	                    "onboardingStatus": "APPROVED"
	 *	                }
	 *	            }
	 *	        ]
	 *	    },
     *	    "timestamp": 1619083490713,
     *	    "statusCode": 0
     *	 }
     *
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 200 OK
     *	{
     *   	"status": "BAD_REQUEST",
     *   	"message": "Error",
     *   	"timestamp": 1619083958882,
     *   	"statusCode": 0
     *	}
     *
     * @apiDescription Retrieve the company and entity admin details of a list of company through companyUuid @author: Melvin
     *
     */
//	@GetMapping(ControllerPath.PRIVATE_COMPANY_AND_ENTITY_ADMIN_DETAILS)
//    public ResponseEntity<ApiResponse> findCompaniesAndAdminDetails(@RequestParam List<String> companyUuidList) {
//        ApiResponse apiResponse = userService.findCompaniesAndAdminDetails(companyUuidList);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

	@GetMapping(ControllerPath.PRIVATE_USR_AUTHORITY)
	public ResponseEntity<ApiResponse> getUserAuthority(@PathVariable String companyUuid, @PathVariable String userUuid) throws ObjectDoesNotExistException {
		ApiResponse apiResponse = authorityService.getUserAuthority(companyUuid, userUuid);
		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
	}


	/**
	 * This is the private api to retrieve user details by user email
	 * @param email
	 * @return
	 */

//	@GetMapping(ControllerPath.PRIVATE_USER_DETAILS_BY_EMAIL)
//	public ResponseEntity<ApiResponse> getUserDetailsByEmail(@RequestParam String email, @RequestParam String companyUuid) {
//		ApiResponse apiResponse = privateService.getUserDetailsByEmail(email, companyUuid);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}

//	@GetMapping(ControllerPath.PRIVATE_GET_USERS_UUID_BY_AUTHORITY)
//	public ResponseEntity<ApiResponse> getUsersUuidByAuthority(@RequestParam String companyUuid, @RequestParam String[] authorities) {
//		ApiResponse apiResponse = privateService.getUsersUuidByCompanyAndAuthority(companyUuid, authorities);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}

//	@PostMapping(ControllerPath.PRIVATE_CREATE_FI_USER)
//	public ResponseEntity<ApiResponse> createFiUser(@RequestBody FiUserDto userDto) throws Exception{
//	ApiResponse createFIUser = userService.createFiUser(userDto);
//	return ResponseEntity.status(HttpStatus.OK).body(createFIUser);
//	}
}
