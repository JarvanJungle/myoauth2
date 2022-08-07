//package com.doxa.oauth2.controllers.authority;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.doxa.oauth2.DTO.authority.GrantUserAuthority;
//import com.doxa.oauth2.authorization.AuthorityAuthorization;
//import com.doxa.oauth2.common.ControllerPath;
//import com.doxa.oauth2.exceptions.AccessDeniedException;
//import com.doxa.oauth2.responses.ApiResponse;
//
//@RestController
//@RequestMapping(ControllerPath.FI_AUTHORITY)
//public class ManageFinancialInstitutionAuthority {
//	@Autowired
//    private AuthorityAuthorization authorityAuthorization;
//
//	/**
//     * @api  {get} /auth/api/:fiUuid/financial-institution/authorities Get financial institution authorities
//     * @apiDescription @author: Lalitha <br />
//     * @apiGroup Authority
//     * @apiVersion 0.0.1
//     *
//     *
//     * @apiHeader {String} Authorization JWT token
//     *
//     * @apiSuccess {String} featureName Feature Name
//     * @apiSuccess {String} moduleCode Module Code
//     * @apiSuccess {String} featureCode Feature Code
//     * @apiSuccess {String} companyUuid Company UUID
//
//     * @apiSuccessExample {json} Success-Response:
//     *    {
//     *       "moduleCode": "P2P",
//     *       "featureCode": "PR",
//     *       "featureName": "Purchase Requisition",
//     *       "startDate": null,
//     *       "endDate": null,
//     *       "fiUuid": "129618fe-4bfb-4c79-b4db-44787bd3cb1f"
//     *     },
//     */
//    @GetMapping(ControllerPath.FINANCE_AUTHORITY)
//    public ResponseEntity<ApiResponse> getFiSubscription(@PathVariable String fiUuid) throws AccessDeniedException {
//        ApiResponse apiResponse =  authorityAuthorization.getFiSubscription(fiUuid);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }
//
//    /**
//     * @api  {post} /auth/api/:fiUuid/:userUuid/authorities Grant permission to user
//     * @apiDescription @author: Lalitha <br />
//     * Role Allowed: <b>ADMIN, user who has manage user permission</b>
//     * @apiGroup Authority
//     * @apiVersion 0.0.1
//     *
//     *
//     * @apiHeader {String} Authorization JWT token
//     *
//     * @apiParam {Object[]} features Feature Name
//     * @apiParam {String} features.featureCode Feature Code
//     * @apiParam {String[]} features.actions List of actions allowed
//     * @apiParam {String} features.actions.action action allow
//     * @apiParamExample {json}
//     * [
//     *        {
//     * 		"featureCode": "PO",
//     * 		"actions": [
//     * 			"WRITE"
//     * 		]
//     *    }
//     * ]
//     * @apiSuccessExample {json} Success-Response:
//     *  {
//     *   "status": "OK",
//     *   "message": "Create successful",
//     *   "timestamp": 1624265743080,
//     *   "statusCode": 0
//     *  }
//     */
//
//    @PostMapping(ControllerPath.FI_USER_AUTHORITY)
//    public ResponseEntity<ApiResponse> grantUserAuthority(@PathVariable String fiUuid, @PathVariable String userUuid, @RequestBody List<GrantUserAuthority> userAuthorities) throws AccessDeniedException {
//        ApiResponse apiResponse =  authorityAuthorization.grantUserAuthority(fiUuid, userUuid, userAuthorities);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }
//
//}
