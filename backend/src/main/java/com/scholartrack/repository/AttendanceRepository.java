package com.scholartrack.repository;

import com.scholartrack.model.Attendance;
import com.scholartrack.model.Course;
import com.scholartrack.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    List<Attendance> findByStudent(Student student);
    List<Attendance> findByCourse(Course course);
    List<Attendance> findByStudentAndCourse(Student student, Course course);

    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId")
    List<Attendance> findByStudentId(@Param("studentId") UUID studentId);

    @Query("SELECT a FROM Attendance a WHERE a.course.id = :courseId")
    List<Attendance> findByCourseId(@Param("courseId") UUID courseId);

    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.course.id = :courseId")
    List<Attendance> findByStudentIdAndCourseId(@Param("studentId") UUID studentId, @Param("courseId") UUID courseId);

    @Query("SELECT a FROM Attendance a WHERE a.attendanceDate BETWEEN :start AND :end")
    List<Attendance> findByAttendanceDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
    
    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.attendanceDate BETWEEN :start AND :end")
    List<Attendance> findByStudentIdAndAttendanceDateBetween(@Param("studentId") UUID studentId, @Param("start") LocalDate start, @Param("end") LocalDate end);
    
    @Query("SELECT a FROM Attendance a WHERE a.course.id = :courseId AND a.attendanceDate BETWEEN :start AND :end")
    List<Attendance> findByCourseIdAndAttendanceDateBetween(@Param("courseId") UUID courseId, @Param("start") LocalDate start, @Param("end") LocalDate end);
    
    @Query("SELECT a FROM Attendance a WHERE a.status = :status")
    List<Attendance> findByStatus(@Param("status") String status);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'Present'")
    Long countPresentByStudentId(@Param("studentId") UUID studentId);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId")
    Long countTotalByStudentId(@Param("studentId") UUID studentId);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.course.id = :courseId AND a.status = 'Present'")
    Long countPresentByCourseId(@Param("courseId") UUID courseId);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.course.id = :courseId")
    Long countTotalByCourseId(@Param("courseId") UUID courseId);
}


