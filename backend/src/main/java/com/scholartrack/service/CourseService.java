package com.scholartrack.service;

import com.scholartrack.model.Course;
import com.scholartrack.model.Teacher;
import com.scholartrack.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private TeacherService teacherService;

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Course> findById(UUID id) {
        return courseRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Course> findByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }

    @Transactional
    public ResponseEntity<?> createCourse(String teacherId, Course course) {
        if (teacherId == null || teacherId.trim().isEmpty()) {
            return new ResponseEntity<>("Teacher ID is required", HttpStatus.BAD_REQUEST);
            }

        Teacher teacher = teacherService.findByTeacherId(teacherId)
            .orElse(null);
        if (teacher == null) {
            return new ResponseEntity<>("Teacher not found with ID: " + teacherId, HttpStatus.NOT_FOUND);
        }
        course.setTeacher(teacher);
        if (course.getCourseCode() == null || course.getCourseCode().trim().isEmpty()) {
            return new ResponseEntity<>("Course code is required", HttpStatus.BAD_REQUEST);
        }
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            return new ResponseEntity<>("Course with code " + course.getCourseCode() + " already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Course>(courseRepository.save(course), HttpStatus.CREATED);
    }

    @Transactional
    public Course updateCourse(String courseCode, String teacherId, Course courseDetails) throws Exception {
        Course existingCourse = courseRepository.findByCourseCode(courseCode)
            .orElseThrow(() -> new Exception("Course not found with code: " + courseCode));
    
        if (!courseDetails.getCourseCode().equals(courseCode)) {
            Optional<Course> courseWithNewCode = courseRepository.findByCourseCode(courseDetails.getCourseCode());
            if (courseWithNewCode.isPresent()) {
                throw new Exception("Course code " + courseDetails.getCourseCode() + " already exists in the database");
            }
        }
    
        existingCourse.setCourseCode(courseDetails.getCourseCode());
        existingCourse.setCourseName(courseDetails.getCourseName());
        existingCourse.setDescription(courseDetails.getDescription());
        existingCourse.setCredits(courseDetails.getCredits());
    
        // Update teacher if provided
        if (teacherId != null && !teacherId.trim().isEmpty()) {
            Teacher teacher = teacherService.findByTeacherId(teacherId)
                .orElseThrow(() -> new Exception("Teacher not found with ID: " + teacherId));
            existingCourse.setTeacher(teacher);
        }
    
        return courseRepository.save(existingCourse);
    }

    @Transactional
    public void deleteCourse(String courseCode) {
        Course course = courseRepository.findByCourseCode(courseCode)
            .orElseThrow(() -> new IllegalArgumentException("Course not found with code: " + courseCode));
        
        courseRepository.delete(course);
    }
}