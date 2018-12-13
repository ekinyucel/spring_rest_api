package com.amadeus.ist.springrest.authentication;

import com.amadeus.ist.springrest.config.interfaces.Service;

public interface AuthenticationService extends Service {
    boolean authenticate(Authentication authentication);
}
