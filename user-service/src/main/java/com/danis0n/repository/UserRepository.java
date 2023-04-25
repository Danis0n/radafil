package com.danis0n.repository;

import com.danis0n.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Override
    @NonNull
    Optional<User> findById(@NonNull String uuid);
}
