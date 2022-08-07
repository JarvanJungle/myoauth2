//package com.doxa.oauth2.controllers.authority;
//
//import com.doxa.oauth2.DTO.manageAdminMatrix.SubAdminDto;
//import com.doxa.oauth2.common.ControllerPath;
//import com.doxa.oauth2.responses.ApiResponse;
//import com.doxa.oauth2.serviceImpl.ManageAdminPermissionServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping(ControllerPath.MANAGE_SUBADMIN)
//public class ManageAdminPermissionController {
//    @Autowired
//    ManageAdminPermissionServiceImpl iManageAdminPermissionService;
//
//    /**
//     * @api  {get} /subadmin/get-details?userUuid={userUuid}&companyUuid={CompanyUuid} Get user's details from a company
//     * @apiDescription Get user's details from a company
//     * @apiDescription @author: Andrew
//     * @apiGroup SubAdmin
//     * @apiVersion 0.0.1
//     *
//     *  @apiParam {String} userUuid user's unique ID
//     *  @apiParam {String} companyUuid company's unique ID
//     *
//     * @apiHeader {String} Authorization JWT token
//     *
//     * @apiSuccess {String} administrativeCode
//     * @apiSuccess (list) {String} categoryCode
//     *
//     * @apiSuccessExample Success-Response Example:
//     * {
//     *     "status": "OK",
//     *     "data": [
//     *         {
//     *             "administrativeCode": "MANAGE_USERS",
//     *             "adminCategories": {
//     *                 "categoryCode": "USER_MANAGEMENT"
//     *             }
//     *         },
//     *         {
//     *             "administrativeCode": "MANAGE_CURRENCY",
//     *             "adminCategories": {
//     *                 "categoryCode": "ENTITY_SETUP"
//     *             }
//     *         },
//     *         {
//     *             "administrativeCode": "MANAGE_BANK_CONNECTION",
//     *             "adminCategories": {
//     *                 "categoryCode": "BANK_CONNECTION"
//     *             }
//     *         }
//     *     ],
//     *     "timestamp": 1621438660489,
//     *     "statusCode": 0
//     * }
//     *
//     * @apiErrorExample Error-Response Example:
//     *           {
//     *           "status": "NOT_FOUND",
//     *           "data": "UserId: 2221 does not exist",
//     *           "timestamp": 1619432308514,
//     *           "statusCode": 0
//     *       }
//     *
//     * @param {Next} next
//     */
//
//    @GetMapping(ControllerPath.USER_SUBADMIN_PERMISSIONS )
//    public ResponseEntity<ApiResponse> userSubAdminPermission(@RequestParam String userUuid, @RequestParam String companyUuid){
//        ApiResponse apiResponse = (iManageAdminPermissionService.userSubAdminPermission(userUuid, companyUuid));
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }
//
//    /**
//     * @api {get} /subadmin/list?uuid={CompanyUuid} List all the user's permission within a company
//     * @apiDescription List all the user's permission within a company
//     * @apiGroup SubAdmin
//     * @apiVersion 0.0.1
//     * @apiParam {String} companyUuid company's uuid
//     *
//     *
//     *
//     * @apiHeader {String} Authorization JWT token
//     *
//     * @apiSuccess (list) {String} administrativeCode
//     * @apiSuccess (list) (list) {String} categoryCode
//
//     *
//     * @apiSuccessExample Success-Response Example:
//     * {
//     *     "status": "OK",
//     *     "data": [
//     *         {
//     *             "userUuid": "222",
//     *             "administrativesDtoList": [
//     *                 {
//     *                     "administrativeCode": "MANAGE_CURRENCY",
//     *                     "adminCategories": {
//     *                         "categoryCode": "ENTITY_SETUP"
//     *                     }
//     *                 },
//     *                 {
//     *                     "administrativeCode": "MANAGE_BANK_CONNECTION",
//     *                     "adminCategories": {
//     *                         "categoryCode": "BANK_CONNECTION"
//     *                     }
//     *                 }
//     *             ]
//     *         },
//     *         {
//     *             "userUuid": "333",
//     *             "administrativesDtoList": [
//     *                 {
//     *                     "administrativeCode": "MANAGE_USERS",
//     *                     "adminCategories": {
//     *                         "categoryCode": "USER_MANAGEMENT"
//     *                     }
//     *                 },
//     *                 {
//     *                     "administrativeCode": "MANAGE_CURRENCY",
//     *                     "adminCategories": {
//     *                         "categoryCode": "ENTITY_SETUP"
//     *                     }
//     *                 },
//     *                 {
//     *                     "administrativeCode": "MANAGE_BANK_CONNECTION",
//     *                     "adminCategories": {
//     *                         "categoryCode": "BANK_CONNECTION"
//     *                     }
//     *                 }
//     *             ]
//     *         }
//     *     ],
//     *     "timestamp": 1619429420444,
//     *     "statusCode": 0
//     * }
//     *
//     * @apiErrorExample Error-Response Example:
//     *     {
//     *     "status": "NOT_FOUND",
//     *     "data": "UserId: 2221 does not exist",
//     *     "timestamp": 1619432308514,
//     *     "statusCode": 0
//     * }
//     *
//     * @param {Next} next
//     */
//    @GetMapping(ControllerPath.LIST_SUBADMIN_PERMISSIONS )
//    public ResponseEntity<ApiResponse> listSubAdminPermission(@RequestParam String uuid){
//        ApiResponse apiResponse = (iManageAdminPermissionService.listSubAdminPermissions(uuid));
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }
//
//    /**
//     * @api {post} /subadmin/update Create and update permission for one user
//     * @apiDescription create or update an user sub admin privileges
//     * @apiGroup SubAdmin
//     * @apiVersion 0.0.1
//     * @apiParam {String} userUuid user's uuid
//     * @apiParam {String} companyUuid company's uuid
//     * @apiParam (List){String} administrativeCode - the administrative code
//     *
//     *@apiParamExample {json} Request-Example:
//     * {
//     *     "userUuid":"222",
//     *     "companyUuid":"333333",
//     *     "administrativesDtoList": [
//     *
//     *                 {
//     *                     "administrativeCode": "MANAGE_CURRENCY",
//     *                     "adminCategories": {
//     *                         "categoryCode": "ENTITY_SETUP"
//     *                     }
//     *                 },
//     *                 {
//     *                     "administrativeCode": "MANAGE_BANK_CONNECTION",
//     *                     "adminCategories": {
//     *                         "categoryCode": "BANK_CONNECTION"
//     *                     }
//     *                 }
//     *             ]
//     * }
//     *
//     *
//     * @apiHeader {String} Authorization JWT token
//     *
//     * @apiSuccess (list){String} administrativeCode
//     * @apiSuccess (list)(list){String} categoryCode
//
//     *
//     * @apiSuccessExample Success-Response Example:
//     *     {
//     *     "status": "OK",
//     *     "data": [
//     *         {
//     *             "administrativeCode": "MANAGE_CURRENCY",
//     *             "adminCategories": {
//     *                 "categoryCode": "ENTITY_SETUP"
//     *             }
//     *         },
//     *         {
//     *             "administrativeCode": "MANAGE_BANK_CONNECTION",
//     *             "adminCategories": {
//     *                 "categoryCode": "BANK_CONNECTION"
//     *             }
//     *         }
//     *     ],
//     *     "timestamp": 1619429435059,
//     *     "statusCode": 0
//     * }
//     *
//     * @apiErrorExample Error-Response Example:
//     *     {
//     *     "status": "NOT_FOUND",
//     *     "data": "UserId: 2221 does not exist",
//     *     "timestamp": 1619432308514,
//     *     "statusCode": 0
//     * }
//     *
//     * @param {Next} next
//     */
//    //update the database row and if the user does not exist then add a new row
//    @PostMapping(ControllerPath.GRANT_SUBADMIN_PERMISSIONS )
//    public ResponseEntity<ApiResponse> updateSubAdminPermission(@RequestBody @Validated SubAdminDto subAdminDto){
//        ApiResponse apiResponse =(iManageAdminPermissionService.updateSubAdminPermission(subAdminDto));
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }
//
//
//
//
//
//}
