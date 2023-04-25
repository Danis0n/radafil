package com.danis0n.mapper;

import com.danis0n.dto.UserDto;
import com.danis0n.entity.Role;
import com.danis0n.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserMapper implements Function<User, UserDto> {

    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getUuid(),
                user.getUsername(),
                user.getRoles()
                    .stream()
                    .map(Role::getName)
                    .collect(Collectors.toList())
        );
    }

}
