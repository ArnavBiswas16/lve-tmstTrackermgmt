package com.alt.lve_tmst_mgmt.Exceptions;

public class BusinessValidationException extends  RuntimeException{

    public BusinessValidationException(String message){
        super(message);
    }
}
