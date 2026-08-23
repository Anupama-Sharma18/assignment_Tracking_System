package com.example.assignment_Tracking_System.repository;


import com.example.assignment_Tracking_System.entity.AssignmentStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentStudentRepository
        extends JpaRepository<AssignmentStudent, Long> {

    boolean existsByAssignment_IdAndStudent_Id(
            Long assignmentId,
            Long studentId
    );

    List<AssignmentStudent> findByAssignment_Id(
            Long assignmentId
    );

    List<AssignmentStudent> findByStudent_Id(
            Long studentId
    );

    void deleteByAssignment_Id(
            Long assignmentId
    );
}