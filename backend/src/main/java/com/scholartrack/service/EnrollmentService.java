package com.scholartrack.service;

import com.scholartrack.model.StudentCourse;
import com.scholartrack.repository.EnrollmentRepository;
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
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CourseRepository courseRepository;

    public StudentCourse create(StudentCourse enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Transactional(readOnly = true)
    public List<StudentCourse> findAll() {
        return enrollmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<StudentCourse> findById(UUID id) {
        return enrollmentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<StudentCourse> findByStudentId(UUID studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<StudentCourse> findByCourseId(UUID courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public Optional<StudentCourse> findByStudentIdAndCourseId(UUID studentId, UUID courseId) {
        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);
    }

    public StudentCourse enrollStudent(UUID studentId, UUID courseId) {
        StudentCourse enrollment = new StudentCourse();
        enrollment.setStudent(studentRepository.findById(studentId).orElseThrow());
        enrollment.setCourse(courseRepository.findById(courseId).orElseThrow());
        enrollment.setEnrollmentDate(LocalDate.now());
        return enrollmentRepository.save(enrollment);
    }

    public void unenrollStudent(UUID studentId, UUID courseId) {
        enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .ifPresent(enrollmentRepository::delete);
    }

    public Optional<StudentCourse> update(UUID id, StudentCourse update) {
        return enrollmentRepository.findById(id).map(existing -> {
            existing.setStudent(update.getStudent());
            existing.setCourse(update.getCourse());
            existing.setEnrollmentDate(update.getEnrollmentDate());
            return existing;
        });
    }

    public void delete(UUID id) {
        enrollmentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Long getStudentCountByCourseId(UUID courseId) {
        return enrollmentRepository.countByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public Long getCourseCountByStudentId(UUID studentId) {
        return enrollmentRepository.countByStudentId(studentId);
    }
}
