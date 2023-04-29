package com.danis0n.mapper;

import com.danis0n.dto.CreateUserDto;
import com.danis0n.dto.UserDto;
import com.danis0n.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapper implements Function<User, UserDto> {

    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getUuid().toString(),
                user.getUsername(),
                user.getEmail()
        );
    }
    
}
