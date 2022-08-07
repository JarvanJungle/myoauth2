package com.doxa.oauth2.controllers.company;

import com.doxa.oauth2.common.ControllerPath;
import com.doxa.oauth2.repositories.companies.CompanyRepository;
import com.doxa.oauth2.responses.ApiResponse;
import com.doxa.oauth2.services.ICompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping(ControllerPath.COMPANY)
public class ManageCompanyController {

	private ICompaniesService companiesService;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	public void setCompaniesService(ICompaniesService companiesService) {
		this.companiesService = companiesService;
	}

	/**
	 * @api {get} company/list List Company
	 * @apiDescription To list companies
	 * @apiGroup Manage Company
	 * @apiVersion 0.0.1
	 * 
	 * 
	 * @apiHeader {String} Authorization JWT token
	 * 
	 * @apiSuccess (List) {String} onboardingStatus Onboarding status of company
	 * @apiSuccess (List) {boolean} active Active status of company
	 * @apiSuccess (List) {String} entityName Name of company
	 * @apiSuccess (List) {String} uuid UUID of Company
	 * @apiSuccess (List) {String} company Company's country of origin
	 * @apiSuccess (List) {String} companyRegistrationNumber Registration number of company
	 * @apiSuccess (List) {LocalDate} createdAt Date company is created at
	 * @apiSuccess (List) {LocalDate} subscriptionExpiry Date company's subscription expires	 
	 * 
	 * @apiSuccessExample Success-Response Example:
	 * 	HTTP/1.1 200 OK
	 * 	{
	 * 		"status":"OK",
	 * 		"data": [
	 * 			{
	 * 				"onboardingStatus":"APPROVED",
	 * 				"active":"true",
	 * 				"entityName":"testing12change",
	 * 				"uuid":"0529e000-73d2-42df-a2c5-fb8e590f1758",
	 * 				"country":"Afghanistan",
	 * 				"companyRegistrationNumber":"testing12",
	 * 				"createdAt":"22/04/2021",
	 * 				"subscriptionExpiry":"22/04/2022"
	 * 			}
	 * 		],
	 * 		"timestamp":1619075023632,
	 * 		"statusCode":200
	 * 	}
	 * 
	 * @apiErrorExample Error-Response:
	 *  HTTP/1.1 500 Internal Server Error
	 *	{
	 *   	"status": "INTERNAL_SERVER_ERROR",
	 *   	"message": "Exception Error Message",
	 *   	"timestamp": 1619083958882,
	 *   	"statusCode": 500
	 *	}
	 * 
	 * @param {Next} next 
	 */
	
	// @PreAuthorize("hasAuthority('ENTITY_ADMIN')")
//	@GetMapping(ControllerPath.LIST_COMPANIES)
//	public ResponseEntity<ApiResponse> retrieveEntityCompanies(@PathVariable String entityUuid) {
//		ApiResponse apiResponse = companiesService.listAllCompaniesUnderEntity(entityUuid);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}

//	@GetMapping(ControllerPath.LIST_ENTITY_COMPANIES)
//	public ResponseEntity<ApiResponse> retrieveCompanies() {
//		ApiResponse apiResponse = companiesService.listCompanies();
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
	
	/**
	 * @api {post} company/create Create Company
	 * @apiDescription To create company
	 * @apiGroup Manage Company
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {String} entityName Name of Company
	 * @apiParam {String} gstNo GST number of entity
	 * @apiParam {String} companyRegistrationNumber Registration number of company
	 * @apiParam {String} entityType Entity Type of company
	 * @apiParam {boolean} gstApplicable If GST is applicable for company
	 * @apiParam {String} industryType Industry type of company
	 * @apiParam {String} country Company's country of origin
	 * @apiParam {Array} documentsMetaDataList List of document metadata
	 * 	@apiParam (List) {String} fileName Name of file
	 * 	@apiParam (List) {String} guid GUID of file
	 * 	@apiParam (List) {String} Title of file
	 * 
	 * @apiParamExample {json} Request-Example:
	 * 	{
	 * 		"entityName": "Testing1",
	 * 		"gstNo": "Testing1", 
	 * 		"companyRegistrationNumber": "Testing23", 
	 * 		"entityType": "LIMITED LIABILITY COMPANY", 
	 * 		"gstApplicable": true, 
	 * 		"industryType": "CONSTRUCTION", 
	 * 		"country": "Singapore", 
	 * 		"documentsMetaDataList": [
	 * 			{ 
	 * 				"fileName": "testingPDF1.pdf", 
	 * 				"guid": "7e148f7339684599809697bb9sdaf3ba", 
	 * 				"title": "sdasdadasdasd"
	 * 			}
	 * 		]
	 * 	}
	 * 
	 * @apiHeader {String} Authorization JWT token
	 * 
	 * @apiSuccessExample Success-Response Example:
	 * 	HTTP/1.1 200 OK
	 * 	{
	 * 		"status":"OK",
	 * 		"data":"Company created for: Testing1",
	 * 		"timestamp":1619076036450,
	 * 		"statusCode":200
	 * 	}
	 * 
	 * @apiErrorExample Error-Response Example:
	 * 	HTTP/1.1 400 Bad Request
	 * 	{
	 * 		"status":"BAD_REQUEST",
	 * 		"message":"Company Already Exists",
	 * 		"timestamp":1619075930148,
	 * 		"statusCode":400
	 * 	}
	 * 
	 * @param {Next} next 
	 */

	// @PreAuthorize("hasAuthority('ENTITY_ADMIN')")
//	@PostMapping(ControllerPath.CREATE_COMPANY)
//	public ResponseEntity<ApiResponse> createEntity(@Valid @RequestBody CreateCompanyDetailsDto createCompanyDetailsDto) {
//		ApiResponse apiResponse = companiesService.createCompany(createCompanyDetailsDto);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
	
	/**
	 * @api {get} company/get-company-details/:uuid Get Company Details
	 * @apiDescription To get company details
	 * @apiGroup Manage Company
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {String} uuid company's unique ID
	 *
	 * @apiSuccess {String} uuid uuid company's unique ID
	 * @apiSuccess {String} entityName Name of Company
	 * @apiSuccess {String} gstNo GST number of entity
	 * @apiSuccess {String} companyRegistrationNumber Registration number of company
	 * @apiSuccess {String} entityType Entity Type of company
	 * @apiSuccess {boolean} gstApplicable If GST is applicable for company
	 * @apiSuccess {String} industryType Industry type of company
	 * @apiSuccess{String} country Company's country of origin
	 * @apiSuccess {boolean} active If company is active
	 * @apiSuccess {Array} documentsMetaDataList list of document metadata
	 *	@apiSuccess {String} fileName name of file
	 *	@apiSuccess {String} guid GUID of file
	 *	@apiSuccess {String} title Title of file
	 *	@apiSuccess {LocalDate} createdAt Date file was created
	 *	@apiSuccess {LocalDate} updatedAt Date file was updated 
	 *
	 * 
	 * 
	 * @apiSuccessExample Success-Response Example:
	 * 	HTTP/1.1 200 OK
	 * 	{
	 * 		"status":"OK",
	 * 		"data":{
	 * 			"uuid":"0589c6a7-aa62-4626-af5f-684bd3c8e927",
	 * 			"entityName":"Testing1",
	 * 			"gstNo":"Testing1",
	 * 			"companyRegistrationNumber":"Testing23",
	 * 			"entityType":"LIMITED LIABILITY COMPANY",
	 * 			"industryType":"CONSTRUCTION",
	 * 			"gstApplicable":true,
	 * 			"country":"Singapore",
	 * 			"documentsMetaDataList":[
	 * 				{
	 * 					"guid":"7e148f7339684599809697bb9sdaf3ba",
	 * 					"title":"sdasdadasdasd",
	 * 					"fileName":"testingPDF1.pdf",
	 * 					"createdAt":"22/04/2021",
	 * 					"updatedAt":"22/04/2021"
	 * 				}
	 * 			],
	 * 			"active":true,
	 * 		},
	 * 		"timestamp":1619078481486,
	 * 		"statusCode":200
	 * 	}
	 * 
	 * @apiErrorExample Error-Response Example:
	 * 	HTTP/1.1 404 Not Found
	 * 	{
	 * 		"status":"NOT_FOUND",
	 * 		"message":"Cannot retrieve company",
	 * 		"timestamp":1619079114220,
	 * 		"statusCode":404
	 * 	}
	 * 
	 * @param {Next} next 
	 */

	// @PreAuthorize("hasAuthority('ENTITY_ADMIN')")
	@GetMapping(ControllerPath.GET_COMPANY)
	public ResponseEntity<ApiResponse> getCompanyDetails(@PathVariable @NotEmpty String uuid) {
		ApiResponse apiResponse = companiesService.getCompanyDetails(uuid);
		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
	}
	
	/**
	 * @api {post} company/update Update Company
	 * @apiDescription To update company
	 * @apiGroup Manage Company
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {String} uuid Unique uuid of Company
	 * @apiParam {String} entityName Name of Company
	 * @apiParam {String} gstNo GST number of entity
	 * @apiParam {String} companyRegistrationNumber Registration number of company
	 * @apiParam {String} entityType Entity Type of company
	 * @apiParam {boolean} gstApplicable If GST is applicable for company
	 * @apiParam {String} industryType Industry type of company
	 * @apiParam{String} country Company's country of origin
	 * @apiParam {Array} documentsMetaDataList list of document metadata
	 * 	@apiParam {String} fileName name of file
	 * 	@apiParam {String} guid GUID of file
	 * 	@apiParam {String} title of file
	 * 
	 * @apiParamExample {json} Request-Example:
	 * {
	 * 		"uuid": "0589c6a7-aa62-4626-af5f-684bd3c8e927", 
	 * 		"entityName": "Testing12", 
	 * 		"gstNo": "Testing1", 
	 * 		"companyRegistrationNumber": "Testing23", 
	 * 		"entityType": "LIMITED LIABILITY COMPANY", 
	 * 		"gstApplicable": true, 
	 * 		"industryType": "CONSTRUCTION", 
	 * 		"country": "Singapore", 
	 * 		"documentsMetaDataList": [
	 * 			{
	 * 				"fileName": "testingPDF1.pdf", 
	 * 				"guid": "7e148f7339684599809697bb9sdaf3ba", 
	 * 				"title": "sdasdadasdasd"
	 * 			}
	 * 		]
	 * }
	 * 
	 * @apiHeader {String} Authorization JWT token
	 * 
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	{
	 * 		"status":"OK",
	 * 		"data":"Company updated for: Testing12",
	 * 		"timestamp":1619105448374,
	 * 		"statusCode":200
	 * 	}
	 * 
	 * @apiErrorExample Error-Response:
	 * 	HTTP/1.1 404 Not Found
	 * 	{
	 * 		"status":"NOT_FOUND",
	 * 		"message":"Cannot retrieve company",
	 * 		"timestamp":1619079114220,
	 * 		"statusCode":404
	 * 	}
	 * 
	 * @param {Next} next 
	 */

	// @PreAuthorize("hasAuthority('ENTITY_ADMIN')")
//	@PostMapping(ControllerPath.UPDATE_COMPANY)
//	public ResponseEntity<ApiResponse> updateEntity(@Valid @RequestBody UpdateCompanyDto updateCompanyDto) {
//		ApiResponse apiResponse = companiesService.updateCompanies(updateCompanyDto);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}

	/**
	 * @api {put} company/delete/:uuid Deactivate Company
	 * @apiDescription To deactivate company
	 * @apiGroup Manage Company
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {String} uuid company's unique ID
	 * 
	 * @apiHeader {String} Authorization JWT token
	 * 
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	{
	 * 		"status":"OK",
	 * 		"data":"Company deactivated: Testing12",
	 * 		"timestamp":1619107101421,
	 * 		"statusCode":200
	 * 	}
	 * 
	 * @apiErrorExample Error-Response:
	 * 	HTTP/1.1 404 Not Found
	 * 	{
	 * 		"status":"NOT_FOUND",
	 * 		"message":"Cannot retrieve company",
	 * 		"timestamp":1619108941126,
	 * 		"statusCode":404
	 * 	}
	 * 
	 * @param {Next} next 
	 */
	
	// @PreAuthorize("hasAuthority('ENTITY_ADMIN')")
//	@PutMapping(ControllerPath.DELETE_COMPANY)
//	public ResponseEntity<ApiResponse> deleteCompany(@PathVariable @NotEmpty String uuid) {
//		ApiResponse apiResponse = companiesService.markCompanyDeleted(uuid);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}

	/**
	 * @api {put} company/reactivate/:uuid Reactivate Company
	 * @apiName To reactivate company
	 * @apiGroup Manage Company
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {String} uuid company's unique ID
	 * 
	 * @apiHeader {String} Authorization JWT token
	 * 
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	{
	 * 		"status":"OK",
	 * 		"data":"Company reactivated: Testing12",
	 * 		"timestamp":1619139921531,
	 * 		"statusCode":200
	 * 	}
	 * 
	 * @apiErrorExample Error-Response:
	 * 	HTTP/1.1 404 Not Found
	 * 	{
	 * 		"status":"NOT_FOUND",
	 * 		"message":"Cannot retrieve company",
	 * 		"timestamp":1619108941126,
	 * 		"statusCode":404
	 * 	}
	 * 
	 * @param {Next} next 
	 */
	
	// @PreAuthorize("hasAuthority('ENTITY_ADMIN')")
//	@PutMapping(ControllerPath.REACTIVATE_COMPANY)
//	public ResponseEntity<ApiResponse> reactivateCompany(@PathVariable @NotEmpty String uuid) {
//		ApiResponse apiResponse = companiesService.reactivateCompany(uuid);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
		
   /**
     * @api {get} company/all/list List all companies
     * @apiGroup Manage Company
     * @apiVersion 0.0.1
	 *
	 * @apiHeader {String} Authorization JWT token
	 * 
     * @apiSuccessExample Success-Response:
     *   HTTP/1.1 200 OK
	 *	 {
	 *	    "status": "OK",
	 *      "data": [
	 *	        {
	 *	            "onboardingStatus": "APPROVED",
	 *	            "active": "true",
	 *	            "entityName": "Company1",
	 *	            "uuid": "11111",
	 *	            "country": "Singapore",
	 *	            "companyRegistrationNumber": "123123",
	 *	            "createdAt": "24/03/2021",
	 *	            "subscriptionExpiry": "22/04/2022"
	 *	        }
	 *	    ],
	 *	    "timestamp": 1619083490713,
	 *	    "statusCode": 200
	 *	 }
     *
     * 
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 
	 *	{
	 *   	"status": "INTERNAL_SERVER_ERROR",
	 *   	"message": "Exception Error Message",
	 *   	"timestamp": 1619083958882,
	 *   	"statusCode": 500
	 *	}
     * 
     * @apiDescription List every single companies under the entity (Including default company)
     * 
     * @param {Next} next 
     */
//	@GetMapping(ControllerPath.LIST_ALL_COMPANIES)
//	public ResponseEntity<ApiResponse> retrieveAllCompaniesUnderEntity(){
//		ApiResponse apiResponse = companiesService.listAllCompaniesUnderEntity("");
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}


}
