package com.danis0n.service.user;

import com.danis0n.dto.CreateUserDto;
import com.danis0n.dto.UserDto;
import com.danis0n.entity.User;
import com.danis0n.exception.DuplicateResourceException;
import com.danis0n.repository.UserRepository;
import com.danis0n.validator.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.danis0n.util.Mapper.mapUserToUserDto;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder BCRYPT;
    private final ObjectValidator<CreateUserDto> createUserValidator;

    @Override
    public UserDto findUserByUUID(@NonNull String uuid) {
        UUID convertedUUID = UUID.fromString(uuid);

        return repository
                .findByUuid(convertedUUID)
                .map(mapUserToUserDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with id [%s] not found".formatted(uuid)
                ));
    }

    @Override
    public String addUser(@NonNull CreateUserDto dto) {
        createUserValidator.validate(dto);

        String username = dto.getUsername();
        String email = dto.getEmail();

        if (repository.findByEmail(email).isPresent()) {
            throw new DuplicateResourceException(
                    "Пользователь с данным email уже существует!"
            );
        }

        if (repository.findByUsername(username).isPresent()) {
            throw new DuplicateResourceException(
                    "Пользователь с данным username уже существует!"
            );
        }

        User user = new User(
                username,
                BCRYPT.encode(dto.getPassword()),
                email
        );

        return repository
                .save(user)
                .getUuid()
                .toString();
    }

}
