package com.example.assignment_Tracking_System.dto;

import com.example.assignment_Tracking_System.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private User.Role role;

    private boolean active;
}