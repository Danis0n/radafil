package com.danis0n.util;

import com.danis0n.dto.AdminDto;
import com.danis0n.dto.UserDto;
import com.danis0n.entity.Admin;
import com.danis0n.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Mapper {

    public static Function<User, UserDto> mapUserToUserDto =
            user -> new UserDto(
                    user.getUuid().toString(),
                    user.getUsername(),
                    user.getEmail()
            );

    public static Function<Admin, AdminDto> mapAdminToAdminDto =
            admin -> new AdminDto(
                    admin.getUuid().toString(),
                    admin.getUsername()
            );

}
