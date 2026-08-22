package com.example.assignment_Tracking_System.dto;


import com.example.assignment_Tracking_System.entity.User.Role;
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

    private Role role;

    private boolean active;
}
