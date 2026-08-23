package com.example.assignment_Tracking_System.controller;


import com.example.assignment_Tracking_System.dto.*;
import com.example.assignment_Tracking_System.entity.Assignment;
import com.example.assignment_Tracking_System.entity.Submission;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.service.AssignmentService;
import com.example.assignment_Tracking_System.service.StudentService;
import com.example.assignment_Tracking_System.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final SubmissionService submissionService;
    private final AssignmentService assignmentService;

//    Get Student Profile
// Get Student Profile
@GetMapping("/{studentId}")
public ResponseEntity<UserResponse> getStudent(
        @PathVariable Long studentId) {

    return ResponseEntity.ok(
            studentService.getStudent(studentId)
    );
}

    // Update Student Profile
    @PutMapping("/{studentId}")
    public ResponseEntity<UserResponse> updateStudent(
            @PathVariable Long studentId,
            @Valid @RequestBody UserRequest user) {

        return ResponseEntity.ok(
                studentService.updateStudent(
                        studentId,
                        user
                )
        );
    }

//    View my assignment
    @GetMapping("/{studentId}/assignments")
    public ResponseEntity<List<AssignmentResponse>> viewStudentAssignments(@PathVariable Long studentId){
            List<AssignmentResponse> assignmentList = assignmentService.getStudentAssignments(studentId);
            return ResponseEntity.ok(assignmentList);
    }

//    Submit Assignment
    @PostMapping("/{studentId}/assignments/{assignmentId}/submissions")
    public ResponseEntity<SubmissionResponse> submitAssignment(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId,
            @RequestBody SubmissionRequest submissionRequest){

        SubmissionResponse submission = submissionService.submitAssignment(studentId, assignmentId, submissionRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

//    View My Submission
    @GetMapping("/{studentId}/assignments/{assignmentId}/submission")
    public ResponseEntity<SubmissionResponse> viewSubmission(@PathVariable Long studentId, @PathVariable Long assignmentId){
        SubmissionResponse submission = submissionService.viewSubmission(studentId, assignmentId);
        return ResponseEntity.ok(submission);
    }

//    Update Submission
    @PutMapping("/{studentId}/assignments/{assignmentId}/submission")
    public ResponseEntity<SubmissionResponse> updateSubmission(@PathVariable Long studentId, @PathVariable Long assignmentId,@RequestBody @Valid SubmissionRequest submissionRequest){
            SubmissionResponse submission = submissionService.updateSubmission(studentId,assignmentId,submissionRequest);
            return ResponseEntity.ok(submission);
    }

//    View all my Submission
    @GetMapping("/{studentId}/submissions")
    public ResponseEntity<List<SubmissionResponse>> viewAllSubmissionByStudentId(@PathVariable Long studentId){
            List<SubmissionResponse> submissionList = submissionService.viewAllSubmissionByStudentId(studentId);
            return ResponseEntity.ok(submissionList);
    }

//    View Result for a particular assignment
    @GetMapping("/{studentId}/submissions/{submissionId}/result")
    public ResponseEntity<SubmissionResponse> viewResult(@PathVariable Long studentId, @PathVariable Long submissionId){
        SubmissionResponse result = submissionService.viewResult(studentId,submissionId);
        return ResponseEntity.ok(result);

    }






}
