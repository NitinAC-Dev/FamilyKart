package com.identify.product.FamilyKart.security.jwt.service;

import com.identify.product.FamilyKart.security.jwt.repository.UserRepository;
import com.identify.product.FamilyKart.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    UserRepository userRepository;
    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional //which mean the data base activity will happen smoothly if anything happens it will rollback the transaction
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user=   userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User Not Found with username: "+username));


        return UserDetailsImpl.build(user);
    }
}
