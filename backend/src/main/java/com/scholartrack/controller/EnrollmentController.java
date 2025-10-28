
package com.scholartrack.controller;

import com.scholartrack.model.StudentCourse;
import com.scholartrack.service.EnrollmentService;
import com.scholartrack.repository.StudentRepository;
import com.scholartrack.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.HashMap;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CourseRepository courseRepository;

    private static ResponseEntity<Map<String, Object>> errorResponse(int status, String error, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        errorResponse.put("timestamp", java.time.LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    @GetMapping
    public ResponseEntity<List<StudentCourse>> getAllEnrollments() {
        return new ResponseEntity<>(enrollmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCourse> getEnrollmentById(@PathVariable UUID id) {
        Optional<StudentCourse> enrollment = enrollmentService.findById(id);
        
        if (enrollment.isPresent()) {
            return new ResponseEntity<>(enrollment.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
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
                .map(enrollment -> new ResponseEntity<>(enrollment, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

    // @PostMapping
    // public ResponseEntity<StudentCourse> createEnrollment(@RequestBody StudentCourse enrollment) {
    //     return ResponseEntity.ok(enrollmentService.create(enrollment));
    // }

    // @PostMapping("/enroll")
    // public ResponseEntity<StudentCourse> enrollStudent(@RequestParam UUID studentId, 
    //         @RequestParam UUID courseId) {
    //     return ResponseEntity.ok(enrollmentService.enrollStudent(studentId, courseId));
    // }

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollStudent(@RequestParam String studentNumber,
                                                @RequestParam String courseCode) {
        // Check if student exists
        if (!studentRepository.findByStudentNumber(studentNumber).isPresent()) {
            return errorResponse(404, "Student Not Found", 
                "Cannot enroll student. Student with number '" + studentNumber + "' does not exist in the system. Please verify the student number and try again.");
        }
        
        // Check if course exists
        if (!courseRepository.findByCourseCode(courseCode).isPresent()) {
            return errorResponse(404, "Course Not Found", 
                "Cannot enroll student. Course with code '" + courseCode + "' does not exist in the system. Please verify the course code and try again.");
        }
        
        return ResponseEntity.ok(enrollmentService.enrollByStudentNumberAndCourseCode(studentNumber, courseCode));
    }

    @PostMapping("/enroll/students-to-course")
    public ResponseEntity<?> enrollStudentsToCourse(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<String> studentNumbers = (List<String>) request.get("studentNumbers");
        String courseCode = (String) request.get("courseCode");
        
        if (studentNumbers == null || studentNumbers.isEmpty()) {
            return errorResponse(400, "Bad Request", 
                "Student numbers list cannot be empty. Please provide at least one student number to enroll.");
        }
        
        if (courseCode == null || courseCode.trim().isEmpty()) {
            return errorResponse(400, "Bad Request", 
                "Course code is required. Please provide a valid course code.");
        }
        
        if (!courseRepository.findByCourseCode(courseCode).isPresent()) {
            return errorResponse(404, "Course Not Found", 
                "Cannot enroll students. Course with code '" + courseCode + "' does not exist in the system. Please verify the course code and try again.");
        }
        
        for (String studentNumber : studentNumbers) {
            if (!studentRepository.findByStudentNumber(studentNumber).isPresent()) {
                return errorResponse(404, "Student Not Found", 
                    "Cannot enroll students. Student with number '" + studentNumber + "' does not exist in the system. Please verify the student number and try again.");
            }
        }
        
        return new ResponseEntity<>(enrollmentService.enrollStudentsToCourse(studentNumbers, courseCode), HttpStatus.OK);
    }

    @PostMapping("/enroll/student-to-courses")
    public ResponseEntity<?> enrollStudentToCourses(@RequestParam String studentNumber,
            @RequestParam List<String> courseCodes) {
        if (studentNumber == null || studentNumber.trim().isEmpty()) {
            return errorResponse(400, "Bad Request", 
                "Student number is required. Please provide a valid student number.");
        }
        
        if (courseCodes == null || courseCodes.isEmpty()) {
            return errorResponse(400, "Bad Request", 
                "Course codes list cannot be empty. Please provide at least one course code to enroll the student in.");
        }
        
        if (!studentRepository.findByStudentNumber(studentNumber).isPresent()) {
            return errorResponse(404, "Student Not Found", 
                "Cannot enroll student. Student with number '" + studentNumber + "' does not exist in the system. Please verify the student number and try again.");
        }
        
        for (String courseCode : courseCodes) {
            if (!courseRepository.findByCourseCode(courseCode).isPresent()) {
                return errorResponse(404, "Course Not Found", 
                    "Cannot enroll student. Course with code '" + courseCode + "' does not exist in the system. Please verify the course code and try again.");
            }
        }
        
        return ResponseEntity.ok(enrollmentService.enrollStudentToCourses(studentNumber, courseCodes));
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
