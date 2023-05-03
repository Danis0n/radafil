package com.danis0n.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationDataDto {
    private String username;
    private String hashedPassword;
}
