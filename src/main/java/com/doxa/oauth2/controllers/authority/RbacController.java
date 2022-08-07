//package com.doxa.oauth2.controllers.authority;
//
//import com.doxa.oauth2.DTO.authority.CreateRoleDto;
//import com.doxa.oauth2.common.ControllerPath;
//import com.doxa.oauth2.responses.ApiResponse;
//import com.doxa.oauth2.serviceImpl.RbacServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * @author vuducnoi
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(ControllerPath.RBAC)
//public class RbacController {
//
//	private final RbacServiceImpl service;
//
//	/**
//	 * @api  {post} /auth/api/:companyUuid/rbac/role Create RBAC Role
//	 * @apiDescription @author: Noi <br />
//	 * @apiGroup RBAC
//	 * @apiVersion 0.0.1
//	 *
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiSuccess {String} name Role name
//	 * @apiSuccess {String} [description] Description
//	 * @apiSuccess {Authority[]} permissions List of authority
//	 * @apiSuccess {String} permissions.featureCode Feature Code
//	 * @apiSuccess {String[]} permissions.actions List of actions allowed
//	 * @apiSuccess {String} permissions.actions.action read | write | approve
//
//	 * @apiSuccessExample {json} Success-Response:
//	 *    {
//	 *     "status": "OK",
//	 *     "message": "",
//	 *     "timestamp": 1637720702367,
//	 *     "statusCode": 200
//	 *  }
//	 */
//	@PostMapping(ControllerPath.RBAC_ROLE)
//	public ResponseEntity<ApiResponse> createRole(@PathVariable String companyUuid, @RequestBody @Valid CreateRoleDto body) {
//		ApiResponse apiResponse = service.createRole(companyUuid, body);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	/**
//	 * @api  {get} /auth/api/:companyUuid/rbac/role List all role under the company
//	 * @apiDescription @author: Noi <br />
//	 * @apiGroup RBAC
//	 * @apiVersion 0.0.1
//	 *
//	 *
//	 * @apiHeader {String} Authorization JWT token
//
//	 * @apiSuccessExample {json} Success-Response:
//	 * {
//	 *     "status": "OK",
//	 *     "data": [
//	 *         {
//	 *             "uuid": "0d614681-e8cd-4b7d-8764-cf6b30352793",
//	 *             "name": "Test",
//	 *             "companyUuid": "60419a9f-ff39-4fd9-a9f5-97fb2e17b6d6",
//	 *             "description": "This is the test",
//	 *             "status": "CREATED",
//	 *             "createdBy": "Bartell Group",
//	 *             "createdAt": "2021-11-23 08:51:43",
//	 *             "permissions": null
//	 *         },
//	 *         {
//	 *             "uuid": "08dad372-21d2-40aa-b236-368cfc0e0b43",
//	 *             "name": "Test 3",
//	 *             "companyUuid": "60419a9f-ff39-4fd9-a9f5-97fb2e17b6d6",
//	 *             "description": "This is the test",
//	 *             "status": "CREATED",
//	 *             "createdBy": "Bartell Group",
//	 *             "createdAt": "2021-11-24 02:25:02",
//	 *             "permissions": null
//	 *         }
//	 *     ],
//	 *     "timestamp": 1637720916190,
//	 *     "statusCode": 0
//	 * }
//	 */
//	@GetMapping(ControllerPath.RBAC_ROLE)
//	public ResponseEntity<ApiResponse> listAll(@PathVariable String companyUuid) {
//		ApiResponse apiResponse = service.listRoles(companyUuid);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	/**
//	 * @api  {put} /auth/api/:companyUuid/rbac/role/:uuid Update role
//	 * @apiDescription @author: Noi <br />
//	 * @apiGroup RBAC
//	 * @apiVersion 0.0.1
//	 *
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiParam {String} uuid Role uuid to update
//	 * @apiParam {String} companyUuid Current company
//	 * @apiParam {String} name Role name
//	 * @apiParam {String} [description] Description
//	 * @apiParam {Authority[]} permissions List of authority
//	 * @apiParam {String} permissions.featureCode Feature Code
//	 * @apiParam {String[]} permissions.actions List of actions allowed
//	 * @apiParam {String} permissions.actions.action read | write | approve
//
//	 * @apiSuccessExample {json} Success-Response:
//	 * {
//	 *     "status": "OK",
//	 *     "message": "Saved successfully",
//	 *     "timestamp": 1637721769857,
//	 *     "statusCode": 0
//	 * }
//	 */
//	@PutMapping(ControllerPath.RBAC_ROLE + "/{uuid}")
//	public ResponseEntity<ApiResponse> update(@PathVariable String companyUuid,
//	                                          @PathVariable String uuid,
//	                                          @RequestBody @Valid CreateRoleDto body) {
//		ApiResponse apiResponse = service.updateRole(companyUuid, uuid, body);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	/**
//	 * @api  {get} /auth/api/:companyUuid/rbac/role/:uuid Get role detail
//	 * @apiDescription @author: Noi <br />
//	 * @apiGroup RBAC
//	 * @apiVersion 0.0.1
//	 *
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiParam {String} uuid Role uuid
//	 * @apiParam {String} companyUuid Current company
//
//	 * @apiSuccessExample {json} Success-Response:
//	 * {
//	 *     "status": "OK",
//	 *     "data": {
//	 *         "uuid": "08dad372-21d2-40aa-b236-368cfc0e0b43",
//	 *         "name": "Test 3",
//	 *         "companyUuid": "60419a9f-ff39-4fd9-a9f5-97fb2e17b6d6",
//	 *         "description": "This is the test",
//	 *         "status": "CREATED",
//	 *         "createdBy": "Bartell Group",
//	 *         "createdAt": "2021-11-24 02:25:02",
//	 *         "permissions": [
//	 *             {
//	 *                 "read": true,
//	 *                 "write": true,
//	 *                 "approve": false,
//	 *                 "feature": {
//	 *                     "uuid": "a8d457ed-6a4d-477c-99d3-ac8b5027c724",
//	 *                     "featureName": "Purchase Requisition",
//	 *                     "featureCode": "PR",
//	 *                     "category": "Requisitions",
//	 *                     "profile": "USER",
//	 *                     "subCategory": "Requisitions",
//	 *                     "moduleCode": "P2P"
//	 *                 }
//	 *             },
//	 *             {
//	 *                 "read": true,
//	 *                 "write": true,
//	 *                 "approve": false,
//	 *                 "feature": {
//	 *                     "uuid": "722eabd7-f38e-4c62-b256-8df1243b468d",
//	 *                     "featureName": "Purchase Order",
//	 *                     "featureCode": "PO",
//	 *                     "category": "Orders",
//	 *                     "profile": "USER",
//	 *                     "subCategory": "Orders List",
//	 *                     "moduleCode": "P2P"
//	 *                 }
//	 *             }
//	 *         ]
//	 *     },
//	 *     "timestamp": 1637721772215,
//	 *     "statusCode": 0
//	 * }
//	 */
//	@GetMapping(ControllerPath.RBAC_ROLE + "/{uuid}")
//	public ResponseEntity<ApiResponse> getDetail(@PathVariable String companyUuid,
//	                                             @PathVariable String uuid) {
//		ApiResponse apiResponse = service.roleDetails(companyUuid, uuid);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	/**
//	 * @api  {delete} /auth/api/:companyUuid/rbac/role/:uuid Delete a role
//	 * @apiDescription @author: Noi <br />
//	 * Role can't be deleted if it's being used
//	 * @apiGroup RBAC
//	 * @apiVersion 0.0.1
//	 *
//	 *
//	 * @apiHeader {String} Authorization JWT token
//	 *
//	 * @apiParam {String} uuid Role uuid
//	 * @apiParam {String} companyUuid Current company
//
//	 * @apiSuccessExample {json} Success-Response:
//	 * {
//	 *     "status": "OK",
//	 *     "data": null,
//	 *     "message": "Deleted successfully",
//	 *     "timestamp": 1637721772215,
//	 *     "statusCode": 0
//	 * }
//	 */
//	@DeleteMapping(ControllerPath.RBAC_ROLE + "/{uuid}")
//	public ResponseEntity<ApiResponse> deleteRole(@PathVariable String companyUuid,
//	                                             @PathVariable String uuid) {
//		ApiResponse apiResponse = service.deleteRole(companyUuid, uuid);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	@PostMapping(ControllerPath.RBAC_ASSIGN_TO_USER + "/{uuid}")
//	public ResponseEntity<ApiResponse> assignRoleToUser(@PathVariable String companyUuid,
//	                                                    @PathVariable String uuid,
//	                                                    @RequestBody List<String> rbacRoles) {
//		ApiResponse apiResponse = service.assignRoleToUser(companyUuid, uuid, rbacRoles);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	@GetMapping(ControllerPath.RBAC_ASSIGN_TO_USER + "/{uuid}")
//	public ResponseEntity<ApiResponse> getRoleAssignedToUser(@PathVariable String companyUuid,
//	                                                         @PathVariable String uuid) {
//		ApiResponse apiResponse = service.listRoleOfUser(companyUuid, uuid);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//}
