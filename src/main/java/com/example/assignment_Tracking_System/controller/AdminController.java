package com.example.assignment_Tracking_System.controller;


import com.example.assignment_Tracking_System.dto.AssignmentRequest;
import com.example.assignment_Tracking_System.dto.AssignmentResponse;
import com.example.assignment_Tracking_System.dto.StudentIdsRequest;
import com.example.assignment_Tracking_System.dto.SubmissionResponse;
import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.dto.UserResponse;
import com.example.assignment_Tracking_System.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    // TRAINER

    // 1. Create Trainer
    @PostMapping("/trainers")
    public ResponseEntity<UserResponse> createTrainer(
            @Valid @RequestBody UserRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        adminService.createTrainer(request)
                );
    }


    // 2. Get All Trainers
    @GetMapping("/trainers")
    public ResponseEntity<List<UserResponse>>
    getAllTrainers() {

        return ResponseEntity.ok(
                adminService.getAllTrainers()
        );
    }


    // 3. Get Trainer
    @GetMapping("/trainers/{trainerId}")
    public ResponseEntity<UserResponse>
    getTrainer(
            @PathVariable Long trainerId) {

        return ResponseEntity.ok(
                adminService.getTrainer(trainerId)
        );
    }


    // 4. Update Trainer
    @PutMapping("/trainers/{trainerId}")
    public ResponseEntity<UserResponse>
    updateTrainer(
            @PathVariable Long trainerId,
            @Valid @RequestBody UserRequest request) {

        return ResponseEntity.ok(
                adminService.updateTrainer(
                        trainerId,
                        request
                )
        );
    }


    // 5. Delete Trainer
    @DeleteMapping("/trainers/{trainerId}")
    public ResponseEntity<Void>
    deleteTrainer(
            @PathVariable Long trainerId) {

        adminService.deleteTrainer(trainerId);

        return ResponseEntity
                .noContent()
                .build();
    }


    // ASSIGNMENT

    // 6. Create Assignment
    @PostMapping("/assignments")
    public ResponseEntity<AssignmentResponse>
    createAssignment(
            @Valid @RequestBody AssignmentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        adminService.createAssignment(request)
                );
    }


    // 7. Get All Assignments
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentResponse>>
    getAllAssignments() {

        return ResponseEntity.ok(
                adminService.getAllAssignments()
        );
    }


    // 8. Get Assignment
    @GetMapping("/assignments/{assignmentId}")
    public ResponseEntity<AssignmentResponse>
    getAssignment(
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                adminService.getAssignment(
                        assignmentId
                )
        );
    }


    // 9. Update Assignment
    @PutMapping("/assignments/{assignmentId}")
    public ResponseEntity<AssignmentResponse>
    updateAssignment(
            @PathVariable Long assignmentId,
            @Valid @RequestBody AssignmentRequest request) {

        return ResponseEntity.ok(
                adminService.updateAssignment(
                        assignmentId,
                        request
                )
        );
    }


    // 10. Delete Assignment
    @DeleteMapping("/assignments/{assignmentId}")
    public ResponseEntity<Void>
    deleteAssignment(
            @PathVariable Long assignmentId) {

        adminService.deleteAssignment(
                assignmentId
        );

        return ResponseEntity
                .noContent()
                .build();
    }


    // 11. Assign Assignment To Students
    @PostMapping(
            "/assignments/{assignmentId}/students"
    )
    public ResponseEntity<Void>
    assignAssignmentToStudents(
            @PathVariable Long assignmentId,
            @Valid @RequestBody StudentIdsRequest request) {

        adminService.assignAssignmentToStudents(
                assignmentId,
                request
        );

        return ResponseEntity.ok().build();
    }


    // 12. View Submissions
    @GetMapping(
            "/assignments/{assignmentId}/submissions"
    )
    public ResponseEntity<List<SubmissionResponse>>
    getAssignmentSubmissions(
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                adminService.getAssignmentSubmissions(
                        assignmentId
                )
        );
    }
}