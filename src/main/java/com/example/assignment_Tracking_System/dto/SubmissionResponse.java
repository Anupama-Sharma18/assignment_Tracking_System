package com.example.assignment_Tracking_System.dto;


import com.example.assignment_Tracking_System.entity.Submission.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmissionResponse {


    private Long id;

    private Long assignmentId;

    private Long studentId;

    private String submissionText;

    private LocalDateTime submittedAt;

    private Integer marks;

    private String feedback;

    private Status status;

    private LocalDateTime evaluatedAt;
}
