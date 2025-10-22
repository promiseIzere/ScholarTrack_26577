package com.scholartrack.service;

import com.scholartrack.model.Assignment;
import com.scholartrack.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public Assignment create(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    @Transactional(readOnly = true)
    public List<Assignment> findAll() {
        return assignmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Assignment> findById(UUID id) {
        return assignmentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Assignment> findByCourseId(UUID courseId) {
        return assignmentRepository.findByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public List<Assignment> findOverdueAssignments() {
        return assignmentRepository.findOverdueAssignments(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<Assignment> findByDueDateBetween(LocalDate startDate, LocalDate endDate) {
        return assignmentRepository.findByDueDateBetween(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Assignment> findByTitleContaining(String title) {
        return assignmentRepository.findByTitleContaining(title);
    }

    public Optional<Assignment> update(UUID id, Assignment update) {
        return assignmentRepository.findById(id).map(existing -> {
            existing.setCourse(update.getCourse());
            existing.setTitle(update.getTitle());
            existing.setDescription(update.getDescription());
            existing.setDueDate(update.getDueDate());
            existing.setMaxScore(update.getMaxScore());
            return existing;
        });
    }

    public void delete(UUID id) {
        assignmentRepository.deleteById(id);
    }
}
