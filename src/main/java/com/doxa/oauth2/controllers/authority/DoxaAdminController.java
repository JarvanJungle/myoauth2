package com.doxa.oauth2.controllers.authority;

import com.doxa.oauth2.DTO.authority.CreateRoleDto;
import com.doxa.oauth2.common.ControllerPath;
import com.doxa.oauth2.config.AppConfig;
import com.doxa.oauth2.responses.ApiResponse;
import com.doxa.oauth2.serviceImpl.AuthorityService;
//import com.doxa.oauth2.serviceImpl.RbacServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(ControllerPath.DOXA_ADMIN_CONTROLLER)
public class DoxaAdminController {

    @Autowired
    private AuthorityService authorityService;

//    @Autowired
//    private RbacServiceImpl rbacService;

    /**
     * @api  {get} /auth/api/dox/module Get all core modules
     * @apiDescription @author: Noi <br />
     *  Role Allowed: <b>DOXA_ADMIN</b>
     * @apiGroup Authority
     * @apiVersion 0.0.1
     *
     *
     * @apiHeader {String} Authorization JWT token
     *
     * @apiSuccess {String} uuid Module's uuid
     * @apiSuccess {String} moduleName Module Name
     * @apiSuccess {String} moduleCode Module Code
     * @apiSuccess {String} moduleName Module Name
     * @apiSuccess {Object[]} features List of feature
     * @apiSuccess {String} features.featureName Feature Name
     * @apiSuccess {String} features.featureCode Feature Name
     * @apiSuccess {String} features.moduleCode Module Code
     * @apiSuccessExample {json} Success-Response:
     * {
     *       "uuid": "f0a4f5a6-a21c-492b-8a21-1f22178aa16d",
     *       "moduleName": "Procurement To Pay",
     *       "moduleCode": "P2P",
     *       "features": [
     *         {
     *           "featureName": "Pre Purchase Requisition",
     *           "featureCode": "PPR",
     *           "moduleCode": "P2P"
     *         }]
     *  }
     */
//    @GetMapping(ControllerPath.DOXA_ADMIN_MODULES)
//    private ResponseEntity<ApiResponse> getCoreModules() {
//        ApiResponse apiResponse = authorityService.getAllCoreModules();
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    /**
     * @api  {get} /auth/api/dox/module/:companyUuid Grant access to modules to company
     * @apiDescription @author: Noi <br />
     *  Role Allowed: <b>DOXA_ADMIN</b>
     * @apiGroup Authority
     * @apiVersion 0.0.1
     *
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String[]} features List of features
     *
     * @apiParamExample {json} Request-Example:
     * [
     * 	"PO",
     * 	"PR",
     * 	"INVF"
     * ]
     */
//    @PostMapping(ControllerPath.ASSIGN_MODULES)
//    private ResponseEntity<ApiResponse> grantAccess(@PathVariable String companyUuid, @RequestBody @NotNull List<String> moduleCodes) {
//        ApiResponse apiResponse = authorityService.assignModuleToCompany(companyUuid, moduleCodes);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    /**
     * @api  {put} /auth/api/dox/rbac/role/:uuid Update default role
     * @apiDescription @author: Noi <br />
     * @apiGroup RBAC-Admin
     * @apiVersion 0.0.1
     *
     *
     * @apiHeader {String} Authorization JWT token
     *
     * @apiParam {String} uuid Role uuid to update
     * @apiParam {String} companyUuid Current company
     * @apiParam {String} name Role name
     * @apiParam {String} [description] Description
     * @apiParam {Authority[]} permissions List of authority
     * @apiParam {String} permissions.featureCode Feature Code
     * @apiParam {String[]} permissions.actions List of actions allowed
     * @apiParam {String} permissions.actions.action read | write | approve

     * @apiSuccessExample {json} Success-Response:
     * {
     *     "status": "OK",
     *     "message": "Saved successfully",
     *     "timestamp": 1637721769857,
     *     "statusCode": 0
     * }
     */
//    @PutMapping("/rbac" + ControllerPath.RBAC_ROLE + "/{uuid}")
//    public ResponseEntity<ApiResponse> updateDefaultRole(@PathVariable String uuid,
//                                                         @RequestBody @Valid CreateRoleDto body) {
//        ApiResponse apiResponse = rbacService.updateRole(AppConfig.DOXA_COMPANY_UUID, uuid, body);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    /**
     * @api  {get} /auth/api/dox/rbac/role/:uuid View detail default role
     * @apiDescription @author: Noi <br />
     * @apiGroup RBAC-Admin
     * @apiVersion 0.0.1
     *
     *
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample {json} Success-Response:
     * {
     *     "status": "OK",
     *     "data": {
     *         "uuid": "08dad372-21d2-40aa-b236-368cfc0e0b43",
     *         "name": "Test 3",
     *         "companyUuid": "60419a9f-ff39-4fd9-a9f5-97fb2e17b6d6",
     *         "description": "This is the test",
     *         "status": "CREATED",
     *         "createdBy": "Bartell Group",
     *         "createdAt": "2021-11-24 02:25:02",
     *         "permissions": [
     *             {
     *                 "read": true,
     *                 "write": true,
     *                 "approve": false,
     *                 "feature": {
     *                     "uuid": "a8d457ed-6a4d-477c-99d3-ac8b5027c724",
     *                     "featureName": "Purchase Requisition",
     *                     "featureCode": "PR",
     *                     "category": "Requisitions",
     *                     "profile": "USER",
     *                     "subCategory": "Requisitions",
     *                     "moduleCode": "P2P"
     *                 }
     *             },
     *             {
     *                 "read": true,
     *                 "write": true,
     *                 "approve": false,
     *                 "feature": {
     *                     "uuid": "722eabd7-f38e-4c62-b256-8df1243b468d",
     *                     "featureName": "Purchase Order",
     *                     "featureCode": "PO",
     *                     "category": "Orders",
     *                     "profile": "USER",
     *                     "subCategory": "Orders List",
     *                     "moduleCode": "P2P"
     *                 }
     *             }
     *         ]
     *     },
     *     "timestamp": 1637721772215,
     *     "statusCode": 0
     * }
     */
//    @GetMapping("/rbac" + ControllerPath.RBAC_ROLE + "/{uuid}")
//    public ResponseEntity<ApiResponse> getDetailsDefaultRole(@PathVariable String uuid) {
//        ApiResponse apiResponse = rbacService.roleDetails(AppConfig.DOXA_COMPANY_UUID, uuid);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    /**
     * @api {post} /auth/api/dox/rbac/role Create default RBAC Role
     * @apiDescription @author: Noi <br />
     * @apiGroup RBAC-Admin
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiSuccess {String} name Role name
     * @apiSuccess {String} [description] Description
     * @apiSuccess {Authority[]} permissions List of authority
     * @apiSuccess {String} permissions.featureCode Feature Code
     * @apiSuccess {String[]} permissions.actions List of actions allowed
     * @apiSuccess {String} permissions.actions.action read | write | approve
     * @apiSuccessExample {json} Success-Response:
     * {
     * "status": "OK",
     * "message": "",
     * "timestamp": 1637720702367,
     * "statusCode": 200
     * }
     */
//    @PostMapping("/rbac" + ControllerPath.RBAC_ROLE)
//    public ResponseEntity<ApiResponse> createRole(@RequestBody @Valid CreateRoleDto body) {
//        ApiResponse apiResponse = rbacService.createDefaultRoles(body);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    /**
     * @api {get} /auth/api/dox/rbac/role List default role
     * @apiDescription @author: Noi <br />
     * @apiGroup RBAC-Admin
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiSuccess {String} name Role name
     * @apiSuccess {String} [description] Description
     * @apiSuccess {Authority[]} permissions List of authority
     * @apiSuccess {String} permissions.featureCode Feature Code
     * @apiSuccess {String[]} permissions.actions List of actions allowed
     * @apiSuccess {String} permissions.actions.action read | write | approve
     * @apiSuccessExample {json} Success-Response:
     * {
     * "status": "OK",
     * "message": "",
     * "timestamp": 1637720702367,
     * "statusCode": 200
     * }
     */
//    @GetMapping("/rbac" + ControllerPath.RBAC_ROLE)
//    public ResponseEntity<ApiResponse> listDefaultRoles() {
//        ApiResponse apiResponse = rbacService.listDefaultRole("0");
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }
}
