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
import java.util.ArrayList;

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
        var student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return List.of();
        }
        return enrollmentRepository.findByStudentNumber(student.getStudentNumber());
    }

    @Transactional(readOnly = true)
    public List<StudentCourse> findByCourseId(UUID courseId) {
        var course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return List.of();
        }
        return enrollmentRepository.findByCourseCode(course.getCourseCode());
    }

    @Transactional(readOnly = true)
    public Optional<StudentCourse> findByStudentIdAndCourseId(UUID studentId, UUID courseId) {
        var student = studentRepository.findById(studentId).orElse(null);
        var course = courseRepository.findById(courseId).orElse(null);
        if (student == null || course == null) {
            return Optional.empty();
        }
        return enrollmentRepository.findByStudentNumberAndCourseCode(student.getStudentNumber(), course.getCourseCode());
    }

    public StudentCourse enrollStudent(UUID studentId, UUID courseId) {
        var student = studentRepository.findById(studentId).orElseThrow();
        var course = courseRepository.findById(courseId).orElseThrow();
        var existing = enrollmentRepository.findByStudentNumberAndCourseCode(student.getStudentNumber(), course.getCourseCode());
        if (existing.isPresent()) {
            return existing.get();
        }
        StudentCourse enrollment = new StudentCourse();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());
        return enrollmentRepository.save(enrollment);
    }

    public StudentCourse enrollByStudentNumberAndCourseCode(String studentNumber, String courseCode) {
        var student = studentRepository.findByStudentNumber(studentNumber).orElseThrow();
        var course = courseRepository.findByCourseCode(courseCode).orElseThrow();
        var existing = enrollmentRepository.findByStudentNumberAndCourseCode(student.getStudentNumber(), course.getCourseCode());
        if (existing.isPresent()) {
            return existing.get();
        }
        StudentCourse enrollment = new StudentCourse();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());
        return enrollmentRepository.save(enrollment);
    }

    public List<StudentCourse> enrollStudentsToCourse(List<String> studentNumbers, String courseCode) {
        List<StudentCourse> created = new ArrayList<>();
        var course = courseRepository.findByCourseCode(courseCode).orElseThrow();
        for (String studentNumber : studentNumbers) {
            var student = studentRepository.findByStudentNumber(studentNumber).orElseThrow();
            var existing = enrollmentRepository.findByStudentNumberAndCourseCode(student.getStudentNumber(), course.getCourseCode());
            if (existing.isPresent()) {
                continue;
            }
            StudentCourse enrollment = new StudentCourse();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnrollmentDate(LocalDate.now());
            created.add(enrollmentRepository.save(enrollment));
        }
        return created;
    }

    public List<StudentCourse> enrollStudentToCourses(String studentNumber, List<String> courseCodes) {
        List<StudentCourse> created = new ArrayList<>();
        var student = studentRepository.findByStudentNumber(studentNumber).orElseThrow();
        for (String courseCode : courseCodes) {
            var course = courseRepository.findByCourseCode(courseCode).orElseThrow();
            var existing = enrollmentRepository.findByStudentNumberAndCourseCode(student.getStudentNumber(), course.getCourseCode());
            if (existing.isPresent()) {
                continue;
            }
            StudentCourse enrollment = new StudentCourse();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnrollmentDate(LocalDate.now());
            created.add(enrollmentRepository.save(enrollment));
        }
        return created;
    }

    public void unenrollStudent(UUID studentId, UUID courseId) {
        var student = studentRepository.findById(studentId).orElse(null);
        var course = courseRepository.findById(courseId).orElse(null);
        if (student == null || course == null) {
            return;
        }
        enrollmentRepository.findByStudentNumberAndCourseCode(student.getStudentNumber(), course.getCourseCode())
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

    @Transactional(readOnly = true)
    public Long getStudentCountByCourseId(UUID courseId) {
        var course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return 0L;
        }
        return enrollmentRepository.countByCourseCode(course.getCourseCode());
    }

    @Transactional(readOnly = true)
    public Long getCourseCountByStudentId(UUID studentId) {
        var student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return 0L;
        }
        return enrollmentRepository.countByStudentNumber(student.getStudentNumber());
    }
}
