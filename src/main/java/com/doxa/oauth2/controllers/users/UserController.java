package com.doxa.oauth2.controllers.users;

//import com.doxa.oauth2.DTO.financialInstitution.FiUserDto;
import com.doxa.oauth2.DTO.manageUser.*;
import com.doxa.oauth2.authorization.UserServiceAuthorizationImpl;
import com.doxa.oauth2.common.ControllerPath;
import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
import com.doxa.oauth2.responses.ApiResponse;
import com.doxa.oauth2.responses.TwoFactorAuth;
import com.doxa.oauth2.serviceImpl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.MethodNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;


@RestController
@RequestMapping(ControllerPath.USERS)
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceAuthorizationImpl userServiceAuthorization;

    /**
     * @param {Next} next
     * @api {post} /entities/users/signin User Login
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiParam {String} email Email of the user
     * @apiParam {String} password Password of the user
     * @apiParamExample {json} Request-Example:
     * {
     * "email":"entityadmin@getnada.com",
     * "password":"123"
     * }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "data": {
     * "accessToken": "<access_token>",
     * "is2FA": false,
     * "mustSetPassword": false,
     * "email": "entityadmin@getnada.com",
     * "name": "Entity Admin",
     * "tokenType": "Bearer",
     * "roles": [
     * "ENTITY_ADMIN",
     * "COMPANY_ADMIN"
     * ],
     * "companies": [
     * {
     * "role": [
     * "ENTITY_ADMIN"
     * ],
     * "companyName": "Company1",
     * "main": true,
     * "companyUuid": "11111"
     * }
     * ]
     * },
     * "timestamp": 1619082457553,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "NOT_FOUND",
     * "message": "Email or Password is incorrect",
     * "timestamp": 1619082632160,
     * "statusCode": 0
     * }
     * @apiDescription Allows user login
     */
    @PostMapping(ControllerPath.LOGIN)
    public ResponseEntity<ApiResponse> signin(@Valid @RequestBody SignInRequest loginRequest) throws MethodNotSupportedException {
//    	ApiResponse apiResponse = userService.login(loginRequest);
        throw new MethodNotSupportedException("Method deprecated");
    }

    /**
     * @param {Next} next
     * @api {get} /entities/users/me Retrieve User Own Details
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "data": {
     * "name": "Entity Admin",
     * "email": "entityadmin@getnada.com",
     * "uuid": "222",
     * "workNumber": "98989898",
     * "designation": "manager",
     * "createdAt": "2021-03-23T16:00:00Z",
     * "isActive": true,
     * "companiesRoles": null,
     * "active": true
     * },
     * "timestamp": 1619082457553,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "NOT_FOUND",
     * "message": "Data not found",
     * "timestamp": 1619082632160,
     * "statusCode": 0
     * }
     * @apiDescription retrieve user's own details
     */
    @GetMapping(ControllerPath.DETAILS_OWN)
    public ResponseEntity<ApiResponse> getSelfDetails() {
        ApiResponse apiResponse = userService.getUserOwnDetails();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @param {Next} next
     * @api {get} /entities/users/update-avatar User self-update avatar
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} avatarUrl URL of User avatar.
     * @apiParamExample {json} Request-Example:
     * {
     * "avatarUrl":"http://image-url",
     * }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619082457553,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "NOT_FOUND",
     * "message": "Data not found",
     * "timestamp": 1619082632160,
     * "statusCode": 0
     * }
     * @apiDescription retrieve user's own details
     */
    @PostMapping(ControllerPath.UPDATE_AVATAR)
    public ResponseEntity<ApiResponse> updateAvatar(@RequestBody UpdateAvatarDto updateAvatarDto) {
        ApiResponse apiResponse = userService.updateOwnAvatar(updateAvatarDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {get} /entities/users/details/:uuid Retrieve Other User's Details
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiParam {Long} id Users unique UUID.
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "data": {
     * "name": "Entity Admin",
     * "email": "entityadmin@getnada.com",
     * "uuid": "222",
     * "workNumber": "98989898",
     * "designation": "manager",
     * "createdAt": "2021-03-23T16:00:00Z",
     * "isActive": true,
     * "companiesRoles": [
     * {
     * "role": [
     * "ENTITY_ADMIN"
     * ],
     * "companyName": "Company1",
     * "main": true,
     * "companyUuid": "11111"
     * }
     * ]
     * "active": true
     * },
     * "timestamp": 1619082457553,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "NOT_FOUND",
     * "message": "Data not found",
     * "timestamp": 1619082632160,
     * "statusCode": 0
     * }
     * @apiDescription Retrieve other user's details by using user's uuid
     */
//    @PreAuthorize("hasAnyAuthority('DOXA_ADMIN','ENTITY_ADMIN','COMPANY_ADMIN')")
    @GetMapping(ControllerPath.USER_DETAILS)
    public ResponseEntity<ApiResponse> getUserDetails(@PathVariable("id") String id) {
        ApiResponse apiResponse = userService.getUserDetails(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @param {Next} next
     * @api {post} /entities/users/setup-password User setup password
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiParam {String} currentPassword Current password of the user
     * @apiParam {String} newPassword new password of the user
     * @apiParam {String} email Email of the user
     * @apiParamExample {json} Request-Example:
     * {
     * "currentPassword":"123",
     * "newPassword":"123",
     * "email":"entityadmin@getnada.com"
     * }
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Wrong Password",
     * "timestamp": 1619083529544,
     * "statusCode": 0
     * }
     * @apiDescription Allows user to setup password during first login
     */
    @PostMapping(ControllerPath.SETUP_PASSWORD)
    public ResponseEntity<ApiResponse> setupPassword(@Valid @RequestBody SetupPassword setupPassword) {
        ApiResponse apiResponse = userService.updatePassword(setupPassword);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {post} /entities/users/twofa/signin Verify 2FA during signin
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiParam {String} firstPin First 2FA pin
     * @apiParamExample {json} Request-Example:
     * {
     * "firstPin":"836157"
     * }
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "data": {
     * "accessToken": "....jwt token",
     * "is2FA": true,
     * "mustSetPassword": false,
     * "email": "entityadmin@getnada.com",
     * "name": "Entity Admin",
     * "tokenType": "Bearer",
     * "roles": [
     * "ENTITY_ADMIN",
     * "COMPANY_ADMIN"
     * ],
     * "companies": [
     * {
     * "role": [
     * "ENTITY_ADMIN"
     * ],
     * "companyName": "Company1",
     * "main": true,
     * "companyUuid": "11111"
     * }
     * ]
     * },
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Wrong 2FA pin",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Verify 2FA pins during user signin
     */
    @PostMapping(ControllerPath.SIGNIN_2FA)
    public ResponseEntity<ApiResponse> TwoFactorAuthentication(@Valid @RequestBody TwoFactorAuth twoFactorAuth) {
        ApiResponse apiResponse = userService.signin2FA(twoFactorAuth);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {get} /entities/users/twofa/signup Retrieve QR Code for 2FA SignUp
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "data": {
     * "base64": "...base64",
     * "secretKey": "VXEEZDT24IZS27WB6ZALO7XA7AFI5JM5"
     * },
     * "timestamp": 1619082457553,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Error Message",
     * "timestamp": 1619083529544,
     * "statusCode": 0
     * }
     * @apiDescription retrieve user's secret key for QR Code of 2FA setup
     */
    @GetMapping(ControllerPath.TWOFA_SIGNUP)
    public ResponseEntity<ApiResponse> TwoFactorSignUp() {
        ApiResponse apiResponse = userService.getQRCode();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @param {Next} next
     * @api {post} /entities/users/twofa/verification Verify 2FA for 2FA SignUp
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiParam {String} firstPin First 2FA pin
     * @apiParam {String} secondPin Second 2FA pin
     * @apiParamExample {json} Request-Example:
     * {
     * "firstPin":"836157",
     * "secondPin":"767574"
     * }
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Verify 2FA pins of user during 2FA signup
     */
//    @PostMapping(ControllerPath.TWOFA_VERIFICATION)
//    public ResponseEntity<ApiResponse> TwoFactorVerification(@Valid @RequestBody TwoFactorAuth twoFactorAuth) {
//        ApiResponse apiResponse = userService.checkTwoFactorCodeForOptIn(twoFactorAuth);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }


    /**
     * @api {post} /entities/users/password/reset Reset password for other users
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiParam {String} uuid User unique uuid
     * @apiParam {String} password User new password
     * @apiParam {String} needResetPassword Does user need to reset password
     * @apiParamExample {json} Request-Example:
     * {
     * "uuid":"222",
     * "password":"123",
     * "needResetPassword":"false"
     * }
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Exception Error Message",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Reset password for other user
     */
//	@PreAuthorize("hasAnyAuthority('DOXA_ADMIN','ENTITY_ADMIN','COMPANY_ADMIN')")
    @PostMapping(ControllerPath.PASSWORD_RESET)
    public ResponseEntity<ApiResponse> ResetPassword(@Valid @RequestBody ResetFAPassword resetFAPassword) {
        ApiResponse apiResponse = userService.resetPassword(resetFAPassword);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @param {Next} next
     * @api {post} /entities/users/password/own/reset Reset own password
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiParam {String} oldPassword User old password
     * @apiParam {String} newPassword User new password
     * @apiParamExample {json} Request-Example:
     * {
     * "oldPassword":"123",
     * "newPassword":"123"
     * }
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Wrong Password",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Reset own password
     */
    @PostMapping(ControllerPath.PASSWORD_RESET_OWN)
    public ResponseEntity<ApiResponse> ResetOwnPassword(@Valid @RequestBody ResetPassword resetPassword) {
        ApiResponse apiResponse = userService.resetOwnPassword(resetPassword);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {post} /entities/users/twofa/reset Reset 2FA for other users
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiParam {String} uuid User unique uuid
     * @apiParamExample {json} Request-Example:
     * {
     * "uuid":"222"
     * }
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Exception Error Message",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Reset 2FA for other user
     */
//	@PreAuthorize("hasAnyAuthority('DOXA_ADMIN','ENTITY_ADMIN','COMPANY_ADMIN')")
    @PostMapping(ControllerPath.TWOFA_RESET)
    public ResponseEntity<ApiResponse> ResetTwoFA(@Valid @RequestBody ResetFAPassword resetFAPassword) {
        ApiResponse apiResponse = userService.resetTwoFAOther(resetFAPassword);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {put} /entities/users/twofa/own/reset Reset own 2FA
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Exception Error Message",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Reset own 2FA
     */
    @PutMapping(ControllerPath.TWOFA_RESET_OWN)
    public ResponseEntity<ApiResponse> DisableOwnTwoFA() {
        userService.selfReset2FA();
        ApiResponse apiResponse = userService.selfReset2FA();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @param {Next} next
     * @api {get} /entities/users/entity/:uuid Get EntityAdmin UUID from EntityUUID
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} uuid Users unique UUID.
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "data": "222",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "NOT_FOUND",
     * "message": "Data not found",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Get EntityAdmin UUID from EntityUUID
     */
    @GetMapping(ControllerPath.GET_ENTITY_ADMIN)
    public ResponseEntity<ApiResponse> GetEntityAdminFromEntityUuid(@PathVariable("uuid") String entityUuid) {
        ApiResponse apiResponse = userService.getEntityAdminFromEntityUuid(entityUuid);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {get} /api/users/{companyUUid}/entity/list Retrieve all user under own entity
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "data": [
     * {
     * "name": "Entity Admin",
     * "email": "entityadmin@getnada.com",
     * "uuid": "222",
     * "workNumber": "98989898",
     * "designation": "manager",
     * "createdAt": "2021-03-23T16:00:00Z",
     * "isActive": true,
     * "companiesRoles": null,
     * "active": true
     * },
     * {
     * "name": "Entity User",
     * "email": "entityuser@getnada.com",
     * "uuid": "333",
     * "workNumber": "89898989",
     * "designation": "accountant",
     * "createdAt": "2021-03-23T16:00:00Z",
     * "isActive": true,
     * "companiesRoles": null,
     * "active": true
     * }
     * ],
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Exception Error Message",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Get EntityAdmin UUID from EntityUUID
     */
//	@PreAuthorize("hasAuthority('SCOPE_users:read')")
    @GetMapping(ControllerPath.ENTITY_USERS)
    public ResponseEntity<ApiResponse> RetrieveEntityUsers(@PathVariable("companyUuid") String companyUuid) throws ObjectDoesNotExistException {
        ApiResponse apiResponse = userServiceAuthorization.retrieveEntitiesUsers(companyUuid);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {get} /entities/users/company/list/:uuid Retrieve all user under company through passing in company UUID
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} uuid Company unique UUID.
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "data": [
     * {
     * "name": "Entity Admin",
     * "email": "entityadmin@getnada.com",
     * "uuid": "222",
     * "workNumber": "98989898",
     * "designation": "manager",
     * "createdAt": "2021-03-23T16:00:00Z",
     * "isActive": true,
     * "companiesRoles": null,
     * "active": true
     * }
     * ],
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Cannot invoke \"org.doxa.entities.models.Companies.getId()\" because \"company\" is null",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Get users of a company through company uuid
     */
    @GetMapping(ControllerPath.COMPANY_USERS)
    public ResponseEntity<ApiResponse> RetrieveCompanyUsers(@PathVariable("uuid") String companyUuid) {
        ApiResponse apiResponse = userService.retrieveCompanyUsers(companyUuid);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {put} /entities/users/update Update user
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} name User name.
     * @apiParam {String} email User email.
     * @apiParam {String} uuid User unique UUID.
     * @apiParam {String} workNumber User work phone number.
     * @apiParam {String} designation User designation.
     * @apiParam {String} createdAt User created datetime (instance).
     * @apiParam {boolean} isActive User is active or not.
     * @apiParam {String} countryCode User country code of the phone number
     * @apiParam {String} remarks Remarks for creating the user
     * @apiParam {List} companiesRoles List of company and roles attached to user
     * @apiParamExample {json} Request-Example:
     * {
     * "name":"customer1",
     * "email":"customer1@hotmail.com",
     * "uuid":"8dfaa0b3-afc7-4657-b069-1c2ccac0c74f",
     * "workNumber":"123123",
     * "designation":"Accountant",
     * "createdAt":"2021-04-23T01:35:11.882953Z",
     * "isActive":true,
     * "countryCode": "65",
     * "remarks": "xxx",
     * "companiesRoles":[
     * {
     * "companyName":"Company2",
     * "companyUuid":"22222",
     * "main": false,
     * "role":["COMPANY_ADMIN"]
     * },
     * {
     * "companyName":"Company3",
     * "companyUuid":"333333",
     * "main": false,
     * "role":["ENTITY_USER"]
     * }
     * ]
     * }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Exception Error Message",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Get users of a company through company uuid
     */
    @PutMapping(ControllerPath.UPDATE_USER)
    public ResponseEntity<ApiResponse> UpdateUser(@Valid @RequestBody UserDetailsDto userDto) {
        ApiResponse apiResponse = userService.updateUser(userDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {put} /entities/users/deactivate/:uuid Deactivate user based on the uuid
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} uuid User unique UUID.
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Exception Error Message",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Deactivate user through user's unique UUID
     */
    //Only entity admin can deactivate user. "Manage User" user can't deactivate user
//	@PreAuthorize("hasAnyAuthority('DOXA_ADMIN','ENTITY_ADMIN')")
    @PutMapping(ControllerPath.DEACTIVATE_USER)
    public ResponseEntity<ApiResponse> DeactivateUser(@PathVariable("uuid") String uuid) {
        ApiResponse apiResponse = userService.deactivateUser(uuid);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {put} /entities/users/delete/:uuid Mark user as delete based on the uuid
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} uuid User unique UUID.
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Exception Error Message",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Mark user as delete
     */
    //Only entity admin can delete user. "Manage User" user can't delete user
//	@PreAuthorize("hasAnyAuthority('DOXA_ADMIN','ENTITY_ADMIN')")
    @PutMapping(ControllerPath.DELETE_USER)
    public ResponseEntity<ApiResponse> DeleteUser(@PathVariable("uuid") String uuid) {
        ApiResponse apiResponse = userService.markUserAsDelete(uuid);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {post} /entities/users/create Create user
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} name User name.
     * @apiParam {String} email User email.
     * @apiParam {String} workNumber User work phone number.
     * @apiParam {String} designation User designation.
     * @apiParam {List} companiesRoles List of company and roles attached to user
     * @apiParam {String} password Created user password
     * @apiParam {String} countryCode User country code of the phone number
     * @apiParam {String} remarks Remarks for creating the user
     * @apiParamExample {json} Request-Example:
     * {
     * "name":"admin2",
     * "email":"test2@hotmail.com",
     * "designation":"Pa assistant",
     * "workNumber":"123123",
     * "countryCode": "65",
     * "remarks": "xxx",
     * "companiesRoles":[
     * {
     * "companyName":"Company1",
     * "companyUuid":"11111",
     * "role":["COMPANY_ADMIN"]
     * },
     * {
     * "companyName":"Company3",
     * "companyUuid":"333333",
     * "role":["ENTITY_USER"]
     * }
     * ],
     * "password":"123"
     * }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Email has already been used",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Creating a new user
     */
    @PostMapping(ControllerPath.CREATE_USER)
    public ResponseEntity<ApiResponse> CreateEntityUser(@Valid @RequestBody UserCreateDto userDto) {
        ApiResponse apiResponse = userService.createEntityUser(userDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {put} /entities/users/activate/:uuid Activate user based on the uuid
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} uuid User unique UUID.
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Exception Error Message",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Activate user through user's unique UUID
     */
    //Only entity admin can activate user. "Manage User" user can't activate user
//	@PreAuthorize("hasAnyAuthority('DOXA_ADMIN','ENTITY_ADMIN')")
    @PutMapping(ControllerPath.ACTIVATE_USER)
    public ResponseEntity<ApiResponse> ActivateUser(@PathVariable("uuid") String uuid) {
        ApiResponse apiResponse = userService.activateUser(uuid);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }


    /**
     * @api {get} /api/users/entity/list/:uuid Retrieve all user under entity through passing in entity UUID
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} uuid entity unique UUID.
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "data": [
     * {
     * "name": "Entity Admin",
     * "email": "entityadmin@getnada.com",
     * "uuid": "222",
     * "workNumber": "98989898",
     * "designation": "manager",
     * "createdAt": "2021-03-23T16:00:00Z",
     * "isActive": true,
     * "companiesRoles": null,
     * "active": true
     * }
     * ],
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Access denied, user doesn't have permission to access resource",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Get users of a company through company uuid
     */
    @GetMapping(ControllerPath.USERS_UNDER_ENTITY)
    public ResponseEntity<ApiResponse> RetrieveEntityUsersByEntityUuid(@PathVariable("uuid") String entityUuid) {
        ApiResponse apiResponse = userService.retrieveEntityUsersByEntityUuid(entityUuid);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {put} /api/users/:companyUuid/activate/:userUuid Activate company user based on the companyUuid and userUuid
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} companyUuid Company unique UUID.
     * @apiParam {String} userUuid User unique UUID.
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Exception Error Message",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Activate company user through company's unique UUID and user's unique UUID
     */
    @PutMapping(ControllerPath.ACTIVATE_COMPANY_USER)
    public ResponseEntity<ApiResponse> ActivateCompanyUser(@PathVariable("companyUuid") String companyUuid, @PathVariable("userUuid") String userUuid) {
        ApiResponse apiResponse = userService.activateCompanyUser(companyUuid, userUuid);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * @api {put} /api/users/:companyUuid/deactivate/:userUuid Deactivate company user based on the companyUuid and userUuid
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} companyUuid Company unique UUID.
     * @apiParam {String} userUuid User unique UUID.
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Exception Error Message",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Deactivate company user through company's unique UUID and user's unique UUID
     */
    @PutMapping(ControllerPath.DEACTIVATE_COMPANY_USER)
    public ResponseEntity<ApiResponse> DeactivateCompanyUser(@PathVariable("companyUuid") String companyUuid, @PathVariable("userUuid") String userUuid) {
        ApiResponse apiResponse = userService.deactivateCompanyUser(companyUuid, userUuid);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    
    /**
     * @api {post} /api/users/fi-user Create fi user
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} name User name.
     * @apiParam {String} email User email.
     * @apiParam {String} workNumber User work phone number.
     * @apiParam {String} designation User designation.
     * @apiParam {String} password Created user password
     * @apiParam {String} countryCode User country code of the phone number
     * @apiParam {String} remarks Remarks for creating the user
     * @apiParamExample {json} Request-Example:
     * {
     * "name":"admin2",
     * "email":"test2@hotmail.com",
     * "designation":"Pa assistant",
     * "workNumber":"123123",
     * "countryCode": "65",
     * "remarks": "xxx",
     * ],
     * "password":"123"
     * }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Email has already been used",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Creating a new user
     */
//    @PostMapping(ControllerPath.CREATE_FI_USER)
//    public ResponseEntity<ApiResponse> CreateFiUser(@RequestBody FiUserDto userDto) {
//        ApiResponse apiResponse = userService.createFiUser(userDto);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }
    
    /**
     * @api {post} /api/users/update/fi-users/{uuid} Update fi-users
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiHeader {String} Authorization JWT token
     * @apiParam {String} name User name.
     * @apiParam {String} email User email.
     * @apiParam {String} workNumber User work phone number.
     * @apiParam {String} designation User designation.
     * @apiParam {String} password Created user password
     * @apiParam {String} countryCode User country code of the phone number
     * @apiParam {String} remarks Remarks for creating the user
     * @apiParamExample {json} Request-Example:
     * {
     * "name":"admin2",
     * "email":"test2@hotmail.com",
     * "designation":"Pa assistant",
     * "workNumber":"123123",
     * "countryCode": "65",
     * "remarks": "xxx",
     * ],
     * "password":"123"
     * }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Email has already been used",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription updating user
     */
//    @PutMapping(ControllerPath.UPDATE_FI_USER)
//    public ResponseEntity<ApiResponse> updateFiUser(@RequestBody FiUserDto userDto) {
//        ApiResponse apiResponse = userService.updateFiUser(userDto);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }
    
    /**
     * @api {post} /entities/users/twofa/reset Reset 2FA for other users
     * @apiGroup User Management
     * @apiVersion 0.0.1
     * @apiParam {String} uuid User unique uuid
     * @apiParamExample {json} Request-Example:
     * {
     * "uuid":"222"
     * }
     * @apiHeader {String} Authorization JWT token
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "OK",
     * "timestamp": 1619083490713,
     * "statusCode": 0
     * }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 200 OK
     * {
     * "status": "BAD_REQUEST",
     * "message": "Exception Error Message",
     * "timestamp": 1619083958882,
     * "statusCode": 0
     * }
     * @apiDescription Reset 2FA for other user
     */
//	@PreAuthorize("hasAnyAuthority('DOXA_ADMIN','ENTITY_ADMIN','COMPANY_ADMIN')")
//    @PostMapping(ControllerPath.TWO_FA_RESET)
//    public ResponseEntity<ApiResponse> ResetTwoFAOther(@RequestBody ResetFAPassword resetFAPassword) {
//        ApiResponse apiResponse = userService.resetFATwo(resetFAPassword);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }
    
//    @GetMapping(ControllerPath.GET_FILTER_LIST_USER)
//	public ResponseEntity<ApiResponse> getAllFilteredUsers(
//			@RequestParam(value = "s", required = false) String s,
//			@RequestParam(value = "q", required = false) String q,
//			@RequestParam(value = "pageNumber") Integer pageNumber,
//			@RequestParam(value = "pageSize") Integer pageSize,
//			@RequestParam(value = "fiUuid") String fiUuid,
//			@RequestParam(value = "orderBy", required = false) String orderBy) throws Exception {
//		try {
//			ApiResponse apiResponse = userService.getAllFilteredUsers(s, q, pageNumber, pageSize, fiUuid, orderBy);
//			return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//		} catch(Exception e) {
//			throw new Exception(e.getMessage());
//		}
//	}
	
	@GetMapping(ControllerPath.GET_FI_USER)
	public ResponseEntity<ApiResponse> getFiUser(@PathVariable("uuid") @NotEmpty String uuid) throws ObjectDoesNotExistException{
		ApiResponse getFIUser = userService.getFiUser(uuid);
		if (Objects.nonNull(getFIUser)) {
			return new ResponseEntity<ApiResponse>(getFIUser, HttpStatus.OK);
		}else {
			return new ResponseEntity<ApiResponse>(getFIUser, HttpStatus.BAD_REQUEST);
		}
	}

}
