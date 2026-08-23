package com.example.assignment_Tracking_System.repository;


import com.example.assignment_Tracking_System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(User.Role role);

    List<User> findByRoleAndActiveTrue(User.Role role);

    Optional<User> findByIdAndRole(
            Long id,
            User.Role role
    );

    long countByRole(User.Role role);
}
