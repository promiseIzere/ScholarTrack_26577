package com.scholartrack.repository;

import com.scholartrack.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    Optional<Course> findByCourseCode(String courseCode);
    Optional<Course> findById(UUID id);

    @Query("SELECT c FROM Course c WHERE c.courseName LIKE %:name%")
    List<Course> findByCourseNameContaining(@Param("name") String name);
}


