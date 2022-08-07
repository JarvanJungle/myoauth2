package com.doxa.oauth2.controllers.authority;

import com.doxa.oauth2.DTO.authority.GrantUserAuthority;
import com.doxa.oauth2.authorization.AuthorityAuthorization;
import com.doxa.oauth2.common.ControllerPath;
import com.doxa.oauth2.exceptions.AccessDeniedException;
import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
import com.doxa.oauth2.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ControllerPath.AUTHORITY)
public class ManageAuthorityController {

    @Autowired
    private AuthorityAuthorization authorityAuthorization;

    /**
     * @api  {get} /auth/api/:companyUuid/authorities Get company's authorities
     * @apiDescription @author: Noi <br />
     * @apiGroup Authority
     * @apiVersion 0.0.1
     *
     *
     * @apiHeader {String} Authorization JWT token
     *
     * @apiSuccess {String} featureName Feature Name
     * @apiSuccess {String} moduleCode Module Code
     * @apiSuccess {String} featureCode Feature Code
     * @apiSuccess {String} companyUuid Company UUID

     * @apiSuccessExample {json} Success-Response:
     *    {
     *       "moduleCode": "P2P",
     *       "featureCode": "PR",
     *       "featureName": "Purchase Requisition",
     *       "startDate": null,
     *       "endDate": null,
     *       "companyUuid": "129618fe-4bfb-4c79-b4db-44787bd3cb1f"
     *     },
     */
//    @GetMapping(ControllerPath.COMPANY_AUTHORITY)
//    public ResponseEntity<ApiResponse> getCompanySubscription(@PathVariable String companyUuid) throws AccessDeniedException {
//        ApiResponse apiResponse =  authorityAuthorization.getCompanySubscription(companyUuid);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    /**
     * @api  {get} /auth/api/:companyUuid/modules Get company's modules subscription
     * @apiDescription @author: Noi <br />
     * @apiGroup Authority
     * @apiVersion 0.0.1
     *
     *
     * @apiHeader {String} Authorization JWT token
     *
     * @apiSuccess {String} moduleCode Module Code
     * @apiSuccess {String} moduleName Module Name
     * @apiSuccess {String} uuid Module UUID

     * @apiSuccessExample {json} Success-Response:
     *    {
     *       "moduleCode": "P2P",
     *       "moduleName": "Procurement To Pay",
     *       "uuid": "129618fe-4bfb-4c79-b4db-44787bd3cb1f"
     *     },
     */
//    @GetMapping(ControllerPath.COMPANY_MODULES)
//    public ResponseEntity<ApiResponse> getCompanyModules(@PathVariable String companyUuid) throws AccessDeniedException {
//        ApiResponse apiResponse =  authorityAuthorization.getCompanyModules(companyUuid);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    /**
     * @api  {post} /auth/api/:companyUuid/:userUuid/authorities Grant permission to user
     * @apiDescription @author: Noi <br />
     * Role Allowed: <b>ADMIN, user who has manage user permission</b>
     * @apiGroup Authority
     * @apiVersion 0.0.1
     *
     *
     * @apiHeader {String} Authorization JWT token
     *
     * @apiParam {Object[]} features Feature Name
     * @apiParam {String} features.featureCode Feature Code
     * @apiParam {String[]} features.actions List of actions allowed
     * @apiParam {String} features.actions.action action allow
     * @apiParamExample {json}
     * [
     *        {
     * 		"featureCode": "PO",
     * 		"actions": [
     * 			"WRITE"
     * 		]
     *    }
     * ]
     * @apiSuccessExample {json} Success-Response:
     *  {
     *   "status": "OK",
     *   "message": "Create successful",
     *   "timestamp": 1624265743080,
     *   "statusCode": 0
     *  }
     */

//    @PostMapping(ControllerPath.USER_AUTHORITY)
//    public ResponseEntity<ApiResponse> grantUserAuthorities(@PathVariable String companyUuid, @PathVariable String userUuid, @RequestBody @Valid List<GrantUserAuthority> userAuthorities) throws AccessDeniedException {
//        ApiResponse apiResponse =  authorityAuthorization.grantAuthorityToUser(companyUuid, userUuid, userAuthorities);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    /**
     * @api  {get} /auth/api/:companyUuid/:userUuid/authorities Get User's authorities
     * @apiDescription @author: Noi <br />
     * Role Allowed: <b>ADMIN, user who has manage user permission</b>
     * @apiGroup Authority
     * @apiVersion 0.0.1
     *
     * @apiHeader {String} Authorization JWT token
     *
     * @apiParam {String} companyUuid Company UUID
     * @apiParam {String} userUuid User UUID
     *
     * @apiSampleRequest <host>/auth/api/129618fe-4bfb-4c79-b4db-44787bd3cb1f/2e992d01-0110-4fc4-ba2a-69f1c7d3370e/authorities
     *
     * @apiSuccess {String} featureCode
     * @apiSuccess {Object[]} actions Actions allowed
     * @apiSuccess {boolean} read Allowed read if TRUE
     * @apiSuccess {boolean} write Allowed write if TRUE
     * @apiSuccess {boolean} approve Allowed approve if TRUE
     * @apiSuccessExample {json} Success-Response:
     *  {
     *       "featureCode": "PO",
     *       "actions": {
     *         "read": false,
     *         "write": true,
     *         "approve": false
     *       }
     *  }
     */

    @GetMapping(ControllerPath.USER_AUTHORITY)
    public ResponseEntity<ApiResponse> getUserAuthorities(@PathVariable String companyUuid, @PathVariable String userUuid) throws AccessDeniedException, ObjectDoesNotExistException {
        ApiResponse apiResponse =  authorityAuthorization.getUserAuthority(companyUuid, userUuid);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping(ControllerPath.CHECK_USER_AUTHORITY)
    public ResponseEntity<ApiResponse> getUserAuthority(@PathVariable String companyId, @PathVariable String userId, @PathVariable String featureCode) throws AccessDeniedException {
        ApiResponse apiResponse =  authorityAuthorization.checkUserPermission(companyId, userId, featureCode);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
