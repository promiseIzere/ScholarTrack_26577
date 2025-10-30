package com.scholartrack.controller;

import com.scholartrack.model.Assignment;
import com.scholartrack.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(assignmentService.findAll(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> createAssignment(@RequestBody Assignment assignment) {
        ResponseEntity<?> response = assignmentService.create(assignment);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable UUID id, @RequestBody Assignment assignment) {
        return assignmentService.update(id, assignment)
                .map(updatedAssignment -> new ResponseEntity<>(updatedAssignment, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable String title) {
        assignmentService.delete(title);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/course/{courseCode}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourseCode(@PathVariable String courseCode) {
        return new ResponseEntity<>(assignmentService.findByCourseCode(courseCode), HttpStatus.OK);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Assignment>> getOverdueAssignments() {
        return new ResponseEntity<>(assignmentService.findOverdueAssignments(), HttpStatus.OK);
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
