package com.scholartrack.repository;

import com.scholartrack.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<StudentCourse, UUID> {

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.student.studentNumber = :studentNumber AND sc.course.courseCode = :courseCode")
    Optional<StudentCourse> findByStudentNumberAndCourseCode(@Param("studentNumber") String studentNumber,
            @Param("courseCode") String courseCode);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.student.studentNumber = :studentNumber")
    List<StudentCourse> findByStudentNumber(@Param("studentNumber") String studentNumber);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.course.courseCode = :courseCode")
    List<StudentCourse> findByCourseCode(@Param("courseCode") String courseCode);

    @Query("SELECT COUNT(sc) FROM StudentCourse sc WHERE sc.course.courseCode = :courseCode")
    Long countByCourseCode(@Param("courseCode") String courseCode);

    @Query("SELECT COUNT(sc) FROM StudentCourse sc WHERE sc.student.studentNumber = :studentNumber")
    Long countByStudentNumber(@Param("studentNumber") String studentNumber);
}