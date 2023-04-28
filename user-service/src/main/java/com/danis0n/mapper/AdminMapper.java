package com.danis0n.mapper;

import com.danis0n.dto.AdminDto;
import com.danis0n.entity.Admin;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AdminMapper implements Function<Admin, AdminDto> {

    @Override
    public AdminDto apply(Admin admin) {
        return new AdminDto(
                admin.getUuid(),
                admin.getUsername()
        );
    }
}
