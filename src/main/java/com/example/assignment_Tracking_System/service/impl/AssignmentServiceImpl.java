package com.example.assignment_Tracking_System.service.impl;

import com.example.assignment_Tracking_System.dto.AssignmentResponse;
import com.example.assignment_Tracking_System.entity.Assignment;
import com.example.assignment_Tracking_System.entity.AssignmentStudent;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.exception.ResourceNotFoundException;
import com.example.assignment_Tracking_System.repository.AssignmentRepository;
import com.example.assignment_Tracking_System.repository.AssignmentStudentRepository;
import com.example.assignment_Tracking_System.repository.UserRepository;
import com.example.assignment_Tracking_System.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService{

    private final UserRepository userRepository;
    private final AssignmentStudentRepository assignmentStudentRepository;
    private final AssignmentRepository assignmentRepository;


    @Override
    @Transactional(readOnly = true)
    public List<AssignmentResponse> getStudentAssignments(
            Long studentId) {

        // Check student exists
        userRepository.findByIdAndRole(
                studentId,
                User.Role.STUDENT
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Student with id "
                                + studentId
                                + " not found"
                )
        );

        // Get assignments assigned to student
        List<AssignmentStudent> assignedAssignments =
                assignmentStudentRepository
                        .findByStudentId(studentId);

        // If no assignment is assigned
        if (assignedAssignments.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No assignments found for student with id "
                            + studentId
            );
        }
        return assignedAssignments.stream()
                .map(assignmentStudent ->
                        assignmentRepository
                                .findById(
                                        assignmentStudent.getAssignmentId()
                                )
                                .orElseThrow(() ->
                                        new ResourceNotFoundException(
                                                "Assignment with id "
                                                        + assignmentStudent.getAssignmentId()
                                                        + " not found"
                                        )
                                )
                )
                .map(this::convertToAssignmentResponse)
                .toList();

    }
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
}
