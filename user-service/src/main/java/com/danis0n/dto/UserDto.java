package com.danis0n.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserDto {

    private String uuid;
    private String username;
    private List<String> roles;
}
