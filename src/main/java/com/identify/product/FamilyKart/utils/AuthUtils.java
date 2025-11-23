package com.identify.product.FamilyKart.utils;

import com.identify.product.FamilyKart.security.jwt.repository.UserRepository;
import com.identify.product.FamilyKart.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component// spring ioc will maintain the object of this class
public class AuthUtils {

    @Autowired
    UserRepository userRepository;

    public String getLoggedInUserEmail() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + name));
return user.getEmail();
    }

    public User getLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));
    }

    public Long getLoggedInUserCartID() {
        User loggedInUser = getLoggedInUser();

        return loggedInUser.getCart().getCartID();
    }
}
