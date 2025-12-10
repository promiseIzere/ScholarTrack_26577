package com.scholartrack.repository;

import com.scholartrack.model.Location;
import com.scholartrack.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);
    Optional<Student> findTopByOrderByStudentNumberDesc();
    Optional<Student> findByStudentNumber(String studentNumber);
    Page<Student> findByVillageIn(List<Location> villages, Pageable pageable);

}