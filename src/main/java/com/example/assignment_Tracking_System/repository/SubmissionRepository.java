package com.example.assignment_Tracking_System.repository;

import com.example.assignment_Tracking_System.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository
        extends JpaRepository<Submission, Long> {

    Optional<Submission> findByStudentIdAndAssignmentId(Long studentId, Long assignmentId);
    List<Submission> findAllByStudentId(Long studentId);

    List<Submission> findByAssignmentId(Long assignmentId);

    List<Submission> findByAssignmentIdIn(List<Long> assignmentIds);

    void deleteByAssignmentId(Long assignmentId);
}
