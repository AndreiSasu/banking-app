package com.andrei.sasu.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.andrei.sasu.backend.model.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(final String userName);
}
