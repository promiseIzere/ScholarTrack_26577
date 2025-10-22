package com.scholartrack.controller;

import com.scholartrack.model.Assignment;
import com.scholartrack.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        return ResponseEntity.ok(assignmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable UUID id) {
        return assignmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        return ResponseEntity.ok(assignmentService.create(assignment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable UUID id, @RequestBody Assignment assignment) {
        return assignmentService.update(id, assignment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable UUID id) {
        assignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourseId(@PathVariable UUID courseId) {
        return ResponseEntity.ok(assignmentService.findByCourseId(courseId));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Assignment>> getOverdueAssignments() {
        return ResponseEntity.ok(assignmentService.findOverdueAssignments());
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Assignment>> getAssignmentsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(assignmentService.findByDueDateBetween(
                LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Assignment>> searchAssignmentsByTitle(@RequestParam String title) {
        return ResponseEntity.ok(assignmentService.findByTitleContaining(title));
    }
}
