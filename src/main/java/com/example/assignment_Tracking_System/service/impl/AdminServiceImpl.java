package com.example.assignment_Tracking_System.service.impl;
//
//import com.example.assignment_Tracking_System.dto.*;
//import com.example.assignment_Tracking_System.entity.Assignment;
//import com.example.assignment_Tracking_System.entity.AssignmentStudent;
//import com.example.assignment_Tracking_System.entity.Submission;
//import com.example.assignment_Tracking_System.entity.User;
//import com.example.assignment_Tracking_System.exception.DuplicateResourceException;
//import com.example.assignment_Tracking_System.exception.InvalidAssignmentException;
//import com.example.assignment_Tracking_System.exception.ResourceNotFoundException;
//import com.example.assignment_Tracking_System.repository.AssignmentRepository;
//import com.example.assignment_Tracking_System.repository.AssignmentStudentRepository;
//import com.example.assignment_Tracking_System.repository.SubmissionRepository;
//import com.example.assignment_Tracking_System.repository.UserRepository;
//import com.example.assignment_Tracking_System.service.AdminService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class AdminServiceImpl implements AdminService {
//
//    private final UserRepository userRepository;
//    private final AssignmentRepository assignmentRepository;
//    private final AssignmentStudentRepository assignmentStudentRepository;
//    private final SubmissionRepository submissionRepository;
//
//
//
//    // CREATE TRAINER
//
//    @Override
//    public UserResponse createTrainer(
//            UserRequest request) {
//
//        if (userRepository.existsByEmail(request.getEmail())) {
//            throw new DuplicateResourceException(
//                    "Email already exists: "
//                            + request.getEmail()
//            );
//        }
//
//        User trainer = User.builder()
//                .name(request.getName())
//                .email(request.getEmail())
//                .phone(request.getPhone())
//                .role(User.Role.TRAINER)
//                .active(true)
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        User savedTrainer =
//                userRepository.save(trainer);
//
//        return convertToUserResponse(savedTrainer);
//    }
//
//
//    // GET ALL TRAINERS
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<UserResponse> getAllTrainers() {
//
//        return userRepository
//                .findByRole(User.Role.TRAINER)
//                .stream()
//                .map(this::convertToUserResponse)
//                .toList();
//    }
//
//
//    // GET TRAINER
//
//    @Override
//    @Transactional(readOnly = true)
//    public UserResponse getTrainer(
//            Long trainerId) {
//
//        User trainer =
//                userRepository.findById(trainerId)
//                        .orElseThrow(() ->
//                                new ResourceNotFoundException(
//                                        "Trainer with id "
//                                                + trainerId
//                                                + " not found"
//                                )
//                        );
//
//        validateTrainer(trainer);
//
//        return convertToUserResponse(trainer);
//    }
//
//
//    // UPDATE TRAINER
//
//    @Override
//    public UserResponse updateTrainer(
//            Long trainerId,
//            UserRequest request) {
//
//        User trainer =
//                userRepository.findById(trainerId)
//                        .orElseThrow(() ->
//                                new ResourceNotFoundException(
//                                        "Trainer with id "
//                                                + trainerId
//                                                + " not found"
//                                )
//                        );
//
//        validateTrainer(trainer);
//
//        userRepository.findByEmail(request.getEmail())
//                .ifPresent(existingUser -> {
//
//                    if (!existingUser.getId()
//                            .equals(trainerId)) {
//
//                        throw new DuplicateResourceException(
//                                "Email already exists: "
//                                        + request.getEmail()
//                        );
//                    }
//                });
//
//        trainer.setName(request.getName());
//        trainer.setEmail(request.getEmail());
//        trainer.setPhone(request.getPhone());
//        trainer.setUpdatedAt(LocalDateTime.now());
//
//        return convertToUserResponse(
//                userRepository.save(trainer)
//        );
//    }
//
//
//    // DELETE TRAINER
//
//    @Override
//    public void deleteTrainer(
//            Long trainerId) {
//
//        User trainer =
//                userRepository.findById(trainerId)
//                        .orElseThrow(() ->
//                                new ResourceNotFoundException(
//                                        "Trainer with id "
//                                                + trainerId
//                                                + " not found"
//                                )
//                        );
//
//        validateTrainer(trainer);
//
//        trainer.setActive(false);
//        trainer.setUpdatedAt(LocalDateTime.now());
//
//        userRepository.save(trainer);
//    }
//
//
//
//    // CREATE ASSIGNMENT
//
//
//    @Override
//    public AssignmentResponse createAssignment(
//            AssignmentRequest request) {
//
//        if (request.getTrainerId() == null) {
//            throw new InvalidAssignmentException(
//                    "Trainer id is required"
//            );
//        }
//
//        User trainer =
//                userRepository.findById(
//                        request.getTrainerId()
//                ).orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "Trainer with id "
//                                        + request.getTrainerId()
//                                        + " not found"
//                        )
//                );
//
//        validateTrainer(trainer);
//
//        if (!trainer.isActive()) {
//            throw new InvalidAssignmentException(
//                    "Trainer is inactive"
//            );
//        }
//
//        Assignment assignment = Assignment.builder()
//                .title(request.getTitle())
//                .description(request.getDescription())
//                .assignedDate(LocalDate.now())
//                .dueDate(request.getDueDate())
//                .maxMarks(request.getMaxMarks())
//                .status(Assignment.Status.CREATED)
//                .trainerId(trainer.getId())
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        Assignment savedAssignment =
//                assignmentRepository.save(assignment);
//
//        return convertToAssignmentResponse(
//                savedAssignment
//        );
//    }
//
//
//    // GET ALL ASSIGNMENTS
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<AssignmentResponse> getAllAssignments() {
//
//        return assignmentRepository.findAll()
//                .stream()
//                .map(this::convertToAssignmentResponse)
//                .toList();
//    }
//
//
//    // GET ASSIGNMENT
//
//    @Override
//    @Transactional(readOnly = true)
//    public AssignmentResponse getAssignment(
//            Long assignmentId) {
//
//        Assignment assignment =
//                assignmentRepository.findById(assignmentId)
//                        .orElseThrow(() ->
//                                new ResourceNotFoundException(
//                                        "Assignment with id "
//                                                + assignmentId
//                                                + " not found"
//                                )
//                        );
//
//        return convertToAssignmentResponse(assignment);
//    }
//
//
//    //  UPDATE ASSIGNMENT
//
//
//    @Override
//    public AssignmentResponse updateAssignment(
//            Long assignmentId,
//            AssignmentRequest request) {
//
//        Assignment assignment =
//                assignmentRepository.findById(assignmentId)
//                        .orElseThrow(() ->
//                                new ResourceNotFoundException(
//                                        "Assignment with id "
//                                                + assignmentId
//                                                + " not found"
//                                )
//                        );
//
//        if (assignment.getStatus()
//                == Assignment.Status.CLOSED) {
//
//            throw new InvalidAssignmentException(
//                    "Closed assignment cannot be updated"
//            );
//        }
//
//        assignment.setTitle(request.getTitle());
//        assignment.setDescription(request.getDescription());
//        assignment.setDueDate(request.getDueDate());
//        assignment.setMaxMarks(request.getMaxMarks());
//        assignment.setUpdatedAt(LocalDateTime.now());
//
//
//        if (request.getTrainerId() != null
//                && !request.getTrainerId()
//                .equals(assignment.getTrainerId())) {
//
//            User trainer =
//                    userRepository.findById(
//                            request.getTrainerId()
//                    ).orElseThrow(() ->
//                            new ResourceNotFoundException(
//                                    "Trainer with id "
//                                            + request.getTrainerId()
//                                            + " not found"
//                            )
//                    );
//
//            validateTrainer(trainer);
//
//            if (!trainer.isActive()) {
//                throw new InvalidAssignmentException(
//                        "Trainer is inactive"
//                );
//            }
//
//            assignment.setTrainerId(
//                    trainer.getId()
//            );
//        }
//
//        return convertToAssignmentResponse(
//                assignmentRepository.save(assignment)
//        );
//    }
//
//
//
//    // DELETE ASSIGNMENT
//
//
//    @Override
//    public void deleteAssignment(
//            Long assignmentId) {
//
//        Assignment assignment =
//                assignmentRepository.findById(assignmentId)
//                        .orElseThrow(() ->
//                                new ResourceNotFoundException(
//                                        "Assignment with id "
//                                                + assignmentId
//                                                + " not found"
//                                )
//                        );
//
//        submissionRepository
//                .deleteByAssignmentId(assignmentId);
//
//
//        assignmentStudentRepository
//                .deleteByAssignmentId(assignmentId);
//
//
//        assignmentRepository.delete(assignment);
//    }
//
//
//
//    // ASSIGN ASSIGNMENT TO STUDENTS
//
//    @Override
//    public void assignAssignmentToStudents(
//            Long assignmentId,
//            StudentIdsRequest request) {
//
//        Assignment assignment =
//                assignmentRepository.findById(assignmentId)
//                        .orElseThrow(() ->
//                                new ResourceNotFoundException(
//                                        "Assignment with id "
//                                                + assignmentId
//                                                + " not found"
//                                )
//                        );
//
//        if (assignment.getStatus()
//                == Assignment.Status.CLOSED) {
//
//            throw new InvalidAssignmentException(
//                    "Closed assignment cannot be assigned"
//            );
//        }
//
//        for (Long studentId :
//                request.getStudentIds()) {
//
//            User student =
//                    userRepository.findById(studentId)
//                            .orElseThrow(() ->
//                                    new ResourceNotFoundException(
//                                            "Student with id "
//                                                    + studentId
//                                                    + " not found"
//                                    )
//                            );
//
//            if (student.getRole()
//                    != User.Role.STUDENT) {
//
//                throw new InvalidAssignmentException(
//                        "User with id "
//                                + studentId
//                                + " is not a student"
//                );
//            }
//
//            if (!student.isActive()) {
//
//                throw new InvalidAssignmentException(
//                        "Student with id "
//                                + studentId
//                                + " is inactive"
//                );
//            }
//
//            boolean alreadyAssigned =
//                    assignmentStudentRepository
//                            .existsByAssignmentIdAndStudentId(
//                                    assignmentId,
//                                    studentId
//                            );
//
//            if (!alreadyAssigned) {
//
//                AssignmentStudent assignmentStudent =
//                        AssignmentStudent.builder()
//                                .assignmentId(assignmentId)
//                                .studentId(studentId)
//                                .assignedAt(
//                                        LocalDateTime.now()
//                                )
//                                .build();
//
//                assignmentStudentRepository.save(
//                        assignmentStudent
//                );
//            }
//        }
//
//        assignment.setStatus(
//                Assignment.Status.ASSIGNED
//        );
//
//        assignment.setUpdatedAt(
//                LocalDateTime.now()
//        );
//
//        assignmentRepository.save(assignment);
//    }
//
//
//
//    // VIEW SUBMISSIONS
//
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<SubmissionResponse>
//    getAssignmentSubmissions(
//            Long assignmentId) {
//
//        assignmentRepository.findById(assignmentId)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "Assignment with id "
//                                        + assignmentId
//                                        + " not found"
//                        )
//                );
//
//        return submissionRepository
//                .findByAssignmentId(assignmentId)
//                .stream()
//                .map(this::convertToSubmissionResponse)
//                .toList();
//    }
//
//
//
//    // VALIDATION
//
//
//    private void validateTrainer(User user) {
//
//        if (user.getRole()
//                != User.Role.TRAINER) {
//
//            throw new ResourceNotFoundException(
//                    "Trainer not found"
//            );
//        }
//    }
//
//
//
//    // RESPONSE CONVERTERS
//
//
//    private UserResponse convertToUserResponse(
//            User user) {
//
//        return UserResponse.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .email(user.getEmail())
//                .phone(user.getPhone())
//                .role(user.getRole())
//                .active(user.isActive())
//                .build();
//    }
//
//
//    private AssignmentResponse convertToAssignmentResponse(
//            Assignment assignment) {
//
//        return AssignmentResponse.builder()
//                .id(assignment.getId())
//                .title(assignment.getTitle())
//                .description(assignment.getDescription())
//                .assignedDate(assignment.getAssignedDate())
//                .dueDate(assignment.getDueDate())
//                .maxMarks(assignment.getMaxMarks())
//                .status(assignment.getStatus())
//                .trainerId(assignment.getTrainerId())
//                .build();
//    }
//
//
//    private SubmissionResponse convertToSubmissionResponse(
//            Submission submission) {
//
//        return SubmissionResponse.builder()
//                .id(submission.getId())
//                .assignmentId(submission.getAssignmentId())
//                .studentId(submission.getStudentId())
//                .submissionText(submission.getSubmissionText())
//                .submittedAt(submission.getSubmittedAt())
//                .marks(submission.getMarks())
//                .feedback(submission.getFeedback())
//                .status(submission.getStatus())
//                .evaluatedAt(submission.getEvaluatedAt())
//                .build();
//    }
//
//}

import com.example.assignment_Tracking_System.dto.*;
import com.example.assignment_Tracking_System.entity.Assignment;
import com.example.assignment_Tracking_System.entity.AssignmentStudent;
import com.example.assignment_Tracking_System.entity.Submission;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.exception.DuplicateResourceException;
import com.example.assignment_Tracking_System.exception.InvalidAssignmentException;
import com.example.assignment_Tracking_System.exception.ResourceNotFoundException;
import com.example.assignment_Tracking_System.repository.AssignmentRepository;
import com.example.assignment_Tracking_System.repository.AssignmentStudentRepository;
import com.example.assignment_Tracking_System.repository.SubmissionRepository;
import com.example.assignment_Tracking_System.repository.UserRepository;
import com.example.assignment_Tracking_System.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentStudentRepository assignmentStudentRepository;
    private final SubmissionRepository submissionRepository;



    // CREATE TRAINER

    @Override
    public UserResponse createTrainer(
            UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists: "
                            + request.getEmail()
            );
        }

        User trainer = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(User.Role.TRAINER)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedTrainer =
                userRepository.save(trainer);

        return convertToUserResponse(savedTrainer);
    }


    // GET ALL TRAINERS

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllTrainers() {

        return userRepository
                .findByRole(User.Role.TRAINER)
                .stream()
                .map(this::convertToUserResponse)
                .toList();
    }


    // GET TRAINER

    @Override
    @Transactional(readOnly = true)
    public UserResponse getTrainer(
            Long trainerId) {

        User trainer =
                userRepository.findById(trainerId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Trainer with id "
                                                + trainerId
                                                + " not found"
                                )
                        );

        validateTrainer(trainer);

        return convertToUserResponse(trainer);
    }


    // UPDATE TRAINER

    @Override
    public UserResponse updateTrainer(
            Long trainerId,
            UserRequest request) {

        User trainer =
                userRepository.findById(trainerId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Trainer with id "
                                                + trainerId
                                                + " not found"
                                )
                        );

        validateTrainer(trainer);

        userRepository.findByEmail(request.getEmail())
                .ifPresent(existingUser -> {

                    if (!existingUser.getId()
                            .equals(trainerId)) {

                        throw new DuplicateResourceException(
                                "Email already exists: "
                                        + request.getEmail()
                        );
                    }
                });

        trainer.setName(request.getName());
        trainer.setEmail(request.getEmail());
        trainer.setPhone(request.getPhone());
        trainer.setUpdatedAt(LocalDateTime.now());

        return convertToUserResponse(
                userRepository.save(trainer)
        );
    }


    // DELETE TRAINER

    @Override
    public void deleteTrainer(
            Long trainerId) {

        User trainer =
                userRepository.findById(trainerId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Trainer with id "
                                                + trainerId
                                                + " not found"
                                )
                        );

        validateTrainer(trainer);

        trainer.setActive(false);
        trainer.setUpdatedAt(LocalDateTime.now());

        userRepository.save(trainer);
    }



    // CREATE ASSIGNMENT


    @Override
    public AssignmentResponse createAssignment(
            AssignmentRequest request) {

        if (request.getTrainerId() == null) {
            throw new InvalidAssignmentException(
                    "Trainer id is required"
            );
        }

        User trainer =
                userRepository.findById(
                        request.getTrainerId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trainer with id "
                                        + request.getTrainerId()
                                        + " not found"
                        )
                );

        validateTrainer(trainer);

        if (!trainer.isActive()) {
            throw new InvalidAssignmentException(
                    "Trainer is inactive"
            );
        }

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

        return convertToAssignmentResponse(
                savedAssignment
        );
    }


    // GET ALL ASSIGNMENTS

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentResponse> getAllAssignments() {

        return assignmentRepository.findAll()
                .stream()
                .map(this::convertToAssignmentResponse)
                .toList();
    }


    // GET ASSIGNMENT

    @Override
    @Transactional(readOnly = true)
    public AssignmentResponse getAssignment(
            Long assignmentId) {

        Assignment assignment =
                assignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Assignment with id "
                                                + assignmentId
                                                + " not found"
                                )
                        );

        return convertToAssignmentResponse(assignment);
    }


    //  UPDATE ASSIGNMENT


    @Override
    public AssignmentResponse updateAssignment(
            Long assignmentId,
            AssignmentRequest request) {

        Assignment assignment =
                assignmentRepository.findById(assignmentId)
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


        if (request.getTrainerId() != null
                && !request.getTrainerId()
                .equals(assignment.getTrainerId())) {

            User trainer =
                    userRepository.findById(
                            request.getTrainerId()
                    ).orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Trainer with id "
                                            + request.getTrainerId()
                                            + " not found"
                            )
                    );

            validateTrainer(trainer);

            if (!trainer.isActive()) {
                throw new InvalidAssignmentException(
                        "Trainer is inactive"
                );
            }

            assignment.setTrainerId(
                    trainer.getId()
            );
        }

        return convertToAssignmentResponse(
                assignmentRepository.save(assignment)
        );
    }



    // DELETE ASSIGNMENT


    @Override
    public void deleteAssignment(
            Long assignmentId) {

        Assignment assignment =
                assignmentRepository.findById(assignmentId)
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



    // ASSIGN ASSIGNMENT TO STUDENTS

    @Override
    public void assignAssignmentToStudents(
            Long assignmentId,
            StudentIdsRequest request) {

        Assignment assignment =
                assignmentRepository.findById(assignmentId)
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

        for (Long studentId :
                request.getStudentIds()) {

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

                assignmentStudentRepository.save(
                        assignmentStudent
                );
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



    // VIEW SUBMISSIONS


    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponse>
    getAssignmentSubmissions(
            Long assignmentId) {

        assignmentRepository.findById(assignmentId)
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



    // VALIDATION


    private void validateTrainer(User user) {

        if (user.getRole()
                != User.Role.TRAINER) {

            throw new ResourceNotFoundException(
                    "Trainer not found"
            );
        }
    }
    //    Students management
    @Override
    public UserResponse createStudent(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists: "
                            + request.getEmail()
            );
        }

        User student = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(User.Role.STUDENT)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedStudent = userRepository.save(student);

        return convertToUserResponse(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllStudents() {

        return userRepository
                .findByRole(User.Role.STUDENT)
                .stream()
                .map(this::convertToUserResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getStudent(Long studentId) {

        User student =
                userRepository.findById(studentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student with id "
                                                + studentId
                                                + " not found"
                                )
                        );

        if (student.getRole() != User.Role.STUDENT) {
            throw new ResourceNotFoundException(
                    "Student with id "
                            + studentId
                            + " not found"
            );
        }

        return convertToUserResponse(student);
    }

    @Override
    public UserResponse updateStudent(
            Long studentId,
            UserRequest request) {

        User student =
                userRepository.findById(studentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student with id "
                                                + studentId
                                                + " not found"
                                )
                        );

        if (student.getRole() != User.Role.STUDENT) {
            throw new ResourceNotFoundException(
                    "Student with id "
                            + studentId
                            + " not found"
            );
        }

        userRepository.findByEmail(request.getEmail())
                .ifPresent(existingUser -> {

                    if (!existingUser.getId()
                            .equals(studentId)) {

                        throw new DuplicateResourceException(
                                "Email already exists: "
                                        + request.getEmail()
                        );
                    }
                });

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setUpdatedAt(LocalDateTime.now());

        User updatedStudent =
                userRepository.save(student);

        return convertToUserResponse(updatedStudent);
    }
    @Override
    public void deleteStudent(Long studentId) {

        User student =
                userRepository.findById(studentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student with id "
                                                + studentId
                                                + " not found"
                                )
                        );

        if (student.getRole() != User.Role.STUDENT) {
            throw new ResourceNotFoundException(
                    "Student with id "
                            + studentId
                            + " not found"
            );
        }

        student.setActive(false);
        student.setUpdatedAt(LocalDateTime.now());

        userRepository.save(student);
    }


    // RESPONSE CONVERTERS


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