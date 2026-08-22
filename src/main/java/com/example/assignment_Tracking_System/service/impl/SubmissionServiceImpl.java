package com.example.assignment_Tracking_System.service.impl;

import com.example.assignment_Tracking_System.dto.SubmissionRequest;
import com.example.assignment_Tracking_System.dto.UserRequest;
import com.example.assignment_Tracking_System.entity.Submission;
import com.example.assignment_Tracking_System.entity.User;
import com.example.assignment_Tracking_System.exception.ResourceNotFoundException;
import com.example.assignment_Tracking_System.repository.SubmissionRepository;
import com.example.assignment_Tracking_System.repository.UserRepository;
import com.example.assignment_Tracking_System.service.StudentService;
import com.example.assignment_Tracking_System.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;

    @Override
    public Submission submitAssignment(Long studentId, Long assignmentId, SubmissionRequest submissionRequest){

        Submission submission = Submission.builder()
                .assignmentId(assignmentId)
                .studentId(studentId)
                .submissionText(submissionRequest.getSubmissionText())
                .submittedAt(LocalDateTime.now())
                .status(Submission.Status.SUBMITTED)
                .build();
        return submissionRepository.save(submission);
    }

    @Override
    public Submission viewSubmission(Long studentId, Long assignmentId){
        return submissionRepository
                .findByStudentIdAndAssignmentId(studentId, assignmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Submission not found for student id "
                        + studentId
                        + " and assignment id "
                        + assignmentId)
                );
    }

    @Override
    public Submission updateSubmission(Long studentId, Long assignmentId, SubmissionRequest submissionRequest){

        Submission submission = viewSubmission(studentId,assignmentId);

//        if the submitted assignment status is evaluated so you can't change/update the submission
        if (submission.getStatus() == Submission.Status.EVALUATED) {
            throw new IllegalStateException(
                    "Submission cannot be modified after evaluation"
            );
        }

        submission.setSubmissionText(submissionRequest.getSubmissionText());
        return submissionRepository.save(submission);
    }

    @Override
    public List<Submission> viewAllSubmissionByStudentId(Long studentId){
        List<Submission> submissionList = submissionRepository.findAllByStudentId(studentId);
        if (submissionList.isEmpty()){
            throw new ResourceNotFoundException("No Submission found for student id "+ studentId);
        }
        return submissionList;

    }

    @Override
    public Submission viewResult(Long studentId, Long submissionId){
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Submission with id " + submissionId + " not found"
                        )
                );
        if (!submission.getStudentId().equals(studentId)){
            throw new ResourceNotFoundException(
                    "Submission does not belong to student with id "+ studentId
            );
        }
        if (submission.getStatus() != Submission.Status.EVALUATED) {
            throw new ResourceNotFoundException(
                    "Result is not evaluated yet"
            );
        }
        return submission;
    }





}
