package com.example.assignment_Tracking_System.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private long totalAdmins;

    private long totalTrainers;

    private long totalStudents;

    private long totalAssignments;

    private long totalSubmissions;

    private long evaluatedSubmissions;

    private long pendingSubmissions;
}
