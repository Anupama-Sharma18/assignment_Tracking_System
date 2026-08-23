package com.example.assignment_Tracking_System.repository;

import com.example.assignment_Tracking_System.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByTrainerIdOrderByDueDateAsc(Long trainerId);

    Optional<Assignment> findByIdAndTrainerId(
            Long assignmentId,
            Long trainerId
    );
}
