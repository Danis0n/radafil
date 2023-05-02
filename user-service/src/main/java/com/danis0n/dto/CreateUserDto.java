package com.danis0n.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserDto {

    @NotNull(message = "username is null")
    @NotEmpty(message = "username is empty")
    private String username;

    @NotNull(message = "password is null")
    @NotEmpty(message = "password is empty")
    private String password;

    @Email(message = "invalid email")
    private String email;
}
