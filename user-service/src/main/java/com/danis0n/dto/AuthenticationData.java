package com.danis0n.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationData {
    private String username;
    private String hashedPassword;
}
