package com.scholartrack.controller;

import com.scholartrack.model.StudentCourse;
import com.scholartrack.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<StudentCourse>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCourse> getEnrollmentById(@PathVariable UUID id) {
        return enrollmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentCourse>> getEnrollmentsByStudentId(@PathVariable UUID studentId) {
        return ResponseEntity.ok(enrollmentService.findByStudentId(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<StudentCourse>> getEnrollmentsByCourseId(@PathVariable UUID courseId) {
        return ResponseEntity.ok(enrollmentService.findByCourseId(courseId));
    }

    @GetMapping("/enrollment")
    public ResponseEntity<StudentCourse> getEnrollmentByStudentAndCourse(
            @RequestParam UUID studentId, 
            @RequestParam UUID courseId) {
        return enrollmentService.findByStudentIdAndCourseId(studentId, courseId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StudentCourse> createEnrollment(@RequestBody StudentCourse enrollment) {
        return ResponseEntity.ok(enrollmentService.create(enrollment));
    }

    @PostMapping("/enroll")
    public ResponseEntity<StudentCourse> enrollStudent(@RequestParam UUID studentId, 
            @RequestParam UUID courseId) {
        return ResponseEntity.ok(enrollmentService.enrollStudent(studentId, courseId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentCourse> updateEnrollment(@PathVariable UUID id, @RequestBody StudentCourse enrollment) {
        return enrollmentService.update(id, enrollment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable UUID id) {
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/unenroll")
    public ResponseEntity<Void> unenrollStudent(@RequestParam UUID studentId, 
            @RequestParam UUID courseId) {
        enrollmentService.unenrollStudent(studentId, courseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/course/{courseId}/count")
    public ResponseEntity<Long> getStudentCountByCourseId(@PathVariable UUID courseId) {
        return ResponseEntity.ok(enrollmentService.getStudentCountByCourseId(courseId));
    }

    @GetMapping("/student/{studentId}/count")
    public ResponseEntity<Long> getCourseCountByStudentId(@PathVariable UUID studentId) {
        return ResponseEntity.ok(enrollmentService.getCourseCountByStudentId(studentId));
    }
}
