package com.example.assignment_Tracking_System.repository;


import com.example.assignment_Tracking_System.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository
        extends JpaRepository<Assignment, Long> {

    List<Assignment> findByTrainer_IdOrderByDueDateAsc(Long trainerId);

    Optional<Assignment> findByIdAndTrainer_Id(
            Long assignmentId,
            Long trainerId
    );
}
