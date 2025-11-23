package com.identify.product.FamilyKart.exceptionhandling;

public class ResourceNotFoundException extends RuntimeException{
   String resourceName;
   String field;
   Long fieldID;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ResourceNotFoundException() {
    }

    String fieldName;
    public ResourceNotFoundException(String resourceName, String field, Long fieldID) {
        super(String.format("%s not found with %s : '%s'",resourceName,field,fieldID));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldID = fieldID;
    }
    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s : '%s'",resourceName,field,fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }
}
