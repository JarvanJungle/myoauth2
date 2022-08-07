package com.doxa.oauth2.common;

public class ControllerPath {
    public static final String USERS = "/api/users";
    public static final String LOGIN = "/signin";
    public static final String SETUP_PASSWORD = "/setup-password";
    public static final String SETUP_2FA = "/twofa/signin";
    public static final String TWOFA_VERIFICATION = "/twofa/verification";
    public static final String SIGNIN_2FA = "/twofa/signin";
    public static final String TWOFA_SIGNUP = "/twofa/signup";

    //    Controller path for entities
    public static final String ENTITIES = "/api/org";
    public static final String CREATE_ENTITY = "/create";
    public static final String LIST_ENTITIES = "/list";
    public static final String UPDATE_ENTITY = "/update";
    public static final String ENTITY_TYPE = "/entity-type-list";
    public static final String INDUSTRY_TYPE = "/industry-type-list";
    public static final String GET_ENTITY = "/get-entity-details/{uuid}";
    public static final String TWOFA_RESET = "/twofa/reset";
    public static final String TWOFA_RESET_OWN = "/twofa/own/reset";
    public static final String PASSWORD_RESET = "/password/reset";
    public static final String PASSWORD_RESET_OWN = "/password/own/reset";
    public static final String DETAILS_OWN = "/me";
    public static final String UPDATE_AVATAR = "/update-avatar";
    public static final String DELETE_ENTITY = "/delete/{uuid}";
    public static final String REACTIVATE_ENTITY = "/reactivate/{uuid}";

    public static final String USER_DETAILS = "/details/{uuid}";
    public static final String ENTITY_USERS = "/{companyUuid}/entity/list";
    public static final String COMPANY_USERS = "/company/list/{uuid}";
    public static final String USERS_UNDER_ENTITY = "/entity/list/{uuid}";
    public static final String UPDATE_USER = "/update";
    public static final String DEACTIVATE_USER = "/deactivate/{uuid}";
    public static final String ACTIVATE_USER = "/activate/{uuid}";
    public static final String DELETE_USER = "/delete/{uuid}";
    public static final String CREATE_USER = "/create";
    public static final String GET_ENTITY_ADMIN = "/entity/{uuid}";
    public static final String ACTIVATE_COMPANY_USER = "/{companyUuid}/activate/{userUuid}";
    public static final String DEACTIVATE_COMPANY_USER = "/{companyUuid}/deactivate/{userUuid}";
//    public static final String PASSWORD_RESET_ENTITYADMIN = "/password/entity/reset/{uuid}";

    //    Controller path for companies
    public static final String COMPANY = "/api/company";
    public static final String CREATE_COMPANY = "/create";
    public static final String LIST_ENTITY_COMPANIES = "/list";
    public static final String LIST_COMPANIES = "/{entityUuid}/list"; // List all companies under entity
    public static final String UPDATE_COMPANY = "/update";
    public static final String GET_COMPANY = "/get-company-details/{uuid}";
    public static final String DELETE_COMPANY = "/delete/{uuid}";
    public static final String REACTIVATE_COMPANY = "/reactivate/{uuid}";
    public static final String LIST_ALL_COMPANIES = "/all/list";

    //Controller path for sub-admin
    public static final String MANAGE_SUBADMIN = "/api/subadmin";
    public static final String LIST_SUBADMIN_PERMISSIONS = "/list";
    public static final String GRANT_SUBADMIN_PERMISSIONS = "/update";
    public static final String USER_SUBADMIN_PERMISSIONS = "/get-details";

    public static final String PRIVATE = "/api/private";
    public static final String PRIVATE_COMPANY_DETAILS = "/company-details";
    public static final String PRIVATE_GET_USERS_UUID_BY_AUTHORITY = "/user-uuid-by-authority";
    public static final String PRIVATE_USER_DETAILS = "/user-details/{uuid}";
    public static final String PRIVATE_USER_DETAILS_BY_EMAIL = "/user-details/by-email";
    public static final String PRIVATE_USER_RBAC_ROLES = "/{companyUuid}/user/rbac-role";
    public static final String PRIVATE_USER_EMAIL = "/user-emails";
    public static final String PRIVATE_ENTITY_ADMIN_DETAILS = "/entity-admin-details";
    public static final String PRIVATE_COMPANY_AND_ENTITY_ADMIN_DETAILS = "/company-admin-details";

    public static final String AUTHORITY = "/api/{companyUuid}";
    public static final String COMPANY_AUTHORITY = "/authorities";
    public static final String USER_AUTHORITY = "/{userUuid}/authorities";
    public static final String CHECK_USER_AUTHORITY = "/{userUuid}/authorities/{featureCode}";

    public static final String DOXA_ADMIN_CONTROLLER = "/api/dox";
    public static final String DOXA_ADMIN_MODULES = "/module";
    public static final String ASSIGN_MODULES = "/module/{companyUuid}";
    public static final String COMPANY_MODULES = "/modules";
	public static final String PRIVATE_USR_AUTHORITY = "/company/{companyUuid}/user/{userUuid}/authority";
	public static final String RBAC = "/api/{companyUuid}/rbac";
	public static final String RBAC_ROLE = "/role";
	public static final String RBAC_ASSIGN_TO_USER = "/user";
	public static final String CREATE_FI_USER = "/fi-users";
	public static final String UPDATE_FI_USER = "/update/fi-users";
	public static final String PRIVATE_CREATE_FI_USER = "/fi/user";
	
	
	public static final String FINANCIAL_INSTITUTION = "/api/financial-institution";
	public static final String CREATE = "/create";
	public static final String FI_AUTHORITY= "/api/{fiUuid}";
	public static final String FINANCE_AUTHORITY = "/authority";
	public static final String FI_USER_AUTHORITY = "/{userUuid}/authority";
	public static final String TWO_FA_RESET = "/two-fa-rest";
	public static final String UPDATE = "/update";
	public static final String GET_FINANCIALINSTITUTION = "/get/financialinstitution";
	public static final String GET_FILTER_LIST_USER = "/filter-fi-user";
	public static final String GET_FI_USER = "/get/fi-user/{uuid}";
    public static final String GET_FINANCIALINSTITUTION_BYID = "/financialinstitution/{id}";
    public static final String GET_FINANCIALINSTITUTION_BYLOGGEDINUSER = "/get/financialinstitution/{loggedInUserUuid}";
}
