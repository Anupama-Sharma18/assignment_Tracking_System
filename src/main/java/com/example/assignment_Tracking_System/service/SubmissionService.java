package com.example.assignment_Tracking_System.service;

import com.example.assignment_Tracking_System.dto.SubmissionRequest;
import com.example.assignment_Tracking_System.entity.Submission;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SubmissionService {
    public Submission submitAssignment(Long studentId, Long assignmentId, SubmissionRequest submissionRequest);

    public Submission viewSubmission(Long studentId, Long assignmentId);

    Submission updateSubmission(Long studentId, Long assignmentId, SubmissionRequest submissionRequest);

    List<Submission> viewAllSubmissionByStudentId(Long studentId);

    Submission viewResult(Long studentId, Long submissionId);

}
