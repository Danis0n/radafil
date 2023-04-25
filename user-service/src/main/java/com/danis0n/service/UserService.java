package com.danis0n.service;

import com.danis0n.dto.CreateUserDto;
import com.danis0n.dto.UserDto;

public interface UserService {
    UserDto findUserByUUID(String uuid);
    UserDto create(CreateUserDto dto);
}
