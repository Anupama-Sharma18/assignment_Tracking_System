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

    @NotNull
    @Min(0)
    private Integer marks;

    @NotBlank
    private String feedback;
}
