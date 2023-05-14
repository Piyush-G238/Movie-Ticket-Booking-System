package com.app.audienceize.dtos.requests;

import com.app.audienceize.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    @NotEmpty(message = "Name should be there in order to create account.")
    private String name;

    @Email(message = "Email should includes @ symbol")
    @NotEmpty(message = "Email should be there in order to create account.")
    private String email;

    @NotEmpty(message = "Password should be there in order to create account")
    private String password;

    @Pattern(regexp = "^\\d{10}$", message = "PhoneNo. should be of 10 digits only")
    @NotEmpty(message = "Phone number should be there in order to create account")
    private String phoneNo;
}
