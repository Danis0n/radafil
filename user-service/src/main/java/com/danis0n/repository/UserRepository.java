package com.danis0n.repository;

import com.danis0n.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUuid(@NonNull UUID uuid);
    Optional<User> findByEmail(@NonNull String email);
    Optional<User> findByUsername(@NonNull String username);

}
