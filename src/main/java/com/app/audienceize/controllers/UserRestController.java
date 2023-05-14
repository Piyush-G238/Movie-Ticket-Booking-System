package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.UserRequest;
import com.app.audienceize.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "User Rest Apis")
public class UserRestController {
    @Autowired
    private UserService service;

    @Operation(
            method = "POST",
            description = "Endpoint for registering users in the application",
            summary = "Endpoint for registering users in the application",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED",
                            content = {
                                    @Content(
                                            mediaType = "TEXT_PLAIN",
                                            examples = {
                                                    @ExampleObject(
                                                            value = "Congratulations, your account has been created. Here is your user id: 09077e62-bdf8-4008-98bf-d126e877c989"
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserRequest request) {
        String response = service.addUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            method = "POST",
            description = "Endpoint for user login in the application.",
            summary = "Endpoint for user login in the application.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED",
                            content = {
                                    @Content(
                                            mediaType = "TEXT_PLAIN",
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
                                                                    .eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ
                                                                    .SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String token = service.login(credentials);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}
