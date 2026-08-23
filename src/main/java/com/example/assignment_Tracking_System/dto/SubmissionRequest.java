package com.example.assignment_Tracking_System.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionRequest {

    @NotBlank(message = "Submission text is required")
    private String submissionText;
}