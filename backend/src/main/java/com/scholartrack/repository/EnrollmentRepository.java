package com.scholartrack.repository;

import com.scholartrack.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<StudentCourse, UUID> {
    
    @Query("SELECT sc FROM StudentCourse sc WHERE sc.student.id = :studentId AND sc.course.id = :courseId")
    Optional<StudentCourse> findByStudentIdAndCourseId(@Param("studentId") UUID studentId, @Param("courseId") UUID courseId);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.student.id = :studentId")
    List<StudentCourse> findByStudentId(@Param("studentId") UUID studentId);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.course.id = :courseId")
    List<StudentCourse> findByCourseId(@Param("courseId") UUID courseId);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.enrollmentDate = :date")
    List<StudentCourse> findByEnrollmentDate(@Param("date") LocalDate date);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.enrollmentDate BETWEEN :startDate AND :endDate")
    List<StudentCourse> findByEnrollmentDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);
    
    @Query("SELECT COUNT(sc) FROM StudentCourse sc WHERE sc.course.id = :courseId")
    Long countByCourseId(@Param("courseId") UUID courseId);
    
    @Query("SELECT COUNT(sc) FROM StudentCourse sc WHERE sc.student.id = :studentId")
    Long countByStudentId(@Param("studentId") UUID studentId);
}
