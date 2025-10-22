package com.scholartrack.controller;

import com.scholartrack.model.StudentCourse;
import com.scholartrack.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/student-courses")
@CrossOrigin(origins = "*")
public class StudentCourseController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<StudentCourse>> getAllStudentCourses() {
        return ResponseEntity.ok(enrollmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCourse> getStudentCourseById(@PathVariable UUID id) {
        return enrollmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentCourse>> getStudentCoursesByStudentId(@PathVariable UUID studentId) {
        return ResponseEntity.ok(enrollmentService.findByStudentId(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<StudentCourse>> getStudentCoursesByCourseId(@PathVariable UUID courseId) {
        return ResponseEntity.ok(enrollmentService.findByCourseId(courseId));
    }

    @GetMapping("/enrollment")
    public ResponseEntity<StudentCourse> getStudentCourseByStudentAndCourse(
            @RequestParam UUID studentId, 
            @RequestParam UUID courseId) {
        return enrollmentService.findByStudentIdAndCourseId(studentId, courseId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StudentCourse> createStudentCourse(@RequestBody StudentCourse studentCourse) {
        return ResponseEntity.ok(enrollmentService.create(studentCourse));
    }

    @PostMapping("/enroll")
    public ResponseEntity<StudentCourse> enrollStudent(@RequestParam UUID studentId, 
            @RequestParam UUID courseId) {
        return ResponseEntity.ok(enrollmentService.enrollStudent(studentId, courseId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentCourse> updateStudentCourse(@PathVariable UUID id, @RequestBody StudentCourse studentCourse) {
        return enrollmentService.update(id, studentCourse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentCourse(@PathVariable UUID id) {
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/enrollment")
    public ResponseEntity<Void> unenrollStudent(@RequestParam UUID studentId, 
            @RequestParam UUID courseId) {
        enrollmentService.unenrollStudent(studentId, courseId);
        return ResponseEntity.ok().build();
    }
}


