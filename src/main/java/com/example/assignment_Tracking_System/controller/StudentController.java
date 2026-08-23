package com.example.assignment_Tracking_System.controller;


import com.example.assignment_Tracking_System.dto.AssignmentResponse;
import com.example.assignment_Tracking_System.dto.SubmissionRequest;
import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.dto.UserResponse;
import com.example.assignment_Tracking_System.entity.Assignment;
import com.example.assignment_Tracking_System.entity.Submission;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.service.AdminService;
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


    private final SubmissionService submissionService;
    private final AssignmentService assignmentService;
    private final StudentService studentService;

//    Get Student Profile
@GetMapping("/{studentId}")
public ResponseEntity<UserResponse> getStudent(
        @PathVariable Long studentId) {

    return ResponseEntity.ok(
            studentService.getStudent(studentId)
    );
}

    //    Update Student Profile
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


//    Submit Assignment
    @PostMapping("/{studentId}/assignments/{assignmentId}/submissions")
    public ResponseEntity<Submission> submitAssignment(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId,
            @RequestBody SubmissionRequest submissionRequest){

        Submission submission = submissionService.submitAssignment(studentId, assignmentId, submissionRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

//    View My Submission
@GetMapping("/{studentId}/assignments")
public ResponseEntity<List<AssignmentResponse>>
viewStudentAssignments(
        @PathVariable Long studentId) {

    List<AssignmentResponse> assignmentList =
            assignmentService.getStudentAssignments(studentId);

    return ResponseEntity.ok(assignmentList);
}

//    Update Submission
    @PutMapping("/{studentId}/assignments/{assignmentId}/submission")
    public ResponseEntity<Submission> updateSubmission(@PathVariable Long studentId, @PathVariable Long assignmentId,@RequestBody @Valid SubmissionRequest submissionRequest){
            Submission submission = submissionService.updateSubmission(studentId,assignmentId,submissionRequest);
            return ResponseEntity.ok(submission);
    }

//    View all my Submission
    @GetMapping("/{studentId}/submissions")
    public ResponseEntity<List<Submission>> viewAllSubmissionByStudentId(@PathVariable Long studentId){
            List<Submission> submissionList = submissionService.viewAllSubmissionByStudentId(studentId);
            return ResponseEntity.ok(submissionList);
    }

//    View Result for a particular assignment
    @GetMapping("/{studentId}/submissions/{submissionId}/result")
    public ResponseEntity<Submission> viewResult(@PathVariable Long studentId, @PathVariable Long submissionId){
        Submission result = submissionService.viewResult(studentId,submissionId);
        return ResponseEntity.ok(result);

    }






}
