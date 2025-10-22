package com.scholartrack.service;

import com.scholartrack.model.Course;
import com.scholartrack.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course create(Course course) {
        return courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Course> findById(UUID id) {
        return courseRepository.findById(id);
    }

    public Optional<Course> update(UUID id, Course update) {
        return courseRepository.findById(id).map(existing -> {
            existing.setCourseCode(update.getCourseCode());
            existing.setCourseName(update.getCourseName());
            existing.setDescription(update.getDescription());
            existing.setCredits(update.getCredits());
            existing.setInstructor(update.getInstructor());
            return existing;
        });
    }

    public void delete(UUID id) {
        courseRepository.deleteById(id);
    }
}
