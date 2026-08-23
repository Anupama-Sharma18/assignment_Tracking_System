package com.example.assignment_Tracking_System.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate assignedDate;

    private LocalDate dueDate;

    private Integer maxMarks;

    @Enumerated(EnumType.STRING)
    private Status status;

    // Many assignments can be created by one trainer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public enum Status {
        CREATED,
        ASSIGNED,
        CLOSED
    }
}