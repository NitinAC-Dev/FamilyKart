package com.identify.product.FamilyKart.security.jwt.controller;


import com.identify.product.FamilyKart.constanst.AppRole;
import com.identify.product.FamilyKart.roles.repository.RoleRepository;
import com.identify.product.FamilyKart.security.jwt.JWTUtils;
import com.identify.product.FamilyKart.security.jwt.payload.LoginRequest;

import com.identify.product.FamilyKart.security.jwt.payload.MessageResponse;
import com.identify.product.FamilyKart.security.jwt.payload.SignUpRequest;
import com.identify.product.FamilyKart.security.jwt.payload.UserInfoResponse;
import com.identify.product.FamilyKart.security.jwt.repository.UserRepository;
import com.identify.product.FamilyKart.security.jwt.service.UserDetailsImpl;
import com.identify.product.FamilyKart.roles.model.Role;
import com.identify.product.FamilyKart.user.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private User user;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest) {

        Authentication authentication;
        try {

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Invalid username or password");
            map.put("status", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(
                GrantedAuthority::getAuthority
        ).toList();

        UserInfoResponse logoutResponse = new UserInfoResponse(userDetails.getId(),
                jwtCookie.getValue(),
                userDetails.getUsername(),
                roles
        );
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE,jwtCookie.toString()).body(logoutResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error: Email is already in use!"));
        }

//you can do like this also using constructor or by passing setters also we can set the values i have shown both.
// this is using constructors.//Here we don't need to use @Autowired for user class at the top just create new object of User class like below
        //if we want to test comment any one and test
//        User user=new User(signUpRequest.getUsername(),
//                signUpRequest.getEmail(),
//                passwordEncoder.encode(signUpRequest.getPassword()));
/// constructor above or setter below
        //this is using setters
        // to use this we have use @Autowired for user class at the top where we have to create Bean object in websecurityyconfig class
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));



////////////////////////////////////////////////////////////////
        Set<String> strRoles = signUpRequest.getRoles();

        Set<Role> roles=new HashSet<>();

        if (strRoles ==null ||strRoles.isEmpty())
        {
            Role userRole =roleRepository.findByRoleName(AppRole.USER).orElseThrow(() ->
                    new RuntimeException("Error: Role is not found."));
     roles.add(userRole);
             }
        else
        {
            strRoles.forEach(role ->
                    {
                        switch(role)
                        {
                            case "admin":
                               Role adminRole= roleRepository.findByRoleName(AppRole.ADMIN).orElseThrow(() ->
                                        new RuntimeException("Error: Role is not found."));
                                roles.add(adminRole);
                                break;
                            case "seller":
                                Role sellerRole= roleRepository.findByRoleName(AppRole.SELLER).orElseThrow(() ->
                                        new RuntimeException("Error: Role is not found."));
                                roles.add(sellerRole);
                                break;
                            default:
                                Role userRole= roleRepository.findByRoleName(AppRole.USER).orElseThrow(() ->
                                        new RuntimeException("Error: Role is not found."));
                                roles.add(userRole);
                        }
                    }

            );
        }
        user.setSetOfRoles(roles);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/username")
    public ResponseEntity<?> getUserName(Authentication authentication)
    {
        String getUsername = authentication.getName();

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(getUsername));

    }

    @GetMapping("/user")
    public ResponseEntity<UserInfoResponse> getUser(Authentication authentication)
    {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        UserInfoResponse userInfoResponse=new UserInfoResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                roles
        );
        return ResponseEntity.status(HttpStatus.OK).body(userInfoResponse);
    }

    @PostMapping("/signOut")
    public ResponseEntity<?> signOut()
    {

        ResponseCookie cleanJwtCookie = jwtUtils.getCleanJwtCookie();
       return ResponseEntity.status(HttpStatus.OK)
               .header(HttpHeaders.SET_COOKIE,cleanJwtCookie.toString())
                .body(new MessageResponse("you have been signed out!"));


    }
}