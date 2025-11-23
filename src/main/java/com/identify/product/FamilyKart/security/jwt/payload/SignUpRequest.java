package com.identify.product.FamilyKart.security.jwt.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;


@Data
public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 15, message = "Username must be between 8 and 15 characters")
    private String username;

    @NotBlank
    private String password;

    @Email
    private String email;

//here we can't give notblank because roles are optional why because if not given we can assign default role
    private Set<String> roles;

}
