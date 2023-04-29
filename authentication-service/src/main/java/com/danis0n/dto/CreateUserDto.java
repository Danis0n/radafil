package com.danis0n.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserDto {
    private String username;
    private String password;
    private String email;
}
