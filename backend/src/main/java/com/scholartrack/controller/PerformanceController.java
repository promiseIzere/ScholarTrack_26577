package com.scholartrack.controller;

import com.scholartrack.model.Performance;
import com.scholartrack.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
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
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Performance> createPerformance(@RequestBody Performance performance) {
        return ResponseEntity.ok(performanceService.create(performance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Performance> updatePerformance(@PathVariable UUID id, @RequestBody Performance performance) {
        return performanceService.update(id, performance)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable UUID id) {
        performanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/submission")
    public ResponseEntity<Void> deletePerformanceByStudentAndAssignment(
            @RequestParam UUID studentId, 
            @RequestParam UUID assignmentId) {
        performanceService.deleteByStudentIdAndAssignmentId(studentId, assignmentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student/{studentId}/average")
    public ResponseEntity<BigDecimal> getAverageScoreByStudentId(@PathVariable UUID studentId) {
        return ResponseEntity.ok(performanceService.getAverageScoreByStudentId(studentId));
    }

    @GetMapping("/assignment/{assignmentId}/average")
    public ResponseEntity<BigDecimal> getAverageScoreByAssignmentId(@PathVariable UUID assignmentId) {
        return ResponseEntity.ok(performanceService.getAverageScoreByAssignmentId(assignmentId));
    }

    @GetMapping("/student/{studentId}/count")
    public ResponseEntity<Long> getPerformanceCountByStudentId(@PathVariable UUID studentId) {
        return ResponseEntity.ok(performanceService.getPerformanceCountByStudentId(studentId));
    }

    @GetMapping("/assignment/{assignmentId}/count")
    public ResponseEntity<Long> getPerformanceCountByAssignmentId(@PathVariable UUID assignmentId) {
        return ResponseEntity.ok(performanceService.getPerformanceCountByAssignmentId(assignmentId));
    }
}