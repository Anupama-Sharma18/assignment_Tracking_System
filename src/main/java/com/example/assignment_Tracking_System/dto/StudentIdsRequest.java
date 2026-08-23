package com.example.assignment_Tracking_System.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentIdsRequest {

    @NotEmpty(message = "Student ids are required")
    private List<@NotNull(message = "Student id cannot be null") Long> studentIds;
}

