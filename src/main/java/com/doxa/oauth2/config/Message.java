package com.doxa.oauth2.config;

public enum Message {
    EMAIL_RESET_PASSWORD_SUBJECT("Reset Password"),
    EMAIL_RESET_PASSWORD("We have received a request to reset your password for your account."),
    EMAIL_RESET_PASSWORD_TITLE("Password Reset Successful"),
    EMAIL_RESET_2FA_SUBJECT("Reset Two Factor Authentication"),
    EMAIL_RESET_2FA_TITLE("Two FA Reset Successful"),
    EMAIL_RESET_2FA_MESSAGES("Two FA for your <b>Doxa Account</b> has been successfully turnoff and reset. Please login to the account to activate the twoFA again for more protection. Thank you very much."),
    EMAIL_ACCOUNT_SETUP_SUBJECT("Doxa Account Setup Completed Successfully"),
    EMAIL_CREATE_VENDOR_SUBJECT("Connection Request"),
    EMAIL_CREATE_VENDOR_TITLE("Connection Request on DOXA Connex 2.0"),

    TRADE_RECORDS_ACTIVATED("Trade Record/s Activated"),

    USER_NOT_FOUND("User not found"),
    UNAUTHORIZED("Unauthorized"),
    LOGIN_FAIL("Email or Password is incorrect"),
    SERVER_ERROR("Unexpected error"),
    COMPANY_EXISTS("Company Already Exists"),
    COMPANY_CREATED("Company created for: "),
    COMPANY_UPDATED("Company updated for: "),
    COMPANY_CREATE_FAIL("Unable to create Company"),
    COMPANY_NOT_FOUND("Company not found"),
    COMPANY_DEACTIVATED("Company deactivated: "),
    COMPANY_REACTIVATED("Company reactivated: "),
    ENTITY_EXISTS("Entity Already Exists"),
    ENTITY_CREATED("Entity created: "),
    ENTITY_NOT_FOUND("Cannot retrieve entity"),
    CURRENCY_COMPANY_NOT_FOUND("Currency list for company not found"),
    CURRENCY_NOT_FOUND("Currency not found"),
    ADD_CURRENCY_FAIL("Unable to add currency"),
    UPDATE_CURRENCY_FAIL("Unable to update currency"),
    CURRENCY_ALREADY_EXISTS("Currency already exists"),
    CURRENCY_ADDED("Currency added: "),
    CURRENCY_UPDATED("Currency updated: "),
    WRONG_PASSWORD("Wrong Password"),
    NON_UNIQUE_EMAIL("Email has already been used"),
    WRONG_2FA("Wrong 2FA pin"),
    NOT_FOUND("Data not found"),
    NOT_DATA("No Data Received"),
    UPDATE_SUCCESSFUL("Update is successful"),
    CREATE_SUCCESSFUL("Create successful"),
    CONNECTION_ACCEPTED("Connection accepted"),
    DELETE_SUCCESSFUL("Delete successful"),
    REJECT_SUCCESSFUL("Connection rejected"),
    CONNECTION_CANCELLED("Connection cancelled"),
    NO_PERMISSION("Access denied, no permission"),
    NON_UNIQUE_UEN("Duplicated UEN recorded"),
    NON_UNIQUE_COMPANYCODE("Non unique company code"),
    NON_UNIQUE_ADDRESS_LABEL("Address label is not unique"),
    CURRENCY_UPDATED_FOR_COMPANY("Currencies are updated for company: "),
    DUPLICATE_CURRENCY_CODE("Duplicate currency codes found"),
    NO_UNIQUE_DEFAULT_CURRENCY("No unique default currency is set"),
    CANNOT_DEACTIVATE_DEFAULT_CURRENCY("Cannot deactivate default currency"),
    TAX_CODE_EXISTS("Duplicate Tax Code"),
    COMPANY_DOES_NOT_EXIST("Company does not exist"),
    TAX_RECORD_DOES_NOT_EXIST("Tax record does not exist"),
    ACCESS_DENIED("You do not have permission"),
    NON_UNIQUE_APPROVAL_CODE("Approval code is not unique"),
    ACTIVATE_APPROVAL_MATRIX("Approval matrix activated"),
    DEACTIVATE_APPROVAL_MATRIX("Approval matrix deactivated"),
    UOM_CODE_EXISTS("Duplicate UOM Code"),
    UOM_CODE_DOES_NOT_EXISTS("UOM Code does not exist"),
    GENERAL_LEDGER_EXISTS("General Ledger Account already exist"),
    GENERAL_LEDGER_DOES_NOT_EXISTS("General Ledger Account does not exist"),
    MAX_UPLOAD_SIZE("Max upload size"),
    INVALID_ADDRESS_INACTIVE("Address cannot be default and inactive at the same time"),
    CATALOGUE_COMPANY_NOT_FOUND("Catalogue list for company not found"),
    CATALOGUE_NOT_FOUND("Catalogue not found"),
    ADD_CATALOGUE_FAIL("Unable to add catalogue"),
    UPDATE_CATALOGUE_FAIL("Unable to update catalogue"),
    CATALOGUE_ALREADY_EXISTS("Catalogue already exists"),
    CATALOGUE_ADDED("Catalogue added: "),
    CATALOGUE_UPDATED("Catalogue updated: "),
    CATALOGUE_UPDATED_FOR_COMPANY("Catalogues are updated for company: "),
    ADD_PROJECT_FAIL("Unable to create project"),
    PROJECT_ALREADY_EXISTS("Project already exists"),
    PROJECT_NOT_FOUND("Project not found"),
    UPDATE_PROJECT_FAIL("Unable to update project"),
    PROJECT_UPDATED("Project updated for: "),
    PROJECT_CREATED("Project created for: "),
    TRADE_CODE_DOES_NOT_EXIST("Trade Code does not exist"),
    TRADE_CODE_EXISTS("Duplicate Trade Code"),
    DUPLICATE_CATALOGUE_CODE("Duplicate catalogue codes found"),
    NON_UNIQUE_GROUPNAME("Group name is not unique"),
    DEACTIVATE_SUCCESSFUL("Deactivation is successful"),
    ACTIVATE_SUCCESSFUL("Activation is successful"),
    GROUP_NOT_FOUND("Group is not found"),
    DUPLICATED_SEQUENCE("Sequence is duplicated"),
    DUPLICATED_USERS("Duplicated users found in multiple group. One user can only be under one approval group"),
    FORECAST_TRADE_NOT_FOUND("Trade for project forecast not found"),
    FORECAST_ITEM_NOT_FOUND("Item for project forecast trade not found"),
    DUPLICATE_FORECAST_TRADE_ITEM("Duplicate item for trade forecast exists"),
    PROJECT_FORECAST_COMPANY_NOT_FOUND("Project forecast for company not found"),
    DUPLICATE_FORECAST_TRADE("Duplicate trade forecast exists"),
    UNEXPECTED_ERROR("Unexpected error"),
    PROJECT_FORECAST_UPDATED("Project has been updated for project: ");

    private final String constMessages;

    Message(String constMessages) {
        this.constMessages = constMessages;
    }

    public static String emailResetPasswordMessage(String password, String emailHost) {
        return "Password for your Doxa Account has been successfully reset. Your new password is: " + password + "<br><br><a style=\"background: #aec57d;padding: 5px 15px 5px 15px;margin-top:15px; text-align: center;border-radius: 5px;border: 1px solid #aec57d;text-decoration: none;color: #FFFFFF\" href=\"" + emailHost + "\">Login Now</a>";
    }

    public static String emailAccountSetupMessages(String password, String emailHost) {
        return "Please login to Doxa with your registered email. The password for your account is: " + password + "<br><br><a style=\"background: #aec57d;padding: 5px 15px 5px 15px;margin-top:15px; text-align: center;border-radius: 5px;border: 1px solid #aec57d;text-decoration: none;color: #FFFFFF\" href=\"" + emailHost + "\">Login Now</a>";
    }

    public static String emailCreateVendorMessages(String companyName) {
        return companyName + " would like to connect with you at DOXA Connex 2.0 platform. Please click <a href='www.google.com'>here</a> to access" +
                " the DOXA platform. Thank you very much.";
    }

    public String getValue() {
        return this.constMessages;
    }
}
