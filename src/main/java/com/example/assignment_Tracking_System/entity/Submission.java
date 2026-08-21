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
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long assignmentId;

    private Long studentId;

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
