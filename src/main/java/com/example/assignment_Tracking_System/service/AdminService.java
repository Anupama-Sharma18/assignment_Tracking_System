package com.example.assignment_Tracking_System.service;

import com.example.assignment_Tracking_System.dto.AssignmentRequest;
import com.example.assignment_Tracking_System.dto.AssignmentResponse;
import com.example.assignment_Tracking_System.dto.StudentIdsRequest;
import com.example.assignment_Tracking_System.dto.SubmissionResponse;
import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.dto.UserResponse;

import java.util.List;

public interface AdminService {

    // Trainer Management

    UserResponse createTrainer(UserRequest request);

    List<UserResponse> getAllTrainers();

    UserResponse getTrainer(Long trainerId);

    UserResponse updateTrainer(
            Long trainerId,
            UserRequest request
    );

    void deleteTrainer(Long trainerId);


    // Assignment Management

    AssignmentResponse createAssignment(
            AssignmentRequest request
    );

    List<AssignmentResponse> getAllAssignments();

    AssignmentResponse getAssignment(
            Long assignmentId
    );

    AssignmentResponse updateAssignment(
            Long assignmentId,
            AssignmentRequest request
    );

    void deleteAssignment(
            Long assignmentId
    );

    void assignAssignmentToStudents(
            Long assignmentId,
            StudentIdsRequest request
    );

    List<SubmissionResponse> getAssignmentSubmissions(
            Long assignmentId
    );
}
