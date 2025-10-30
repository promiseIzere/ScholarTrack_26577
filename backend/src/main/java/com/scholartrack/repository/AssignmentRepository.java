package com.scholartrack.repository;

import com.scholartrack.model.Assignment;
// import com.scholartrack.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    
    @Query("SELECT a FROM Assignment a WHERE a.course.id = :courseId")
    List<Assignment> findByCourseId(@Param("courseId") UUID courseId);
    
    @Query("SELECT a FROM Assignment a WHERE a.course.id = :courseId AND a.dueDate BETWEEN :startDate AND :endDate")
    List<Assignment> findByCourseIdAndDueDateBetween(@Param("courseId") UUID courseId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT a FROM Assignment a WHERE a.dueDate < :date")
    List<Assignment> findOverdueAssignments(@Param("date") LocalDate date);
    
    @Query("SELECT a FROM Assignment a WHERE a.dueDate BETWEEN :startDate AND :endDate")
    List<Assignment> findByDueDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT a FROM Assignment a WHERE a.title LIKE %:title%")
    List<Assignment> findByTitleContaining(@Param("title") String title);

    @Query("SELECT COUNT(a) > 0 FROM Assignment a WHERE a.course.courseCode = :courseCode AND a.title = :title")
    boolean existsByCourse_CourseCodeAndTitle(@Param("courseCode") String courseCode, @Param("title") String title);

    @Query("SELECT a FROM Assignment a WHERE a.course.courseCode = :courseCode")
    List<Assignment> findByCourseCode(@Param("courseCode") String courseCode);

    @Query("SELECT a FROM Assignment a WHERE a.course.courseCode = :courseCode AND a.title = :title")
    List<Assignment> findByCourseCodeAndTitle(@Param("courseCode") String courseCode, @Param("title") String title);
}


