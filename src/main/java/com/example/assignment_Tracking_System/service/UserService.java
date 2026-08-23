package com.example.assignment_Tracking_System.service;

import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.entity.User;

public interface UserService {

    public void create(UserRequest userRequest);
}
