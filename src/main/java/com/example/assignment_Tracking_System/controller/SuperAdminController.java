package com.example.assignment_Tracking_System.controller;

import com.example.assignment_Tracking_System.dto.*;
import com.example.assignment_Tracking_System.entity.Assignment;
import com.example.assignment_Tracking_System.entity.Submission;
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


    private final SuperAdminService superAdminService;
    private final AdminService adminService;

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
        List<UserResponse> userResponseList = adminService.getAllTrainers();
        return ResponseEntity.ok(userResponseList);
    }

    //    get all Assignments
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentResponse>> getAllAssignments(){
        List<AssignmentResponse> assignmentList = adminService.getAllAssignments();
        return ResponseEntity.ok(assignmentList);
    }

    //    get all students
    @GetMapping("/students")
    public ResponseEntity<List<UserResponse>> getAllStudents(){
        List<UserResponse> userResponseList = superAdminService.getAllStudents();
        return ResponseEntity.ok(userResponseList);
    }
//get all submissions
    @GetMapping("/submissions")
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissions(){
        List<SubmissionResponse> submissionList = superAdminService.getAllSubmissions();
        return ResponseEntity.ok(submissionList);
    }
// dashboard statistics
@GetMapping("/dashboard")
public DashboardResponse getDashboardStatistics() {
    return superAdminService.getDashboardStatistics();
}






}
