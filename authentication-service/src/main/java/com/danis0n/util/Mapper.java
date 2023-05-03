package com.danis0n.util;

import com.danis0n.dto.AuthenticationDataDto;
import com.danis0n.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Mapper {

    public static Function<AuthenticationDataDto, User> mapDataToUser =
            data -> new User(data.getUsername(), data.getHashedPassword());

}
