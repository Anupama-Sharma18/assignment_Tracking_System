package com.example.assignment_Tracking_System.service;

import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.dto.UserResponse;

import java.util.List;

public interface SuperAdminService {

    UserResponse createAdmin(UserRequest userRequest);
    List<UserResponse> getAllAdmins();
    UserResponse getAdminById(Long id);
    UserResponse updateAdmin(Long id, UserRequest userRequest);
    UserResponse deleteAdmin(Long id);
}
