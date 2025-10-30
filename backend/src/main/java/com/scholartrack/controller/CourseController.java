package com.scholartrack.controller;

import com.scholartrack.model.Course;
import com.scholartrack.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable UUID id) {
        Optional<Course> course = courseService.findById(id);
        
        if (course.isPresent()) {
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        }
        
        return new ResponseEntity<>("course with id " + id + " not found", HttpStatus.NOT_FOUND);
    }


    @GetMapping("/code/{courseCode}")
    public ResponseEntity<?> getCourseByCourseCode(@PathVariable String courseCode) {
        Optional<Course> course = courseService.findByCourseCode(courseCode);
        
        if (course.isPresent()) {
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        }
        
        return new ResponseEntity<>("course with code " + courseCode + " not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        String teacherId = course.getTeacherId();
        ResponseEntity<?> savedCourse = courseService.createCourse(teacherId, course);
        return savedCourse;
    }

    @PutMapping(value = "/update/{courseCode}")
    public ResponseEntity<?> updateCourse(@PathVariable String courseCode, @RequestBody Course courseDetails) {
        try {
            String teacherId = courseDetails.getTeacherId();
            Course updatedCourse = courseService.updateCourse(courseCode, teacherId, courseDetails);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>("course with code " + courseCode + " not found", HttpStatus.NOT_FOUND);
            } else if (e.getMessage().contains("already exists")) {
                return new ResponseEntity<>("course code " + courseDetails.getCourseCode() + " is already in use", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("We were not able to update the course", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @DeleteMapping(value = "/{courseCode}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseCode) {
        try {
            courseService.deleteCourse(courseCode);
            return new ResponseEntity<>("course with code " + courseCode + " deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("We were not able to delete the course", HttpStatus.NOT_FOUND);
        }
    }
}