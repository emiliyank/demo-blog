package com.blog.demo.security;

import com.blog.demo.user.User;
import com.blog.demo.user.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        User domainUser = userDAO.findUserByUsername(username);

        CustomUserDetail customUserDetail=new CustomUserDetail();
        customUserDetail.setUser(domainUser);

        return customUserDetail;
    }
}
