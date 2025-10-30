package com.scholartrack.service;

import com.scholartrack.model.Assignment;
import com.scholartrack.model.Performance;
import com.scholartrack.model.Student;
import com.scholartrack.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private com.scholartrack.repository.StudentRepository studentRepository;

    @Autowired
    private com.scholartrack.repository.AssignmentRepository assignmentRepository;

    public ResponseEntity<?> createPerformance(Performance performance) {
        String studentNumber = performance.getStudentNumber();
        UUID assignmentId = performance.getAssignmentId();

        if (studentNumber == null || studentNumber.trim().isEmpty() || assignmentId == null) {
            return new ResponseEntity<>("studentNumber and assignmentId are required", HttpStatus.BAD_REQUEST);
        }
        Student student = studentRepository.findByStudentNumber(studentNumber).orElse(null);
        if (student == null) {
            return new ResponseEntity<>("Student number not found: " + studentNumber, HttpStatus.BAD_REQUEST);
        }

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElse(null);
        if (assignment == null) {
            return new ResponseEntity<>("Assignment not found: " + assignmentId, HttpStatus.BAD_REQUEST);
        }
        boolean exists = performanceRepository.existsByStudent_StudentNumberAndAssignment_Id(studentNumber, assignmentId);
        if (exists) {
            return new ResponseEntity<>("Performance already recorded for this student and assignment", HttpStatus.CONFLICT);
        }
        performance.setStudent(student);
        performance.setAssignment(assignment);
        Performance saved = performanceRepository.save(performance);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    public List<Performance> findAll() {
        return performanceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Performance> findById(UUID id) {
        return performanceRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Performance> findByStudentId(UUID studentId) {
        return performanceRepository.findByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<Performance> findByAssignmentId(UUID assignmentId) {
        return performanceRepository.findByAssignmentId(assignmentId);
    }

    @Transactional(readOnly = true)
    public Optional<Performance> findByStudentIdAndAssignmentId(UUID studentId, UUID assignmentId) {
        return performanceRepository.findByStudentIdAndAssignmentId(studentId, assignmentId);
    }

    @Transactional(readOnly = true)
    public BigDecimal getAverageScoreByStudentId(UUID studentId) {
        return performanceRepository.findAverageScoreByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public BigDecimal getAverageScoreByAssignmentId(UUID assignmentId) {
        return performanceRepository.findAverageScoreByAssignmentId(assignmentId);
    }

    // @Transactional(readOnly = true)
    // public Long getPerformanceCountByStudentId(UUID studentId) {
    //     return performanceRepository.countByStudentId(studentId);
    // }

    @Transactional(readOnly = true)
    public Long getPerformanceCountByAssignmentId(UUID assignmentId) {
        return performanceRepository.countByAssignmentId(assignmentId);
    }

    public Optional<Performance> update(UUID id, Performance update) {
        return performanceRepository.findById(id).map(existing -> {
            existing.setStudent(update.getStudent());
            existing.setAssignment(update.getAssignment());
            existing.setScore(update.getScore());
            existing.setFeedback(update.getFeedback());
            existing.setSubmittedAt(update.getSubmittedAt());
            return existing;
        });
    }

    public void delete(UUID id) {
        performanceRepository.deleteById(id);
    }

    public void deleteByStudentIdAndAssignmentId(UUID studentId, UUID assignmentId) {
        performanceRepository.findByStudentIdAndAssignmentId(studentId, assignmentId)
                .ifPresent(performanceRepository::delete);
    }
}
