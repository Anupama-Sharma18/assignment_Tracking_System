package com.example.assignment_Tracking_System.repository;

import com.example.assignment_Tracking_System.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    Optional<Submission> findByStudentIdAndAssignmentId(Long studentId, Long assignmentId);
    List<Submission> findAllByStudentId(Long studentId);

}
