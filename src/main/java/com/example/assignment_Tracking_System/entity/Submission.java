package com.example.assignment_Tracking_System.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"assignment_id", "student_id"})})
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String submissionText;

    private LocalDateTime submittedAt;

    private Integer marks;

    private String feedback;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime evaluatedAt;

    public enum Status {
        PENDING,
        SUBMITTED,
        EVALUATED,
        LATE
    }
}