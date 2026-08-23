package com.example.assignment_Tracking_System.service;

import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.dto.UserResponse;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface StudentService {

    public UserResponse getStudent(Long id);
    public UserResponse updateStudent(Long id, UserRequest user);

}
