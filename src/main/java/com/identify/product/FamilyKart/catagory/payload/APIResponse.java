package com.identify.product.FamilyKart.catagory.payload;

import org.springframework.stereotype.Component;


public class APIResponse {
    private String message;
    private boolean success;



    public APIResponse() {
    }
    public APIResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
