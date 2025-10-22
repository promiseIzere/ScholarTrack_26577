package com.scholartrack.service;

import com.scholartrack.model.*;
import com.scholartrack.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class AnalyticsService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private PerformanceRepository performanceRepository;
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public PerformanceReport buildStudentReport(Student student, LocalDate start, LocalDate end) {
        PerformanceReport report = new PerformanceReport();
        report.setStudentId(student.getId());

        // Attendance rate
        List<Attendance> records = attendanceRepository.findByStudentIdAndAttendanceDateBetween(student.getId(), start, end);
        long total = records.size();
        long present = records.stream().filter(a -> "Present".equals(a.getStatus())).count();
        double rate = total == 0 ? 0.0 : (double) present / (double) total;
        report.setAttendanceRate(rate);

        // Average assignment score from Performance records
        List<Performance> performances = performanceRepository.findByStudentId(student.getId());
        Double avgScore = performances.stream()
                .filter(p -> p.getScore() != null)
                .mapToDouble(p -> p.getScore().doubleValue())
                .average()
                .orElse(Double.NaN);
        report.setAverageAssignmentScore(Double.isNaN(avgScore) ? null : avgScore);

        // Grade trend by term (using course enrollment dates as term indicators)
        List<StudentCourse> enrollments = enrollmentRepository.findByStudentId(student.getId());
        Map<String, Double> byTerm = new TreeMap<>();
        Map<String, List<Double>> bucket = new HashMap<>();
        
        for (StudentCourse enrollment : enrollments) {
            String term = enrollment.getEnrollmentDate().getYear() + "-Sem" + 
                         (enrollment.getEnrollmentDate().getMonthValue() <= 6 ? "1" : "2");
            
            // Get performances for this course
            List<Performance> coursePerformances = performanceRepository.findByStudentIdAndCourseId(
                    student.getId(), enrollment.getCourse().getId());
            
            for (Performance p : coursePerformances) {
                if (p.getScore() != null) {
                    bucket.computeIfAbsent(term, t -> new ArrayList<>()).add(p.getScore().doubleValue());
                }
            }
        }
        
        bucket.forEach((term, scores) -> 
            byTerm.put(term, scores.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN)));
        report.setGradeTrendByTerm(byTerm);

        // Simple predictive risk based on attendance
        String risk = rate >= 0.9 ? "LOW" : rate >= 0.75 ? "MEDIUM" : "HIGH";
        report.setPredictedRiskLevel(risk);

        return report;
    }
    
    public PerformanceReport buildStudentReport(Student student) {
        return buildStudentReport(student, LocalDate.now().minusMonths(6), LocalDate.now());
    }
    
    public Map<String, Object> getCourseAnalytics(UUID courseId) {
        Map<String, Object> analytics = new HashMap<>();
        
        // Get all performances for this course
        List<Performance> performances = performanceRepository.findByAssignmentId(courseId);
        
        if (performances.isEmpty()) {
            analytics.put("averageScore", 0.0);
            analytics.put("totalSubmissions", 0);
            analytics.put("completionRate", 0.0);
            return analytics;
        }
        
        // Calculate average score
        Double avgScore = performances.stream()
                .filter(p -> p.getScore() != null)
                .mapToDouble(p -> p.getScore().doubleValue())
                .average()
                .orElse(0.0);
        
        // Get total students enrolled in course
        Long totalEnrolled = enrollmentRepository.countByCourseId(courseId);
        
        // Calculate completion rate
        double completionRate = totalEnrolled > 0 ? (double) performances.size() / totalEnrolled : 0.0;
        
        analytics.put("averageScore", avgScore);
        analytics.put("totalSubmissions", performances.size());
        analytics.put("completionRate", completionRate);
        analytics.put("totalEnrolled", totalEnrolled);
        
        return analytics;
    }
    
    public Map<String, Object> getStudentAnalytics(UUID studentId) {
        Map<String, Object> analytics = new HashMap<>();
        
        // Get attendance stats
        Long presentCount = attendanceRepository.countPresentByStudentId(studentId);
        Long totalCount = attendanceRepository.countTotalByStudentId(studentId);
        double attendanceRate = totalCount > 0 ? (double) presentCount / totalCount : 0.0;
        
        // Get performance stats
        List<Performance> performances = performanceRepository.findByStudentId(studentId);
        Double avgScore = performances.stream()
                .filter(p -> p.getScore() != null)
                .mapToDouble(p -> p.getScore().doubleValue())
                .average()
                .orElse(0.0);
        
        // Get enrolled courses count
        Long enrolledCourses = enrollmentRepository.countByStudentId(studentId);
        
        analytics.put("attendanceRate", attendanceRate);
        analytics.put("averageScore", avgScore);
        analytics.put("totalAssignments", performances.size());
        analytics.put("enrolledCourses", enrolledCourses);
        analytics.put("presentCount", presentCount);
        analytics.put("totalAttendance", totalCount);
        
        return analytics;
    }
}


