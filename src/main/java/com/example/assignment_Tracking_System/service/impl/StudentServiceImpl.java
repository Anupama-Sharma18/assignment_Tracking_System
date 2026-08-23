package com.example.assignment_Tracking_System.service.impl;

import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.dto.UserResponse;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.exception.DuplicateResourceException;
import com.example.assignment_Tracking_System.exception.ResourceNotFoundException;
import com.example.assignment_Tracking_System.repository.UserRepository;
import com.example.assignment_Tracking_System.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final UserRepository userRepository;


    // =========================================================
    // GET STUDENT PROFILE
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public UserResponse getStudent(Long id) {

        User student = userRepository
                .findByIdAndRole(id, User.Role.STUDENT)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student with id "
                                        + id
                                        + " not found"
                        )
                );

        return convertToUserResponse(student);
    }


    // =========================================================
    // UPDATE STUDENT PROFILE
    // =========================================================

    @Override
    public UserResponse updateStudent(
            Long id,
            UserRequest userRequest) {

        User student = userRepository
                .findByIdAndRole(id, User.Role.STUDENT)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student with id "
                                        + id
                                        + " not found"
                        )
                );


        // Check duplicate email
        userRepository.findByEmail(userRequest.getEmail())
                .ifPresent(existingUser -> {

                    if (!existingUser.getId().equals(id)) {

                        throw new DuplicateResourceException(
                                "Email already exists: "
                                        + userRequest.getEmail()
                        );
                    }
                });


        student.setName(userRequest.getName());
        student.setEmail(userRequest.getEmail());
        student.setPhone(userRequest.getPhone());
        student.setUpdatedAt(LocalDateTime.now());

        User updatedStudent =
                userRepository.save(student);

        return convertToUserResponse(updatedStudent);
    }


    // =========================================================
    // CONVERT USER → USER RESPONSE
    // =========================================================

    private UserResponse convertToUserResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }
}