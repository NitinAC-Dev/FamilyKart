package com.identify.product.FamilyKart.exceptionhandling;

public class ApiException extends RuntimeException {

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }


}
