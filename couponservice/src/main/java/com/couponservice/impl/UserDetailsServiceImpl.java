package com.couponservice.impl;

import com.couponservice.model.User;
import com.couponservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("Username not found!!!");
        }
        // Ye krne se hme spring security ko UserDetails ka object bna kr return kiya.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),user.getRoles());
    }
}
