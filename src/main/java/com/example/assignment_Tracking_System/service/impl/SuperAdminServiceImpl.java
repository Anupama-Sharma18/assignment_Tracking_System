package com.example.assignment_Tracking_System.service.impl;

import com.example.assignment_Tracking_System.dto.DashboardResponse;
import com.example.assignment_Tracking_System.dto.SubmissionResponse;
import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.dto.UserResponse;
import com.example.assignment_Tracking_System.entity.Submission;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.exception.ResourceNotFoundException;
import com.example.assignment_Tracking_System.repository.AssignmentRepository;
import com.example.assignment_Tracking_System.repository.SubmissionRepository;
import com.example.assignment_Tracking_System.repository.UserRepository;
import com.example.assignment_Tracking_System.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;

    @Override
    public UserResponse createAdmin(UserRequest userRequest) {

        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .role(User.Role.ADMIN)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .role(savedUser.getRole())
                .active(savedUser.isActive())
                .build();
    }

    @Override
    public List<UserResponse> getAllAdmins(){

        List<User> admins = userRepository.findByRole(User.Role.ADMIN);

        if (admins.isEmpty()) {
            throw new ResourceNotFoundException("No admins found");
        }
        return admins
                .stream()
                .map(this::convertToUserResponse)
                .toList();
    }

    @Override
    public UserResponse getAdminById(Long id) {

        User admin = userRepository
                .findByIdAndRole(id, User.Role.ADMIN)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin with id " + id + " not found"
                        )
                );

        return convertToUserResponse(admin);
    }

    @Override
    public UserResponse updateAdmin(Long id, UserRequest userRequest){
//first it will check if the id is of admin or not after that it will update admin
        User admin = userRepository
                .findByIdAndRole(id, User.Role.ADMIN)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin with id " + id + " not found"
                        )
                );

        admin.setName(userRequest.getName());
        admin.setEmail(userRequest.getEmail());
        admin.setPhone(userRequest.getPhone());
        admin.setUpdatedAt(LocalDateTime.now());

        User updatedAdmin = userRepository.save(admin);

        return convertToUserResponse(updatedAdmin);

    }

    public UserResponse deleteAdmin(Long id){
        User admin = userRepository
                .findByIdAndRole(id, User.Role.ADMIN)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin with id " + id + " not found"
                        )
                );

        userRepository.deleteById(id);
        return convertToUserResponse(admin);

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllStudents() {

        return userRepository
                .findByRoleAndActiveTrue(User.Role.STUDENT)
                .stream()
                .map(this::convertToUserResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponse> getAllSubmissions() {

        return submissionRepository.findAll()
                .stream()
                .map(submission -> SubmissionResponse.builder()
                        .id(submission.getId())
                        .assignmentId(submission.getAssignmentId())
                        .studentId(submission.getStudentId())
                        .submissionText(submission.getSubmissionText())
                        .submittedAt(submission.getSubmittedAt())
                        .marks(submission.getMarks())
                        .feedback(submission.getFeedback())
                        .status(submission.getStatus())
                        .evaluatedAt(submission.getEvaluatedAt())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboardStatistics() {

        long totalAdmins =
                userRepository.countByRole(User.Role.ADMIN);

        long totalTrainers =
                userRepository.countByRole(User.Role.TRAINER);

        long totalStudents =
                userRepository.countByRole(User.Role.STUDENT);

        long totalAssignments =
                assignmentRepository.count();

        long totalSubmissions =
                submissionRepository.count();

        long evaluatedSubmissions =
                submissionRepository.countByStatus(
                        Submission.Status.EVALUATED
                );

        long pendingSubmissions =
                submissionRepository.countByStatus(
                        Submission.Status.PENDING
                );

        return DashboardResponse.builder()
                .totalAdmins(totalAdmins)
                .totalTrainers(totalTrainers)
                .totalStudents(totalStudents)
                .totalAssignments(totalAssignments)
                .totalSubmissions(totalSubmissions)
                .evaluatedSubmissions(evaluatedSubmissions)
                .pendingSubmissions(pendingSubmissions)
                .build();
    }



    private UserResponse convertToUserResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }


}