package ru.center.inform.docs.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.center.inform.docs.client.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
