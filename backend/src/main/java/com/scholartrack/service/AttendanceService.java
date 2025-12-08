package com.scholartrack.service;

import com.scholartrack.model.Attendance;
import com.scholartrack.model.Student;
import com.scholartrack.model.Course;
import com.scholartrack.repository.AttendanceRepository;
import com.scholartrack.repository.StudentRepository;
import com.scholartrack.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Attendance create(Attendance attendance) {
        // Attach managed Student
        if (attendance.getStudent() == null || attendance.getStudent().getId() == null) {
            throw new IllegalArgumentException("student.id is required");
        }
        UUID studentId = attendance.getStudent().getId();
        Student managedStudent = studentRepository.findById(studentId)
            .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));

        // Attach managed Course
        if (attendance.getCourse() == null || attendance.getCourse().getId() == null) {
            throw new IllegalArgumentException("course.id is required");
        }
        UUID courseId = attendance.getCourse().getId();
        Course managedCourse = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));

        // Build a fresh managed entity to avoid partially-bound associations
        Attendance toSave = new Attendance();
        toSave.setStudent(managedStudent);
        toSave.setCourse(managedCourse);
        toSave.setAttendanceDate(attendance.getAttendanceDate() != null ? attendance.getAttendanceDate() : LocalDate.now());
        if (attendance.getStatus() == null || attendance.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("status is required");
        }
        toSave.setStatus(attendance.getStatus());
        toSave.setRemarks(attendance.getRemarks());

        return attendanceRepository.save(toSave);
    }

    @Transactional(readOnly = true)
    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Attendance> findById(UUID id) {
        return attendanceRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Attendance> findByStudentId(UUID studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<Attendance> findByCourseId(UUID courseId) {
        return attendanceRepository.findByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public List<Attendance> findByStudentIdAndCourseId(UUID studentId, UUID courseId) {
        return attendanceRepository.findByStudentIdAndCourseId(studentId, courseId);
    }

    @Transactional(readOnly = true)
    public List<Attendance> findByAttendanceDateBetween(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByAttendanceDateBetween(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Attendance> findByStatus(String status) {
        return attendanceRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public Long getPresentCountByStudentId(UUID studentId) {
        return attendanceRepository.countPresentByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public Long getTotalCountByStudentId(UUID studentId) {
        return attendanceRepository.countTotalByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public Long getPresentCountByCourseId(UUID courseId) {
        return attendanceRepository.countPresentByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public Long getTotalCountByCourseId(UUID courseId) {
        return attendanceRepository.countTotalByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public Double getAttendanceRateByStudentId(UUID studentId) {
        Long present = getPresentCountByStudentId(studentId);
        Long total = getTotalCountByStudentId(studentId);
        return total > 0 ? (double) present / total : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getAttendanceRateByCourseId(UUID courseId) {
        Long present = getPresentCountByCourseId(courseId);
        Long total = getTotalCountByCourseId(courseId);
        return total > 0 ? (double) present / total : 0.0;
    }

    public Optional<Attendance> update(UUID id, Attendance update) {
        return attendanceRepository.findById(id).map(existing -> {
            existing.setStudent(update.getStudent());
            existing.setCourse(update.getCourse());
            existing.setAttendanceDate(update.getAttendanceDate());
            existing.setStatus(update.getStatus());
            existing.setRemarks(update.getRemarks());
            return existing;
        });
    }

    public void delete(UUID id) {
        attendanceRepository.deleteById(id);
    }
}
