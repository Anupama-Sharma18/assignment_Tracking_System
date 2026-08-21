package com.example.assignment_Tracking_System.dto;


import com.example.assignment_Tracking_System.entity.Assignment.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentResponse {

    private Long id;

    private String title;

    private String description;

    private LocalDate assignedDate;

    private LocalDate dueDate;

    private Integer maxMarks;

    private Status status;

    private Long trainerId;
}
