package com.danis0n.service;

import com.danis0n.dto.CreateUserDto;
import com.danis0n.dto.UserDto;
import com.danis0n.entity.User;
import com.danis0n.exception.DuplicateResourceException;
import com.danis0n.mapper.UserMapper;
import com.danis0n.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder BCRYPT;
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
    public void addUser(@NonNull CreateUserDto dto) {

        String username = dto.getUsername();
        String email = dto.getEmail();

        if (repository.findByEmail(email).isPresent() ||
                repository.findByUsername(username).isPresent()) {
            throw new DuplicateResourceException(
                    "input data is already taken"
            );
        }

        User user = new User(
                dto.getUsername(),
                BCRYPT.encode(dto.getPassword()),
                dto.getEmail()
        );

        repository.save(user);

    }
}
