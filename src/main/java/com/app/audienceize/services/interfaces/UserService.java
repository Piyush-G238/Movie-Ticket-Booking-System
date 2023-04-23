package com.app.audienceize.services.interfaces;

import com.app.audienceize.dtos.requests.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface UserService extends UserDetailsService {
    public String addUser(UserRequest request);
    public String login(Map<String, String> credentials);
}
