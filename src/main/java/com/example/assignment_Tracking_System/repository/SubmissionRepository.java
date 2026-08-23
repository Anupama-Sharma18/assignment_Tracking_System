package com.example.assignment_Tracking_System.repository;

import com.example.assignment_Tracking_System.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    Optional<Submission> findByStudent_IdAndAssignment_Id(
            Long studentId,
            Long assignmentId
    );

    List<Submission> findAllByStudent_Id(
            Long studentId
    );

    List<Submission> findByAssignment_Id(
            Long assignmentId
    );

    List<Submission> findByAssignment_IdIn(
            List<Long> assignmentIds
    );

    void deleteByAssignment_Id(
            Long assignmentId
    );

    long countByStatus(
            Submission.Status status
    );


}
