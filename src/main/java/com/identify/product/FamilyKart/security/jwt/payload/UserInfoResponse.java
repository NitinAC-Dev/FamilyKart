package com.identify.product.FamilyKart.security.jwt.payload;

import java.util.List;

public class UserInfoResponse {

    public UserInfoResponse(long id, String username, List<String> roles) {

        this.id=id;
        this.username = username;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    private String accesToken;

    private String username;
    private List<String> roles;

    public UserInfoResponse(Long id,String accesToken, String username, List<String> roles) {
       this.id=id;
        this.accesToken = accesToken;
        this.username = username;
        this.roles = roles;
    }





    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


}
