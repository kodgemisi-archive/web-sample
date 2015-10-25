package com.kodgemisi.web.sample.service;

import com.kodgemisi.web.sample.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    public UserDetailsServiceImpl() {
        System.out.println("UserDetailsServiceImpl.UserDetailsServiceImpl");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails user = this.userDao.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("No such user");
        }

        return user;
    }

}