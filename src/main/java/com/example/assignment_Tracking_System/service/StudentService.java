package com.example.assignment_Tracking_System.service;

import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface StudentService {

    public User getStudent(Long id);
    public User updateStudent(Long id, UserRequest user);

}
