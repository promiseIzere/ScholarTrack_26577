package com.scholartrack.controller;

import com.scholartrack.model.PerformanceReport;
import com.scholartrack.service.AnalyticsService;
import com.scholartrack.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;
    
    @Autowired
    private StudentService studentService;

    @GetMapping("/student/{studentId}/report")
    public ResponseEntity<PerformanceReport> getStudentReport(@PathVariable UUID studentId) {
        return studentService.findById(studentId)
                .map(student -> ResponseEntity.ok(analyticsService.buildStudentReport(student)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/student/{studentId}/report/date-range")
    public ResponseEntity<PerformanceReport> getStudentReportWithDateRange(
            @PathVariable UUID studentId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return studentService.findById(studentId)
                .map(student -> ResponseEntity.ok(analyticsService.buildStudentReport(
                        student, LocalDate.parse(startDate), LocalDate.parse(endDate))))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/student/{studentId}/stats")
    public ResponseEntity<Map<String, Object>> getStudentStats(@PathVariable UUID studentId) {
        Map<String, Object> stats = analyticsService.getStudentAnalytics(studentId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/course/{courseId}/stats")
    public ResponseEntity<Map<String, Object>> getCourseStats(@PathVariable UUID courseId) {
        Map<String, Object> stats = analyticsService.getCourseAnalytics(courseId);
        return ResponseEntity.ok(stats);
    }
}
