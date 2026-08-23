package com.example.assignment_Tracking_System.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationRequest {

    @NotNull(message = "Marks are required")
    @Min(value = 0, message = "Marks cannot be negative")
    private Integer marks;

    @NotBlank(message = "Feedback is required")
    private String feedback;
}
