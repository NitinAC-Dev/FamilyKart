package com.identify.product.FamilyKart.security.jwt.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.identify.product.FamilyKart.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
//note if this class is not working or throwing error please change the line where i have comented the code
//for password i have given transient keyword to avoid serialization issue instead of using @JsonIgnore
//I gave List<GrantedAuthority> instead of Collection<? extends GrantedAuthority> to avoid serialization issue
//we can change and check if it works

@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private long id;
    private String username;



    private String email;
    @JsonIgnore
    private /*transient*/ String password;
    private List<GrantedAuthority> authorities;
    //private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(long id, String username, String email, String password, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user)
    {

        List<GrantedAuthority> authorities = user.getSetOfRoles()
                .stream()
                .map(eachRole ->
                        new SimpleGrantedAuthority(
                                eachRole.getRoleName()
                                        .name()))
                .collect(Collectors.toList());
      return  new UserDetailsImpl(
                user.getUserID(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),authorities
        );
    }



    /**
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * @return
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @return
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
