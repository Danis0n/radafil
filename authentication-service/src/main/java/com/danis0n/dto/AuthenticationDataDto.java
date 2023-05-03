package com.danis0n.dto;

import lombok.Getter;

@Getter
public class AuthenticationDataDto {
    private String username;
    private String hashedPassword;
}
