package com.example.assignment_Tracking_System.service.impl;

import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.repository.UserRepository;
import com.example.assignment_Tracking_System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public void create(UserRequest userRequest){
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .build();
        userRepository.save(user);
    }
}
