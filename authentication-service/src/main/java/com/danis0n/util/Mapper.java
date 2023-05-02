package com.danis0n.util;

import com.danis0n.entity.AuthenticationData;
import com.danis0n.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Mapper {

    public static Function<AuthenticationData, User> mapDataToUser =
            data -> new User(data.getUsername(), data.getHashedPassword());

}
