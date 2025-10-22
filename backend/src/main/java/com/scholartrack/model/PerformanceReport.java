package com.scholartrack.model;

import java.util.Map;
import java.util.UUID;

// DTO for analytics output
public class PerformanceReport {
    private UUID studentId;
    private double attendanceRate; // 0..1
    private Double averageAssignmentScore; // 0..100
    private Map<String, Double> gradeTrendByTerm; // term -> avg score
    private String predictedRiskLevel; // LOW/MEDIUM/HIGH based on attendance

    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }
    public double getAttendanceRate() { return attendanceRate; }
    public void setAttendanceRate(double attendanceRate) { this.attendanceRate = attendanceRate; }
    public Double getAverageAssignmentScore() { return averageAssignmentScore; }
    public void setAverageAssignmentScore(Double averageAssignmentScore) { this.averageAssignmentScore = averageAssignmentScore; }
    public Map<String, Double> getGradeTrendByTerm() { return gradeTrendByTerm; }
    public void setGradeTrendByTerm(Map<String, Double> gradeTrendByTerm) { this.gradeTrendByTerm = gradeTrendByTerm; }
    public String getPredictedRiskLevel() { return predictedRiskLevel; }
    public void setPredictedRiskLevel(String predictedRiskLevel) { this.predictedRiskLevel = predictedRiskLevel; }
}


