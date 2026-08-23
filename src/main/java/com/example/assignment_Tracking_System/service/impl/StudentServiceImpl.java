package com.example.assignment_Tracking_System.service.impl;

import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.exception.ResourceNotFoundException;
import com.example.assignment_Tracking_System.repository.UserRepository;
import com.example.assignment_Tracking_System.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final UserRepository userRepository;

    //    Get Student Profile
    @Override
    public User getStudent(Long id){
        User student =  userRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException(
                                "Student with id "+ id + " is not found"));
        if (student.getRole() != User.Role.STUDENT){
            throw new ResourceNotFoundException("User with id "+ id + " is not a student");
        }
        return student;
    }

    //    Update Student Profile
    @Override
    public User updateStudent(Long id, UserRequest user){
        User student = getStudent(id);
        student.setName(user.getName());
        student.setPhone(user.getPhone());
        student.setEmail(user.getEmail());
        return userRepository.save(student);

    }
}
