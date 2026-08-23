package com.example.assignment_Tracking_System.service.impl;

import com.example.assignment_Tracking_System.dto.SubmissionRequest;
import com.example.assignment_Tracking_System.dto.SubmissionResponse;
import com.example.assignment_Tracking_System.entity.Submission;
import com.example.assignment_Tracking_System.exception.ResourceNotFoundException;
import com.example.assignment_Tracking_System.repository.SubmissionRepository;
import com.example.assignment_Tracking_System.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;


    // Submit Assignment
    @Override
    public SubmissionResponse submitAssignment(
            Long studentId,
            Long assignmentId,
            SubmissionRequest submissionRequest) {

        Submission submission = Submission.builder()
                .assignmentId(assignmentId)
                .studentId(studentId)
                .submissionText(submissionRequest.getSubmissionText())
                .submittedAt(LocalDateTime.now())
                .status(Submission.Status.SUBMITTED)
                .build();

        Submission savedSubmission =
                submissionRepository.save(submission);

        return SubmissionResponse.builder()
                .id(savedSubmission.getId())
                .assignmentId(savedSubmission.getAssignmentId())
                .studentId(savedSubmission.getStudentId())
                .submissionText(savedSubmission.getSubmissionText())
                .submittedAt(savedSubmission.getSubmittedAt())
                .marks(savedSubmission.getMarks())
                .feedback(savedSubmission.getFeedback())
                .status(savedSubmission.getStatus())
                .evaluatedAt(savedSubmission.getEvaluatedAt())
                .build();
    }


    // View Submission
    @Override
    public SubmissionResponse viewSubmission(
            Long studentId,
            Long assignmentId) {

        Submission submission =
                submissionRepository
                        .findByStudentIdAndAssignmentId(
                                studentId,
                                assignmentId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Submission not found for student id "
                                                + studentId
                                                + " and assignment id "
                                                + assignmentId
                                )
                        );

        return SubmissionResponse.builder()
                .id(submission.getId())
                .assignmentId(submission.getAssignmentId())
                .studentId(submission.getStudentId())
                .submissionText(submission.getSubmissionText())
                .submittedAt(submission.getSubmittedAt())
                .marks(submission.getMarks())
                .feedback(submission.getFeedback())
                .status(submission.getStatus())
                .evaluatedAt(submission.getEvaluatedAt())
                .build();
    }


    // Update Submission
    @Override
    public SubmissionResponse updateSubmission(
            Long studentId,
            Long assignmentId,
            SubmissionRequest submissionRequest) {

        Submission submission =
                submissionRepository
                        .findByStudentIdAndAssignmentId(
                                studentId,
                                assignmentId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Submission not found for student id "
                                                + studentId
                                                + " and assignment id "
                                                + assignmentId
                                )
                        );

        // Evaluated submission cannot be updated
        if (submission.getStatus()
                == Submission.Status.EVALUATED) {

            throw new IllegalStateException(
                    "Submission cannot be modified after evaluation"
            );
        }

        submission.setSubmissionText(
                submissionRequest.getSubmissionText()
        );

        Submission updatedSubmission =
                submissionRepository.save(submission);

        return SubmissionResponse.builder()
                .id(updatedSubmission.getId())
                .assignmentId(updatedSubmission.getAssignmentId())
                .studentId(updatedSubmission.getStudentId())
                .submissionText(updatedSubmission.getSubmissionText())
                .submittedAt(updatedSubmission.getSubmittedAt())
                .marks(updatedSubmission.getMarks())
                .feedback(updatedSubmission.getFeedback())
                .status(updatedSubmission.getStatus())
                .evaluatedAt(updatedSubmission.getEvaluatedAt())
                .build();
    }


    // View All My Submissions
    @Override
    public List<SubmissionResponse> viewAllSubmissionByStudentId(
            Long studentId) {

        List<Submission> submissionList =
                submissionRepository.findAllByStudentId(studentId);

        if (submissionList.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No Submission found for student id "
                            + studentId
            );
        }

        return submissionList
                .stream()
                .map(submission ->
                        SubmissionResponse.builder()
                                .id(submission.getId())
                                .assignmentId(submission.getAssignmentId())
                                .studentId(submission.getStudentId())
                                .submissionText(submission.getSubmissionText())
                                .submittedAt(submission.getSubmittedAt())
                                .marks(submission.getMarks())
                                .feedback(submission.getFeedback())
                                .status(submission.getStatus())
                                .evaluatedAt(submission.getEvaluatedAt())
                                .build()
                )
                .toList();
    }


    // View Result
    @Override
    public SubmissionResponse viewResult(
            Long studentId,
            Long submissionId) {

        Submission submission =
                submissionRepository.findById(submissionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Submission with id "
                                                + submissionId
                                                + " not found"
                                )
                        );

        // Check submission belongs to student
        if (!submission.getStudentId()
                .equals(studentId)) {

            throw new ResourceNotFoundException(
                    "Submission does not belong to student with id "
                            + studentId
            );
        }

        // Result can only be viewed after evaluation
        if (submission.getStatus()
                != Submission.Status.EVALUATED) {

            throw new ResourceNotFoundException(
                    "Result is not evaluated yet"
            );
        }

        return SubmissionResponse.builder()
                .id(submission.getId())
                .assignmentId(submission.getAssignmentId())
                .studentId(submission.getStudentId())
                .submissionText(submission.getSubmissionText())
                .submittedAt(submission.getSubmittedAt())
                .marks(submission.getMarks())
                .feedback(submission.getFeedback())
                .status(submission.getStatus())
                .evaluatedAt(submission.getEvaluatedAt())
                .build();
    }
}
