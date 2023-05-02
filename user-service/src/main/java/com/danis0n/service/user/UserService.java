package com.danis0n.service.user;

import com.danis0n.dto.CreateUserDto;
import com.danis0n.dto.UserDto;

public interface UserService {
    UserDto findUserByUUID(String uuid);
    String addUser(CreateUserDto dto);
}
