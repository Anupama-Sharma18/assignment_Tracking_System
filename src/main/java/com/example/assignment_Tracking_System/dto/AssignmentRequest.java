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

    @NotBlank
    @Size(min = 5, max = 100)
    private String title;

    @NotBlank
    private String description;

    @NotNull
    @Future
    private LocalDate dueDate;

    @NotNull
    @Min(1)
    private Integer maxMarks;

    private Long trainerId;
}
