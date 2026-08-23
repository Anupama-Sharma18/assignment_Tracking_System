package com.example.assignment_Tracking_System.controller;

import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.dto.UserResponse;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/super-admin")
public class SuperAdminController {

    private final UserService userService;
    private final SubmissionService submissionService;
    private final StudentService studentService;
    private final TrainerService trainerService;
    private final SuperAdminService superAdminService;

    @PostMapping("/admins")
    public ResponseEntity<UserResponse> createAdmin(@RequestBody @Valid UserRequest userRequest){
        UserResponse userResponse = superAdminService.createAdmin(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<UserResponse>> getAllAdmins(){
        List<UserResponse> userResponseList = superAdminService.getAllAdmins();
        return ResponseEntity.ok(userResponseList);

    }

    @GetMapping("/admins/{id}")
    public ResponseEntity<UserResponse> getAdminById(@PathVariable Long id){
        UserResponse userResponse = superAdminService.getAdminById(id);
        return ResponseEntity.ok(userResponse);

    }
    @PutMapping("/admins/{id}")
    public ResponseEntity<UserResponse> updateAdmin(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest){
        UserResponse userResponse = superAdminService.updateAdmin(id, userRequest);
        return ResponseEntity.ok(userResponse);

    }
    @DeleteMapping("/admins/{id}")
    public ResponseEntity<UserResponse> deleteAdmin(@PathVariable Long id){
        UserResponse userResponse = superAdminService.deleteAdmin(id);
        return ResponseEntity.ok(userResponse);
    }
//    get all trainers
    @GetMapping("/trainers")
    public ResponseEntity<List<UserResponse>> getAllTrainers(){
        List<UserResponse> userResponseList = superAdminService.getAllAdmins();
        return ResponseEntity.ok(userResponseList);
    }


}
