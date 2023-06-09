package com.app.audienceize.services.impl;

import com.app.audienceize.dtos.requests.UserRequest;
import com.app.audienceize.entities.User;
import com.app.audienceize.enums.Role;
import com.app.audienceize.helper.JwtTokenGenerator;
import com.app.audienceize.services.interfaces.UserService;
import com.app.audienceize.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenGenerator generator;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String addUser(UserRequest request) {
        User user = toEntity(request);
        userRepository.save(user);
        return "Congratulations, your account has been created. Here is your user id: "+ user.getUserId();
    }

    @Override
    public String login(Map<String, String> credentials) {
        manager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        credentials.get("username"),
                        credentials.get("password"),
                        null
                ));
        User user = (User) loadUserByUsername(credentials.get("username"));
        return generator.generateToken(user);
    }

    private User toEntity(UserRequest request) {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .emailId(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .mobile(request.getPhoneNo())
                .role(Role.USER)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailId(username).orElseThrow(NoSuchElementException::new);
    }
}
