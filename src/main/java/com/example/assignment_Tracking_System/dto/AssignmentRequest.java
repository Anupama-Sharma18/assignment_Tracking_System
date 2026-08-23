package com.example.assignment_Tracking_System.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be a future date")
    private LocalDate dueDate;

    @NotNull(message = "Maximum marks are required")
    @Min(value = 1, message = "Maximum marks must be greater than 0")
    private Integer maxMarks;


    private Long trainerId;
}
