package com.example.assignment_Tracking_System.service.impl;

import com.example.assignment_Tracking_System.entity.Assignment;
import com.example.assignment_Tracking_System.exception.ResourceNotFoundException;
import com.example.assignment_Tracking_System.repository.AssignmentRepository;
import com.example.assignment_Tracking_System.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl{

//    private final AssignmentRepository assignmentRepository;
//
//    @Override
//    public List<Assignment> viewStudentAssignments(Long studentId){
//
//        List<Assignment> assignmentList = assignmentRepository.findByStudentId(studentId);
//        if (assignmentList.isEmpty()){
//            throw new ResourceNotFoundException("Student with id "+ studentId + " does not have any assignment yet!");
//        }
//        return assignmentList;
//    }
}
