package com.edusol.retailbanking.application.responce;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing Required Field. Please check documentation for required field "),
    RECORD_ALREADY_EXIST("This Record is Already Exist ! "),
    INTERNAL_SERVER_ERROR("Internal Server Errror ! "),
    NO_RECORD_FOUND("No Record Found ! "),
    AUTHENTICATION_FAILED("Authentication failed !"),
    COULD_NOT_UPDATE_RECORD("Could Not Update Record ! "),
    COULD_NOT_DELETE_RECORD("Could Not Delete Record ! "),
    EMAIL_ADDRESS_NOT_VERIFIED("Email Address is Not Verified ! ");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
