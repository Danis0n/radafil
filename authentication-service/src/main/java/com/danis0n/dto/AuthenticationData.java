package com.danis0n.dto;

import lombok.Getter;

@Getter
public class AuthenticationData {
    private String username;
    private String hashedPassword;
}
