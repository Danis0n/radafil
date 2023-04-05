package com.danis0n.repository;

import com.danis0n.entity.AUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AUser, Integer> {
    Optional<AUser> findByName(String username);
}
