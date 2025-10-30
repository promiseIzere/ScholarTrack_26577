package com.scholartrack.controller;

import com.scholartrack.model.Performance;
import com.scholartrack.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/performances")
@CrossOrigin(origins = "*")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @GetMapping
    public ResponseEntity<List<Performance>> getAllPerformances() {
        return ResponseEntity.ok(performanceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Performance> getPerformanceById(@PathVariable UUID id) {
        return performanceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Performance>> getPerformancesByStudentId(@PathVariable UUID studentId) {
        return ResponseEntity.ok(performanceService.findByStudentId(studentId));
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<Performance>> getPerformancesByAssignmentId(@PathVariable UUID assignmentId) {
        return ResponseEntity.ok(performanceService.findByAssignmentId(assignmentId));
    }

    @GetMapping("/submission")
    public ResponseEntity<Performance> getPerformanceByStudentAndAssignment(
            @RequestParam UUID studentId, 
            @RequestParam UUID assignmentId) {
        return performanceService.findByStudentIdAndAssignmentId(studentId, assignmentId)
                .map(performance -> new ResponseEntity<>(performance, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createPerformance(@RequestBody Performance performance) {
        ResponseEntity<?> response = performanceService.createPerformance(performance);
        Performance saved = (Performance) response.getBody();

        if (saved == null) {
            return ResponseEntity.badRequest().body("Could not save performance.");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("id", saved.getId());
        data.put("student", saved.getStudent().getStudentNumber());
        data.put("assignment", saved.getAssignment().getId());
        data.put("score", saved.getScore());
        data.put("feedback", saved.getFeedback());
        data.put("submittedAt", saved.getSubmittedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Performance> updatePerformance(@PathVariable UUID id, @RequestBody Performance performance) {
        return performanceService.update(id, performance)
                .map(updatedPerformance -> new ResponseEntity<>(updatedPerformance, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable UUID id) {
        performanceService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/submission")
    public ResponseEntity<Void> deletePerformanceByStudentAndAssignment(
            @RequestParam UUID studentId, 
            @RequestParam UUID assignmentId) {
        performanceService.deleteByStudentIdAndAssignmentId(studentId, assignmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/student/{studentId}/average")
    public ResponseEntity<BigDecimal> getAverageScoreByStudentId(@PathVariable UUID studentId) {
        return new ResponseEntity<>(performanceService.getAverageScoreByStudentId(studentId), HttpStatus.OK);
    }

    @GetMapping("/assignment/{assignmentId}/average")
    public ResponseEntity<BigDecimal> getAverageScoreByAssignmentId(@PathVariable UUID assignmentId) {
        return new ResponseEntity<>(performanceService.getAverageScoreByAssignmentId(assignmentId), HttpStatus.OK);
    }

    @GetMapping("/assignment/{assignmentId}/count")
    public ResponseEntity<Long> getPerformanceCountByAssignmentId(@PathVariable UUID assignmentId) {
        return new ResponseEntity<>(performanceService.getPerformanceCountByAssignmentId(assignmentId), HttpStatus.OK);
    }
}