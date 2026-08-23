package com.example.assignment_Tracking_System.repository;


import com.example.assignment_Tracking_System.entity.AssignmentStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentStudentRepository
        extends JpaRepository<AssignmentStudent, Long> {

    boolean existsByAssignmentIdAndStudentId(
            Long assignmentId,
            Long studentId
    );

    List<AssignmentStudent> findByAssignmentId(
            Long assignmentId
    );

    void deleteByAssignmentId(Long assignmentId);


}