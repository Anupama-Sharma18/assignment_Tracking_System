package com.example.assignment_Tracking_System.service.impl;

import com.example.assignment_Tracking_System.dto.SubmissionRequest;
import com.example.assignment_Tracking_System.dto.SubmissionResponse;
import com.example.assignment_Tracking_System.entity.Assignment;
import com.example.assignment_Tracking_System.entity.AssignmentStudent;
import com.example.assignment_Tracking_System.entity.Submission;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.exception.InvalidSubmissionException;
import com.example.assignment_Tracking_System.exception.ResourceNotFoundException;
import com.example.assignment_Tracking_System.repository.AssignmentRepository;
import com.example.assignment_Tracking_System.repository.AssignmentStudentRepository;
import com.example.assignment_Tracking_System.repository.SubmissionRepository;
import com.example.assignment_Tracking_System.repository.UserRepository;
import com.example.assignment_Tracking_System.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentStudentRepository assignmentStudentRepository;
    private final UserRepository userRepository;


    // =========================================================
    // SUBMIT ASSIGNMENT
    // =========================================================

    @Override
    public SubmissionResponse submitAssignment(
            Long studentId,
            Long assignmentId,
            SubmissionRequest submissionRequest) {

        // 1. Check student exists
        User student = userRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student with id "
                                        + studentId
                                        + " not found"
                        )
                );

        // 2. Check user is actually a student
        if (student.getRole() != User.Role.STUDENT) {
            throw new InvalidSubmissionException(
                    "User with id "
                            + studentId
                            + " is not a student"
            );
        }

        // 3. Check student is active
        if (!student.isActive()) {
            throw new InvalidSubmissionException(
                    "Student with id "
                            + studentId
                            + " is inactive"
            );
        }

        // 4. Check assignment exists
        Assignment assignment =
                assignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Assignment with id "
                                                + assignmentId
                                                + " not found"
                                )
                        );

        // 5. Check student is assigned to assignment
        boolean assigned =
                assignmentStudentRepository
                        .existsByAssignmentIdAndStudentId(
                                assignmentId,
                                studentId
                        );

        if (!assigned) {
            throw new InvalidSubmissionException(
                    "Student with id "
                            + studentId
                            + " is not assigned to assignment with id "
                            + assignmentId
            );
        }

        // 6. Check if submission already exists
        boolean submissionExists =
                submissionRepository
                        .findByStudentIdAndAssignmentId(
                                studentId,
                                assignmentId
                        )
                        .isPresent();

        if (submissionExists) {
            throw new InvalidSubmissionException(
                    "Student has already submitted this assignment. "
                            + "Use update submission if modification is allowed."
            );
        }

        // 7. Check due date
        if (assignment.getDueDate() != null
                && assignment.getDueDate()
                .isBefore(java.time.LocalDate.now())) {

            throw new InvalidSubmissionException(
                    "Assignment due date has passed"
            );
        }

        // 8. Create submission
        Submission submission = Submission.builder()
                .assignmentId(assignmentId)
                .studentId(studentId)
                .submissionText(
                        submissionRequest.getSubmissionText()
                )
                .submittedAt(LocalDateTime.now())
                .status(Submission.Status.SUBMITTED)
                .build();

        Submission savedSubmission =
                submissionRepository.save(submission);

        return convertToSubmissionResponse(savedSubmission);
    }


    // =========================================================
    // VIEW SUBMISSION
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public SubmissionResponse viewSubmission(
            Long studentId,
            Long assignmentId) {

        // Check student
        validateStudent(studentId);

        // Check assignment
        assignmentRepository.findById(assignmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assignment with id "
                                        + assignmentId
                                        + " not found"
                        )
                );

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

        return convertToSubmissionResponse(submission);
    }


    // =========================================================
    // UPDATE SUBMISSION
    // =========================================================

    @Override
    public SubmissionResponse updateSubmission(
            Long studentId,
            Long assignmentId,
            SubmissionRequest submissionRequest) {

        // Check student
        validateStudent(studentId);

        // Check assignment
        assignmentRepository.findById(assignmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assignment with id "
                                        + assignmentId
                                        + " not found"
                        )
                );

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

        // Cannot update after evaluation
        if (submission.getStatus()
                == Submission.Status.EVALUATED) {

            throw new InvalidSubmissionException(
                    "Submission cannot be modified after evaluation"
            );
        }

        // Update submission text
        submission.setSubmissionText(
                submissionRequest.getSubmissionText()
        );

        Submission updatedSubmission =
                submissionRepository.save(submission);

        return convertToSubmissionResponse(
                updatedSubmission
        );
    }


    // =========================================================
    // VIEW ALL STUDENT SUBMISSIONS
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponse> viewAllSubmissionByStudentId(
            Long studentId) {

        // Check student
        validateStudent(studentId);

        List<Submission> submissions =
                submissionRepository.findAllByStudentId(studentId);

        if (submissions.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No submissions found for student id "
                            + studentId
            );
        }

        return submissions.stream()
                .map(this::convertToSubmissionResponse)
                .toList();
    }


    // =========================================================
    // VIEW RESULT
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public SubmissionResponse viewResult(
            Long studentId,
            Long submissionId) {

        // Check student
        validateStudent(studentId);

        // Find submission
        Submission submission =
                submissionRepository.findById(submissionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Submission with id "
                                                + submissionId
                                                + " not found"
                                )
                        );

        // Make sure submission belongs to student
        if (!submission.getStudentId()
                .equals(studentId)) {

            throw new ResourceNotFoundException(
                    "Submission does not belong to student with id "
                            + studentId
            );
        }

        // Result only available after evaluation
        if (submission.getStatus()
                != Submission.Status.EVALUATED) {

            throw new InvalidSubmissionException(
                    "Result is not evaluated yet"
            );
        }

        return convertToSubmissionResponse(submission);
    }


    // =========================================================
    // VALIDATE STUDENT
    // =========================================================

    private User validateStudent(Long studentId) {

        User student =
                userRepository.findById(studentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student with id "
                                                + studentId
                                                + " not found"
                                )
                        );

        if (student.getRole()
                != User.Role.STUDENT) {

            throw new ResourceNotFoundException(
                    "Student with id "
                            + studentId
                            + " not found"
            );
        }

        return student;
    }


    // =========================================================
    // RESPONSE CONVERTER
    // =========================================================

    private SubmissionResponse convertToSubmissionResponse(
            Submission submission) {

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