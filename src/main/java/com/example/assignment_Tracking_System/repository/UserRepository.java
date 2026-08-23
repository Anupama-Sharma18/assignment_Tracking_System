package com.example.assignment_Tracking_System.repository;

import com.example.assignment_Tracking_System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
