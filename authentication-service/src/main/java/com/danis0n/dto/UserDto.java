package com.danis0n.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private String uuid;
    private String username;
    private String email;
}
