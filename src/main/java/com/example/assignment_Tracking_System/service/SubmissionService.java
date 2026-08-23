package com.example.assignment_Tracking_System.service;

import com.example.assignment_Tracking_System.dto.SubmissionRequest;
import com.example.assignment_Tracking_System.dto.SubmissionResponse;
import com.example.assignment_Tracking_System.entity.Submission;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SubmissionService {

    SubmissionResponse submitAssignment(
            Long studentId,
            Long assignmentId,
            SubmissionRequest submissionRequest
    );

    SubmissionResponse viewSubmission(
            Long studentId,
            Long assignmentId
    );

    SubmissionResponse updateSubmission(
            Long studentId,
            Long assignmentId,
            SubmissionRequest submissionRequest
    );

    List<SubmissionResponse> viewAllSubmissionByStudentId(
            Long studentId
    );

    SubmissionResponse viewResult(
            Long studentId,
            Long submissionId
    );
}
