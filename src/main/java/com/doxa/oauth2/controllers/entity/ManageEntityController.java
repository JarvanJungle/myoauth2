//package com.doxa.oauth2.controllers.entity;
//
//
//import com.doxa.oauth2.DTO.manageEntity.CreateEntityDetailsDto;
//import com.doxa.oauth2.DTO.manageEntity.UpdateEntityDto;
//import com.doxa.oauth2.common.ControllerPath;
//import com.doxa.oauth2.repositories.companies.EntitiesRepository;
//import com.doxa.oauth2.repositories.user.UserRepository;
//import com.doxa.oauth2.responses.ApiResponse;
//import com.doxa.oauth2.serviceImpl.UserService;
//import com.doxa.oauth2.services.IEntitiesService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
////import com.google.gson.Gson;
//
//@RestController
//@RequestMapping(ControllerPath.ENTITIES)
//public class ManageEntityController {
//
//	private IEntitiesService entitiesService;
//
//	@Autowired
//	private EntitiesRepository entityRepo;
//
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private UserRepository userRepo;
//
//	@Autowired
//	public void setEntitiesService(IEntitiesService entitiesService) {
//		this.entitiesService = entitiesService;
//	}
//
//	/**
//	 * @api {get} org/list List Entities
//	 * @apiDescription To list entities
//	 * @apiGroup Entity
//	 * @apiVersion 0.0.1
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiSuccess (List) {String} onboardingStatus Onboarding status of company
//	 * @apiSuccess (List) {boolean} active If entity is active
//	 * @apiSuccess (List) {String} entityName Name of company
//	 * @apiSuccess (List) {String} uuid UUID of Company
//	 * @apiSuccess (List) {String} company Company's country of origin
//	 * @apiSuccess (List) {String} companyRegistrationNumber Registration number of company
//	 * @apiSuccess (List) {LocalDate} createdAt Date company is created at
//	 * @apiSuccess (List) {LocalDate} subscriptionExpiry Date company's subscription expires
//	 *
//	 * @apiSuccessExample Success-Response Example:
//	 * 	HTTP/1.1 200 OK
//	 *	{
//	 *		"status": "OK",
//	 *		"data": [
//	 *			{
//	 *				"onboardingStatus": "APPROVED",
//	 *				"active": true,
//	 * 				"entityName": "Company1",
//	 * 				"uuid": "12312-1231231-123123",
//	 * 				"country": "Singapore",
//	 * 				"companyRegistrationNumber": "123123",
//	 * 				"createdAt": "24/03/2021",
//	 * 				"subscriptionExpiry": "22/04/2022",
//	 * 			},
//	 * 			{
//	 * 				"onboardingStatus": "APPROVED",
//	 * 				"active": true,
//	 * 				"entityName": "Doxa Holdings",
//	 * 				"uuid": "000000000000000",
//	 * 				"country": "Singapore",
//	 * 				"companyRegistrationNumber": "000000",
//	 * 				"createdAt": "24/03/2021",
//	 * 				"subscriptionExpiry": "22/04/2022"
//	 * 			}
//	 *		],
//	 *    	"timestamp": 1620025840209,
//	 *    	"statusCode": 200
//	 *	}
//	 *
//	 * @apiErrorExample Error-Response:
//	 *  HTTP/1.1 500 Internal Server Error
//	 *	{
//	 *   	"status": "INTERNAL_SERVER_ERROR",
//	 *   	"message": "Exception Error Message",
//	 *   	"timestamp": 1619083958882,
//	 *   	"statusCode": 500
//	 *	}
//	 *
//	 * @param {Next} next
//	 *
//	 */
//
//	// @PreAuthorize("hasAuthority('DOXA_ADMIN')")
//	@GetMapping( ControllerPath.LIST_ENTITIES)
//	public ResponseEntity<ApiResponse> retrieveEntities() {
//		ApiResponse apiResponse = entitiesService.listEntities();
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	/**
//	 * @api {get} org/get-entity-details/:uuid Get Entity Details
//	 * @apiDescription To get entity details
//	 * @apiGroup Entity
//	 * @apiVersion 0.0.1
//	 *
//	 * @apiParam {String} uuid company's unique ID
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiSuccess {String} uuid uuid company's unique ID
//	 * @apiSuccess {String} entityName Name of Company
//	 * @apiSuccess {String} gstNo GST number of entity
//	 * @apiSuccess {String} companyRegistrationNumber Registration number of company
//	 * @apiSuccess {String} entityType Entity Type of company
//	 * @apiSuccess {boolean} gstApplicable If GST is applicable for company
//	 * @apiSuccess {String} industryType Industry type of company
//	 * @apiSuccess {String} country Company's country of origin
//	 * @apiSuccess {boolean} active If entity is active
//	 * @apiSuccess {Array} documentsMetaDataList List of document metadata
//	 * 	@apiSuccess {String} fileName Name of file
//	 * 	@apiSuccess {String} guid GUID of file
//	 * 	@apiSuccess {String} title Title of file
//	 *	@apiSuccess {LocalDate} createdAt Date file was created
//	 *	@apiSuccess {LocalDate} updatedAt Date file was updated
//	 * @apiSuccess {Array} entityRepresentativeList List of entity representatives
//	 *	@apiSuccess (List) {String} name Entity Rep Name
//	 * 	@apiSuccess (List) {String} email Entity Rep Email
//	 * 	@apiSuccess (List) {String} workNumber Entity Rep Number
//	 * 	@apiSuccess (List) {String} userRole Entity Rep Role
//	 *
//	 * @apiSuccessExample Success-Response Example:
//	 * 	HTTP/1.1 200 OK
//	 * 	{
//	 * 		"status":"OK",
//	 * 		"data":{
//	 * 			"uuid":"b69aab84-125c-4dd7-8079-a73ee8764a6b",
//	 * 			"entityName":"testing1",
//	 * 			"gstNo":"testing1",
//	 * 			"companyRegistrationNumber":"testing1",
//	 * 			"entityType":"LIMITED LIABILITY PARTNERSHIP",
//	 * 			"industryType":"ACTIVITIES OF HOUSEHOLDS AS EMPLOYERS OF DOMESTIC PERSONNEL",
//	 * 			"gstApplicable":true,
//	 * 			"country":"Åland Islands",
//	 * 			"documentsMetaDataList":[
//	 * 				{
//	 * 					"guid":"df6840e56b894a81842c510cadb038ad",
//	 * 					"title":"testing1",
//	 * 					"fileName":"Testing.txt",
//	 * 					"createdAt":"22/04/2021",
//	 * 					"updatedAt":"22/04/2021"
//	 * 				}
//	 * 			],
//	 * 			"entityRepresentativeList":[
//	 * 				{
//	 * 					"name":"testing1",
//	 * 					"email":"doxatesting1@getnada.com",
//	 * 					"workNumber":"1231123",
//	 * 					"userRole":"Entity Representitive"
//	 * 				},
//	 * 				{
//	 * 					"name":"testing1",
//	 * 					"email":"doxatesting1@getnada.com",
//	 * 					"workNumber":"1231123",
//	 * 					"userRole":"Authorized Signatory"
//	 * 				},
//	 * 				{
//	 * 					"name":"testing12",
//	 * 					"email":"doxatesting1@getnada.com",
//	 * 					"workNumber":"1231123",
//	 * 					"userRole":"Entity Administrator"
//	 * 				}
//	 * 			],
//	 * 			"active":true,
//	 * 		},
//	 * 		"timestamp":1619141382981,
//	 * 		"statusCode":200
//	 * 	}
//	 *
//	 * @apiErrorExample Error-Response Example:
//	 * 	HTTP/1.1 404 Not Found
//	 * 	{
//	 * 		"status":"NOT_FOUND",
//	 * 		"message":"Data not found",
//	 * 		"timestamp":1619079114220,
//	 * 		"statusCode":404
//	 * 	}
//	 *
//	 * @param {Next} next
//	 *
//	 */
//
//	// @PreAuthorize("hasAuthority('DOXA_ADMIN')")
//	@GetMapping(ControllerPath.GET_ENTITY)
//	public ResponseEntity<ApiResponse> getEntityDetails(@PathVariable @NotEmpty String uuid) {
//		ApiResponse apiResponse = entitiesService.getEntityDetails(uuid);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	/**
//	 * @api {post} org/create Create Entity
//	 * @apiDescription To get entity details
//	 * @apiGroup Entity
//	 * @apiVersion 0.0.1
//	 *
//	 * @apiParam {String} entityName Name of Company
//	 * @apiParam {String} gstNo GST number of entity
//	 * @apiParam {String} companyRegistrationNumber Registration number of company
//	 * @apiParam {String} entityType Entity Type of company
//	 * @apiParam {boolean} gstApplicable If GST is applicable for company
//	 * @apiParam {String} industryType Industry type of company
//	 * @apiParam {String} country Company's country of origin
//	 * @apiParam {Array} documentsMetaDataList List of document metadata
//	 * 	@apiParam (List) {String} fileName Name of file
//	 * 	@apiParam (List) {String} guid GUID of file
//	 * 	@apiParam (List) {String} Title of file
//	 * @apiParm {Array} entityRepresentativeList
//	 *	@apiParam (List) {String} name Entity Rep Name
//	 * 	@apiParam (List) {String} email Entity Rep Email
//	 * 	@apiParam (List) {String} workNumber Entity Rep Number
//	 * 	@apiParam (List) {String} userRole Entity Rep Role
//	 *
//	 * @apiParamExample {json} Request-Example:
//	 * 	{
//	 * 		"entityName": "Testing1",
//	 * 		"gstNo": "Testing1",
//	 * 		"companyRegistrationNumber": "Testing2",
//	 * 		"entityType": "LIMITED LIABILITY COMPANY",
//	 * 		"gstApplicable": true,
//	 * 		"industryType": "CONSTRUCTION",
//	 * 		"country": "Singapore",
//	 * 		"documentsMetaDataList": [
//	 * 			{
//	 * 				"fileName": "testingPDF1.pdf",
//	 * 				"guid": "7e148f7339684599809697bb9sdaf3ba",
//	 * 				"title": "sdasdadasdasd"
//	 * 			}
//	 * 		],
//	 * 		"entityRepresentativeList": [
//	 * 			{
//	 * 				"name": "Testing1",
//	 * 				"email": "rep1@testing.com",
//	 * 				"workNumber": "91298567",
//	 * 				"userRole": "Entity Representitive"
//	 * 			},
//	 * 			{
//	 * 				"name": "Testing1",
//	 * 				"email": "rep1@testing.com",
//	 * 				"workNumber": "91298567",
//	 * 				"userRole": "Authorized Signatory"
//	 * 			},
//	 * 			{
//	 * 				"name": "Testing1",
//	 * 				"email": "rep1@testing.com",
//	 * 				"workNumber": "91298567",
//	 * 				"userRole": "Entity Administrator"
//	 * 			}
//	 * 		]
//	 * 	}
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiSuccessExample Success-Response Example:
//	 * 	HTTP/1.1 200 OK
//	 * 	{
//	 * 		"status":"OK",
//	 * 		"data":"Company created for: Testing1",
//	 * 		"timestamp":1619076036450,
//	 * 		"statusCode":200
//	 * 	}
//	 *
//	 * @apiErrorExample Error-Response Example:
//	 * 	HTTP/1.1 404 Bad Request
//	 * 	{
//	 * 		"status":"BAD_REQUEST",
//	 * 		"message":"Company Already Exists",
//	 * 		"timestamp":1619075930148,
//	 * 		"statusCode":400
//	 * 	}
//	 *
//	 * @param {Next} next
//	 */
//
//	// @PreAuthorize("hasAuthority('DOXA_ADMIN')")
//	@PostMapping(ControllerPath.CREATE_ENTITY)
//	public ResponseEntity<ApiResponse> createEntity(@Valid @RequestBody CreateEntityDetailsDto createEntityDetailsDto) {
//		ApiResponse apiResponse = entitiesService.createEntities(createEntityDetailsDto);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	/**
//	 * @api {post} org/update Update Entity
//	 * @apiDescription To update entity details
//	 * @apiGroup Entity
//	 * @apiVersion 0.0.1
//	 *
//	 * @apiParam {String} entityName Name of Company
//	 * @apiParam {String} gstNo GST number of entity
//	 * @apiParam {String} companyRegistrationNumber Registration number of company
//	 * @apiParam {String} entityType Entity Type of company
//	 * @apiParam {boolean} gstApplicable If GST is applicable for company
//	 * @apiParam {String} industryType Industry type of company
//	 * @apiParam {String} country Company's country of origin
//	 * @apiParam {Array} documentsMetaDataList List of document metadata
//	 * 	@apiParam (List) {String} fileName Name of file
//	 * 	@apiParam (List) {String} guid GUID of file
//	 * 	@apiParam (List) {String} Title of file
//	 * @apiParm {Array} entityRepresentativeList
//	 *	@apiParam (List) {String} name Entity Rep Name
//	 * 	@apiParam (List) {String} email Entity Rep Email
//	 * 	@apiParam (List) {String} workNumber Entity Rep Number
//	 * 	@apiParam (List) {String} userRole Entity Rep Role
//	 *
//	 * @apiParamExample {json} Request-Example:
//	 * {
//	 * 		"uuid": "21afc4f8-90b2-4836-9601-c7576b9ddd0a",
//	 * 		"entityName": "Testing1",
//	 * 		"gstNo": "Testing1",
//	 * 		"companyRegistrationNumber": "Testing2",
//	 * 		"entityType": "LIMITED LIABILITY COMPANY",
//	 * 		"gstApplicable": true,
//	 * 		"industryType": "CONSTRUCTION",
//	 * 		"country": "Singapore",
//	 * 		"documentsMetaDataList": [
//	 * 			{
//	 * 				"fileName": "testingPDF1.pdf",
//	 * 				"guid": "7e148f7339684599809697bb9sdaf3ba",
//	 * 				"title": "sdasdadasdasd"
//	 * 			}
//	 * 		],
//	 * 		"entityRepresentativeList": [
//	 * 			{
//	 * 				"name": "Testing1",
//	 * 				"email": "rep1@testing.com",
//	 * 				"workNumber": "91298567",
//	 * 				"userRole": "Entity Representitive"
//	 * 			},
//	 * 			{
//	 * 				"name": "Testing1",
//	 * 				"email": "rep1@testing.com",
//	 * 				"workNumber": "91298567",
//	 * 				"userRole": "Authorized Signatory"
//	 * 			},
//	 * 			{
//	 * 				"name": "Testing1",
//	 * 				"email": "rep1@testing.com",
//	 * 				"workNumber": "91298567",
//	 * 				"userRole": "Entity Administrator"
//	 * 			}
//	 * 		]
//	 * 	}
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiSuccessExample Success-Response Example:
//	 * 	HTTP/1.1 200 OK
//	 * 	{
//	 * 		"status":"OK",
//	 * 		"data":"Entity updated for: Testing2changed",
//	 * 		"timestamp":1619076036450,
//	 * 		"statusCode":200
//	 * 	}
//	 *
//	 * @apiErrorExample Error-Response:
//	 * 	HTTP/1.1 404 Bad Request
//	 * 	{
//	 * 		"status":"NOT_FOUND",
//	 * 		"message":"Cannot retrieve entity",
//	 * 		"timestamp":1619079114220,
//	 * 		"statusCode":404
//	 * 	}
//	 *
//	 * @param {Next} next
//	 */
//
//	// @PreAuthorize("hasAuthority('DOXA_ADMIN')")
//	@PostMapping(ControllerPath.UPDATE_ENTITY)
//	public ResponseEntity<ApiResponse> updateEntity(@Valid @RequestBody UpdateEntityDto updateEntityDto) {
//		ApiResponse apiResponse = entitiesService.updateEntities(updateEntityDto);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	/**
//	 * @api {get} org/industry-type-list Retrieve Industry Type List
//	 * @apiDescription To list all industry types for companies
//	 * @apiGroup Entity
//	 * @apiVersion 0.0.1
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiSuccess industryType Industry type for company
//	 *
//	 * @apiSuccessExample Success-Response Example:
//	 * 	HTTP/1.1 200 OK
//	 * 	{
//	 * 		"status":"OK",
//	 * 		"data":[
//	 * 			{"industryType":"ACTIVITIES OF EXTRA-TERRORIAL ORGANISATIONS AND BODIES"},
//	 * 			{"industryType":"ACTIVITIES OF HOUSEHOLDS AS EMPLOYERS OF DOMESTIC PERSONNEL"},
//	 * 			{"industryType":"ADMINISTRATIVE AND SUPPORT SERVICE ACTIVITIES"},
//	 * 			{"industryType":"AGRICULTURE AND FISHING"},
//	 * 			{"industryType":"ARTS, ENTERTAINMENT AND RECREATION"},
//	 * 			{"industryType":"CONSTRUCTION"},{"industryType":"EDUCATION"},
//	 * 			{"industryType":"ELECTRICITY, GAS, STEAM AND AIR-CONDITIONING SUPPLY"},
//	 * 			{"industryType":"FINANCIAL AND INSURANCE ACTIVITIES"},
//	 * 			{"industryType":"HEALTH AND SOCIAL SERVICES"},
//	 * 			{"industryType":"INFORMATION AND COMMUNICATION"},
//	 * 			{"industryType":"MANUFACTURING"},
//	 * 			{"industryType":"MINING AND QUARRYING"},
//	 * 			{"industryType":"OTHER SERVICE ACTIVITIES"},
//	 * 			{"industryType":"PROFESSIONAL, SCIENTIFIC AND TECHNICAL ACTIVITIES"},
//	 * 			{"industryType":"PUBLIC ADMINISTRATION AND DEFENCE"},
//	 * 			{"industryType":"REAL ESTATE ACTIVITIES"},
//	 * 			{"industryType":"TRANSPORTATION AND STORAGE"},
//	 * 			{"industryType":"WATER SUPPLY; SEWERAGE, WASTE MANAGEMENT AND REMEDIATION ACTIVITIES"},
//	 * 			{"industryType":"WHOLESALE AND RETAIL TRADE"},
//	 * 			{"industryType":"ACCOMODATION AND FOOD SERVICE ACTIVITIES"},
//	 * 			{"industryType":"ACTIVITIES NOT ADEQUATELY DEFINED"}
//	 * 		],
//	 * 		"timestamp":1619143396522,
//	 * 		"statusCode":200
//	 * 	}
//	 *
//	 * @apiErrorExample Error-Response:
//	 *  HTTP/1.1 500 Internal Server Error
//	 *	{
//	 *   	"status": "INTERNAL_SERVER_ERROR",
//	 *   	"message": "Exception Error Message",
//	 *   	"timestamp": 1619083958882,
//	 *   	"statusCode": 500
//	 *	}
//	 *
//	 * @param {Next} next
//	 */
//
//	// @PreAuthorize("hasAuthority('DOXA_ADMIN')")
//	@GetMapping(ControllerPath.ENTITY_TYPE)
//	public ResponseEntity<ApiResponse> retrieveEntityTypeList() {
//		ApiResponse apiResponse = entitiesService.listEntityType();
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	/**
//	 * @api {get} org/entity-type-list Retrieve Entity Type List
//	 * @apiDescription To list all entity types for companies
//	 * @apiGroup Entity
//	 * @apiVersion 0.0.1
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiSuccess entityType Entity type for company
//	 *
//	 * @apiSuccessExample Success-Response Example:
//	 * 	HTTP/1.1 200 OK
//	 * 	{
//	 * 		"status":"OK",
//	 * 		"data":[
//	 * 			{"entityType":"GENERAL PARTNERSHIP"},
//	 * 			{"entityType":"LIMITED LIABILITY PARTNERSHIP"},
//	 * 			{"entityType":"SOLE PROPRIETORSHIP"},
//	 * 			{"entityType":"FOREIGN COMPANY - REPRESENTATIVE OFFICE OR BRANCH OFFICE"},
//	 * 			{"entityType":"LIMITED LIABILITY COMPANY"},
//	 * 			{"entityType":"PRIVATE LIMITED"}
//	 * 		],
//	 * 		"timestamp":1619143769535,
//	 * 		"statusCode":200
//	 * 	}
//	 *
//	 * @apiErrorExample Error-Response:
//	 *  HTTP/1.1 500 Internal Server Error
//	 *	{
//	 *   	"status": "INTERNAL_SERVER_ERROR",
//	 *   	"message": "Exception Error Message",
//	 *   	"timestamp": 1619083958882,
//	 *   	"statusCode": 500
//	 *	}
//	 *
//	 * @param {Next} next
//	 */
//
//	// @PreAuthorize("hasAuthority('DOXA_ADMIN')")
//	@GetMapping(ControllerPath.INDUSTRY_TYPE)
//	public ResponseEntity<ApiResponse> retrieveIndustryTypeList() {
//		ApiResponse apiResponse = entitiesService.listIndustryType();
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	/**
//	 * @api {put} org/delete/:uuid Deactivate Entity
//	 * @apiDescription To deactivate entity
//	 * @apiGroup Entity
//	 * @apiVersion 0.0.1
//	 *
//	 * @apiParam {String} uuid entity's unique ID
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiSuccessExample Success-Response:
//	 * 	HTTP/1.1 200 OK
//	 * 	{
//	 * 		"status":"OK",
//	 * 		"data":"Company deactivated: Testing2changed",
//	 * 		"timestamp":1619139921531,
//	 * 		"statusCode":200
//	 * 	}
//	 *
//	 * @apiErrorExample Error-Response:
//	 * 	HTTP/1.1 404 Bad Request
//	 * 	{
//	 * 		"status":"NOT_FOUND",
//	 * 		"message":"Cannot retrieve entity",
//	 * 		"timestamp":1619079114220,
//	 * 		"statusCode":404
//	 * 	}
//	 *
//	 * @param {Next} next
//	 */
//
//	// @PreAuthorize("hasAuthority('DOXA_ADMIN')")
//	@PutMapping(ControllerPath.DELETE_ENTITY)
//	public ResponseEntity<ApiResponse> markCompanyDeleted(@PathVariable @NotEmpty String uuid) {
//		ApiResponse apiResponse = entitiesService.markEntityDeleted(uuid);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	/**
//	 * @api {put} org/reactivate/:uuid Reactivate Entity
//	 * @apiDescription To reactivate entity
//	 * @apiGroup Entity
//	 * @apiVersion 0.0.1
//	 *
//	 * @apiParam {String} uuid company's unique ID
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiSuccessExample Success-Response:
//	 * 	HTTP/1.1 200 OK
//	 * 	{
//	 * 		"status":"OK",
//	 * 		"data":"Company reactivated: Testing12",
//	 * 		"timestamp":1619139921531,
//	 * 		"statusCode":200
//	 * 	}
//	 *
//	 * @apiErrorExample Error-Response:
//	 * 	HTTP/1.1 200 OK
//	 * 	{
//	 * 		"status":"BAD_REQUEST",
//	 * 		"message":"Cannot retrieve company",
//	 * 		"timestamp":1619140010156,
//	 * 		"statusCode":400
//	 * 	}
//	 *
//	 * @param {Next} next
//	 */
//
//	// @PreAuthorize("hasAuthority('ENTITY_ADMIN')")
//	@PutMapping(ControllerPath.REACTIVATE_ENTITY)
//	public ResponseEntity<ApiResponse> reactivateEntity(@PathVariable @NotEmpty String uuid) {
//		ApiResponse apiResponse = entitiesService.reactivateEntity(uuid);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//}
