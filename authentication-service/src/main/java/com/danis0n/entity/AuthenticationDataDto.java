package com.danis0n.entity;

import lombok.Getter;

@Getter
public class AuthenticationDataDto {
    private String username;
    private String hashedPassword;
}
