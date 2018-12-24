package com.amadeus.ist.springrest.authentication.impl;

import com.amadeus.ist.springrest.authentication.Authentication;
import com.amadeus.ist.springrest.authentication.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public boolean authenticate(Authentication authentication) {
        //TODO implement a proper authentication logic
        return authentication.getEmail().equals("admin@admin.com") &&
                authentication.getPassword().equals("1234");
    }
}
