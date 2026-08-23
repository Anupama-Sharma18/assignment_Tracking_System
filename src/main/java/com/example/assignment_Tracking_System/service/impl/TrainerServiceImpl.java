package com.example.assignment_Tracking_System.service.impl;


import com.example.assignment_Tracking_System.dto.*;
import com.example.assignment_Tracking_System.entity.Assignment;
import com.example.assignment_Tracking_System.entity.AssignmentStudent;
import com.example.assignment_Tracking_System.entity.Submission;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.exception.InvalidAssignmentException;
import com.example.assignment_Tracking_System.exception.InvalidSubmissionException;
import com.example.assignment_Tracking_System.exception.ResourceNotFoundException;
import com.example.assignment_Tracking_System.repository.AssignmentRepository;
import com.example.assignment_Tracking_System.repository.AssignmentStudentRepository;
import com.example.assignment_Tracking_System.repository.SubmissionRepository;
import com.example.assignment_Tracking_System.repository.UserRepository;
import com.example.assignment_Tracking_System.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentStudentRepository assignmentStudentRepository;
    private final SubmissionRepository submissionRepository;



    // CREATE ASSIGNMENT


    @Override
    public AssignmentResponse createAssignment(
            Long trainerId,
            AssignmentRequest request) {

        User trainer = getTrainer(trainerId);

        Assignment assignment = Assignment.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .assignedDate(LocalDate.now())
                .dueDate(request.getDueDate())
                .maxMarks(request.getMaxMarks())
                .status(Assignment.Status.CREATED)
                .trainerId(trainer.getId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Assignment savedAssignment =
                assignmentRepository.save(assignment);

        return convertToAssignmentResponse(savedAssignment);
    }



    // GET TRAINER ASSIGNMENTS


    @Override
    @Transactional(readOnly = true)
    public List<AssignmentResponse> getTrainerAssignments(
            Long trainerId) {

        getTrainer(trainerId);

        return assignmentRepository
                .findByTrainerIdOrderByDueDateAsc(trainerId)
                .stream()
                .map(this::convertToAssignmentResponse)
                .toList();
    }



    // GET ASSIGNMENT


    @Override
    @Transactional(readOnly = true)
    public AssignmentResponse getAssignment(
            Long trainerId,
            Long assignmentId) {

        getTrainer(trainerId);

        Assignment assignment =
                assignmentRepository
                        .findByIdAndTrainerId(
                                assignmentId,
                                trainerId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Assignment with id "
                                                + assignmentId
                                                + " not found"
                                )
                        );

        return convertToAssignmentResponse(assignment);
    }



    // UPDATE ASSIGNMENT


    @Override
    public AssignmentResponse updateAssignment(
            Long trainerId,
            Long assignmentId,
            AssignmentRequest request) {

        Assignment assignment =
                assignmentRepository
                        .findByIdAndTrainerId(
                                assignmentId,
                                trainerId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Assignment with id "
                                                + assignmentId
                                                + " not found"
                                )
                        );

        if (assignment.getStatus()
                == Assignment.Status.CLOSED) {

            throw new InvalidAssignmentException(
                    "Closed assignment cannot be updated"
            );
        }

        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment.setDueDate(request.getDueDate());
        assignment.setMaxMarks(request.getMaxMarks());
        assignment.setUpdatedAt(LocalDateTime.now());

        return convertToAssignmentResponse(
                assignmentRepository.save(assignment)
        );
    }



    // DELETE ASSIGNMENT


    @Override
    public void deleteAssignment(
            Long trainerId,
            Long assignmentId) {

        Assignment assignment =
                assignmentRepository
                        .findByIdAndTrainerId(
                                assignmentId,
                                trainerId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Assignment with id "
                                                + assignmentId
                                                + " not found"
                                )
                        );

        submissionRepository
                .deleteByAssignmentId(assignmentId);

        assignmentStudentRepository
                .deleteByAssignmentId(assignmentId);

        assignmentRepository.delete(assignment);
    }



    // VIEW STUDENTS


    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getStudents(
            Long trainerId) {

        getTrainer(trainerId);

        return userRepository
                .findByRoleAndActiveTrue(User.Role.STUDENT)
                .stream()
                .map(this::convertToUserResponse)
                .toList();
    }



    // ASSIGN ASSIGNMENT TO STUDENTS


    @Override
    public void assignStudents(
            Long trainerId,
            Long assignmentId,
            StudentIdsRequest request) {

        Assignment assignment =
                assignmentRepository
                        .findByIdAndTrainerId(
                                assignmentId,
                                trainerId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Assignment with id "
                                                + assignmentId
                                                + " not found"
                                )
                        );

        if (assignment.getStatus()
                == Assignment.Status.CLOSED) {

            throw new InvalidAssignmentException(
                    "Closed assignment cannot be assigned"
            );
        }

        for (Long studentId : request.getStudentIds()) {

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

                throw new InvalidAssignmentException(
                        "User with id "
                                + studentId
                                + " is not a student"
                );
            }

            if (!student.isActive()) {

                throw new InvalidAssignmentException(
                        "Student with id "
                                + studentId
                                + " is inactive"
                );
            }

            boolean alreadyAssigned =
                    assignmentStudentRepository
                            .existsByAssignmentIdAndStudentId(
                                    assignmentId,
                                    studentId
                            );

            if (!alreadyAssigned) {

                AssignmentStudent assignmentStudent =
                        AssignmentStudent.builder()
                                .assignmentId(assignmentId)
                                .studentId(studentId)
                                .assignedAt(
                                        LocalDateTime.now()
                                )
                                .build();

                assignmentStudentRepository
                        .save(assignmentStudent);
            }
        }

        assignment.setStatus(
                Assignment.Status.ASSIGNED
        );

        assignment.setUpdatedAt(
                LocalDateTime.now()
        );

        assignmentRepository.save(assignment);
    }



    // VIEW ASSIGNED STUDENTS


    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAssignedStudents(
            Long trainerId,
            Long assignmentId) {

        assignmentRepository
                .findByIdAndTrainerId(
                        assignmentId,
                        trainerId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assignment with id "
                                        + assignmentId
                                        + " not found"
                        )
                );

        return assignmentStudentRepository
                .findByAssignmentId(assignmentId)
                .stream()
                .map(AssignmentStudent::getStudentId)
                .map(studentId ->
                        userRepository.findById(studentId)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException(
                                                "Student with id "
                                                        + studentId
                                                        + " not found"
                                        )
                                )
                )
                .map(this::convertToUserResponse)
                .toList();
    }



    // VIEW ALL TRAINER SUBMISSIONS


    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponse> getTrainerSubmissions(
            Long trainerId) {

        getTrainer(trainerId);

        List<Long> assignmentIds =
                assignmentRepository
                        .findByTrainerIdOrderByDueDateAsc(
                                trainerId
                        )
                        .stream()
                        .map(Assignment::getId)
                        .toList();

        if (assignmentIds.isEmpty()) {
            return List.of();
        }

        return submissionRepository
                .findByAssignmentIdIn(assignmentIds)
                .stream()
                .map(this::convertToSubmissionResponse)
                .toList();
    }



    // VIEW SUBMISSIONS FOR ASSIGNMENT


    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponse> getAssignmentSubmissions(
            Long trainerId,
            Long assignmentId) {

        assignmentRepository
                .findByIdAndTrainerId(
                        assignmentId,
                        trainerId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assignment with id "
                                        + assignmentId
                                        + " not found"
                        )
                );

        return submissionRepository
                .findByAssignmentId(assignmentId)
                .stream()
                .map(this::convertToSubmissionResponse)
                .toList();
    }



    // EVALUATE SUBMISSION


    @Override
    public SubmissionResponse evaluateSubmission(
            Long trainerId,
            Long submissionId,
            EvaluationRequest request) {

        Submission submission =
                submissionRepository.findById(submissionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Submission with id "
                                                + submissionId
                                                + " not found"
                                )
                        );

        Assignment assignment =
                assignmentRepository
                        .findByIdAndTrainerId(
                                submission.getAssignmentId(),
                                trainerId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Assignment does not belong "
                                                + "to this trainer"
                                )
                        );

        if (submission.getStatus()
                == Submission.Status.EVALUATED) {

            throw new InvalidSubmissionException(
                    "Submission is already evaluated"
            );
        }

        if (request.getMarks()
                > assignment.getMaxMarks()) {

            throw new InvalidSubmissionException(
                    "Marks cannot exceed maxMarks "
                            + assignment.getMaxMarks()
            );
        }

        boolean studentAssigned =
                assignmentStudentRepository
                        .existsByAssignmentIdAndStudentId(
                                assignment.getId(),
                                submission.getStudentId()
                        );

        if (!studentAssigned) {

            throw new InvalidSubmissionException(
                    "Student is not assigned to this assignment"
            );
        }

        submission.setMarks(
                request.getMarks()
        );

        submission.setFeedback(
                request.getFeedback()
        );

        submission.setStatus(
                Submission.Status.EVALUATED
        );

        submission.setEvaluatedAt(
                LocalDateTime.now()
        );

        Submission savedSubmission =
                submissionRepository.save(submission);

        return convertToSubmissionResponse(
                savedSubmission
        );
    }



    // GET TRAINER


    private User getTrainer(Long trainerId) {

        User trainer =
                userRepository.findById(trainerId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Trainer with id "
                                                + trainerId
                                                + " not found"
                                )
                        );

        if (trainer.getRole()
                != User.Role.TRAINER) {

            throw new InvalidAssignmentException(
                    "User with id "
                            + trainerId
                            + " is not a trainer"
            );
        }

        if (!trainer.isActive()) {

            throw new InvalidAssignmentException(
                    "Trainer is inactive"
            );
        }

        return trainer;
    }



    // CONVERTERS


    private AssignmentResponse convertToAssignmentResponse(
            Assignment assignment) {

        return AssignmentResponse.builder()
                .id(assignment.getId())
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .assignedDate(assignment.getAssignedDate())
                .dueDate(assignment.getDueDate())
                .maxMarks(assignment.getMaxMarks())
                .status(assignment.getStatus())
                .trainerId(assignment.getTrainerId())
                .build();
    }


    private UserResponse convertToUserResponse(
            User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }


    private SubmissionResponse convertToSubmissionResponse(
            Submission submission) {

        return SubmissionResponse.builder()
                .id(submission.getId())
                .assignmentId(
                        submission.getAssignmentId()
                )
                .studentId(
                        submission.getStudentId()
                )
                .submissionText(
                        submission.getSubmissionText()
                )
                .submittedAt(
                        submission.getSubmittedAt()
                )
                .marks(
                        submission.getMarks()
                )
                .feedback(
                        submission.getFeedback()
                )
                .status(
                        submission.getStatus()
                )
                .evaluatedAt(
                        submission.getEvaluatedAt()
                )
                .build();
    }
}
