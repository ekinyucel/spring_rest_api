package com.amadeus.ist.springrest.config.model;

public class Error {
    private String errorMessage;
    private int errorStatus;

    public Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
