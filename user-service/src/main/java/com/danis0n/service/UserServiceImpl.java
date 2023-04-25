package com.danis0n.service;

import com.danis0n.dto.CreateUserDto;
import com.danis0n.dto.UserDto;
import com.danis0n.mapper.UserMapper;
import com.danis0n.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserDto findUserByUUID(String uuid) {
        return repository.findById(uuid)
                .map(mapper)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with id [%s] not found".formatted(uuid)
                ));
    }

    @Override
    public UserDto create(CreateUserDto dto) {
//        return repository.save();
        return null;
    }
}
