package com.scholartrack.service;

import com.scholartrack.model.Performance;
import com.scholartrack.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Performance create(Performance performance) {
        return performanceRepository.save(performance);
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

    @Transactional(readOnly = true)
    public Long getPerformanceCountByStudentId(UUID studentId) {
        return performanceRepository.countByStudentId(studentId);
    }

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
