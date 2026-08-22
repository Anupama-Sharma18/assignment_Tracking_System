package com.example.assignment_Tracking_System.repository;


import com.example.assignment_Tracking_System.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository
        extends JpaRepository<Submission, Long> {

    List<Submission> findByAssignmentId(Long assignmentId);

    List<Submission> findByAssignmentIdIn(List<Long> assignmentIds);

    void deleteByAssignmentId(Long assignmentId);
}
