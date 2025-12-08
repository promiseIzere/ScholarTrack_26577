package com.scholartrack.repository;

import com.scholartrack.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
    @Query("SELECT p FROM Performance p WHERE p.student.id = :studentId AND p.assignment.id = :assignmentId")
    Optional<Performance> findByStudentIdAndAssignmentId(@Param("studentId") UUID studentId, @Param("assignmentId") UUID assignmentId);

    @Query("SELECT p FROM Performance p WHERE p.student.id = :studentId")
    List<Performance> findByStudentId(@Param("studentId") UUID studentId);

    @Query("SELECT p FROM Performance p WHERE p.assignment.id = :assignmentId")
    List<Performance> findByAssignmentId(@Param("assignmentId") UUID assignmentId);
    
    @Query("SELECT AVG(p.score) FROM Performance p WHERE p.student.id = :studentId")
    BigDecimal findAverageScoreByStudentId(@Param("studentId") UUID studentId);
    
    @Query("SELECT AVG(p.score) FROM Performance p WHERE p.assignment.id = :assignmentId")
    BigDecimal findAverageScoreByAssignmentId(@Param("assignmentId") UUID assignmentId);
    
    
    boolean existsByStudent_StudentNumberAndAssignment_Id(String studentNumber, UUID assignmentId);
    
    @Query("SELECT COUNT(p) FROM Performance p WHERE p.assignment.id = :assignmentId")
    Long countByAssignmentId(@Param("assignmentId") UUID assignmentId);
    
    // boolean existsByStudent_StudentNumberAndAssignment_Id(String studentNumber, UUID assignmentId);
    
    @Query("SELECT p FROM Performance p WHERE p.student.id = :studentId AND p.assignment.course.id = :courseId")
    List<Performance> findByStudentIdAndCourseId(@Param("studentId") UUID studentId, @Param("courseId") UUID courseId);

    @Query("SELECT p FROM Performance p WHERE p.assignment.course.id = :courseId")
    List<Performance> findByCourseId(@Param("courseId") UUID courseId);
}


