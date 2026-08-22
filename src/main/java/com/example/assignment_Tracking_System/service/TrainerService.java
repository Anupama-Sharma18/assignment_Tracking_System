package com.example.assignment_Tracking_System.service;


import com.example.assignment_Tracking_System.dto.*;

import java.util.List;

public interface TrainerService {

    AssignmentResponse createAssignment(
            Long trainerId,
            AssignmentRequest request
    );

    List<AssignmentResponse> getTrainerAssignments(
            Long trainerId
    );

    AssignmentResponse getAssignment(
            Long trainerId,
            Long assignmentId
    );

    AssignmentResponse updateAssignment(
            Long trainerId,
            Long assignmentId,
            AssignmentRequest request
    );

    void deleteAssignment(
            Long trainerId,
            Long assignmentId
    );

    List<UserResponse> getStudents(
            Long trainerId
    );

    void assignStudents(
            Long trainerId,
            Long assignmentId,
            StudentIdsRequest request
    );

    List<UserResponse> getAssignedStudents(
            Long trainerId,
            Long assignmentId
    );

    List<SubmissionResponse> getTrainerSubmissions(
            Long trainerId
    );

    List<SubmissionResponse> getAssignmentSubmissions(
            Long trainerId,
            Long assignmentId
    );

    SubmissionResponse evaluateSubmission(
            Long trainerId,
            Long submissionId,
            EvaluationRequest request
    );
}
