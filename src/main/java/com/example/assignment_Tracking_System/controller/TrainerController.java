package com.example.assignment_Tracking_System.controller;


import com.example.assignment_Tracking_System.dto.*;
import com.example.assignment_Tracking_System.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers/{trainerId}")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;


    // =========================================================
    // CREATE ASSIGNMENT
    // =========================================================

    @PostMapping("/assignments")
    public ResponseEntity<AssignmentResponse> createAssignment(
            @PathVariable Long trainerId,
            @Valid @RequestBody AssignmentRequest request) {

        AssignmentResponse response =
                trainerService.createAssignment(
                        trainerId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =========================================================
    // GET TRAINER ASSIGNMENTS
    // =========================================================

    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentResponse>> getAssignments(
            @PathVariable Long trainerId) {

        return ResponseEntity.ok(
                trainerService.getTrainerAssignments(
                        trainerId
                )
        );
    }


    // =========================================================
    // GET ASSIGNMENT
    // =========================================================

    @GetMapping("/assignments/{assignmentId}")
    public ResponseEntity<AssignmentResponse> getAssignment(
            @PathVariable Long trainerId,
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                trainerService.getAssignment(
                        trainerId,
                        assignmentId
                )
        );
    }


    // =========================================================
    // UPDATE ASSIGNMENT
    // =========================================================

    @PutMapping("/assignments/{assignmentId}")
    public ResponseEntity<AssignmentResponse> updateAssignment(
            @PathVariable Long trainerId,
            @PathVariable Long assignmentId,
            @Valid @RequestBody AssignmentRequest request) {

        return ResponseEntity.ok(
                trainerService.updateAssignment(
                        trainerId,
                        assignmentId,
                        request
                )
        );
    }


    // =========================================================
    // DELETE ASSIGNMENT
    // =========================================================

    @DeleteMapping("/assignments/{assignmentId}")
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable Long trainerId,
            @PathVariable Long assignmentId) {

        trainerService.deleteAssignment(
                trainerId,
                assignmentId
        );

        return ResponseEntity.noContent().build();
    }


    // =========================================================
    // VIEW STUDENTS
    // =========================================================

    @GetMapping("/students")
    public ResponseEntity<List<UserResponse>> getStudents(
            @PathVariable Long trainerId) {

        return ResponseEntity.ok(
                trainerService.getStudents(
                        trainerId
                )
        );
    }


    // =========================================================
    // ASSIGN ASSIGNMENT TO STUDENTS
    // =========================================================

    @PostMapping("/assignments/{assignmentId}/students")
    public ResponseEntity<Void> assignStudents(
            @PathVariable Long trainerId,
            @PathVariable Long assignmentId,
            @Valid @RequestBody StudentIdsRequest request) {

        trainerService.assignStudents(
                trainerId,
                assignmentId,
                request
        );

        return ResponseEntity.ok().build();
    }


    // =========================================================
    // VIEW ASSIGNED STUDENTS
    // =========================================================

    @GetMapping("/assignments/{assignmentId}/students")
    public ResponseEntity<List<UserResponse>> getAssignedStudents(
            @PathVariable Long trainerId,
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                trainerService.getAssignedStudents(
                        trainerId,
                        assignmentId
                )
        );
    }


    // =========================================================
    // VIEW ALL SUBMISSIONS
    // =========================================================

    @GetMapping("/submissions")
    public ResponseEntity<List<SubmissionResponse>> getSubmissions(
            @PathVariable Long trainerId) {

        return ResponseEntity.ok(
                trainerService.getTrainerSubmissions(
                        trainerId
                )
        );
    }


    // =========================================================
    // VIEW ASSIGNMENT SUBMISSIONS
    // =========================================================

    @GetMapping(
            "/assignments/{assignmentId}/submissions"
    )
    public ResponseEntity<List<SubmissionResponse>>
    getAssignmentSubmissions(
            @PathVariable Long trainerId,
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                trainerService.getAssignmentSubmissions(
                        trainerId,
                        assignmentId
                )
        );
    }


    // =========================================================
    // EVALUATE SUBMISSION
    // =========================================================

    @PutMapping(
            "/submissions/{submissionId}/evaluate"
    )
    public ResponseEntity<SubmissionResponse>
    evaluateSubmission(
            @PathVariable Long trainerId,
            @PathVariable Long submissionId,
            @Valid @RequestBody EvaluationRequest request) {

        return ResponseEntity.ok(
                trainerService.evaluateSubmission(
                        trainerId,
                        submissionId,
                        request
                )
        );
    }
}
