package com.app.audienceize.services.interfaces;

import com.app.audienceize.dtos.requests.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public String addUser(UserRequest request);
}
