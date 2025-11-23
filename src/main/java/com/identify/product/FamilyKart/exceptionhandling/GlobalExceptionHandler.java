package com.identify.product.FamilyKart.exceptionhandling;


import com.identify.product.FamilyKart.catagory.payload.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {



    @Autowired
    private APIResponse apiResponse;



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<String, String>();
        e.getBindingResult().getAllErrors().forEach(error -> {

            String fieldname = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMap.put(fieldname, message);

        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<String> handlePropertyReferenceException(PropertyReferenceException e) {
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myRecourseNotFoundException(ResourceNotFoundException e) {

        String message = e.getMessage();
        apiResponse.setMessage(message);
        apiResponse.setSuccess(false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);




    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<APIResponse> apiExceptionHandler(ApiException e) {
        String message = e.getMessage();
        apiResponse.setMessage(message);
        apiResponse.setSuccess(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }


}
