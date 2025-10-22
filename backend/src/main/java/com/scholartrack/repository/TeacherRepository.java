package com.scholartrack.repository;

import com.scholartrack.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    
    Optional<Teacher> findByEmployeeId(String employeeId);
    Optional<Teacher> findByEmail(String email);
    
    @Query("SELECT t FROM Teacher t WHERE t.department = :department")
    List<Teacher> findByDepartment(@Param("department") String department);
    
    @Query("SELECT t FROM Teacher t WHERE t.status = :status")
    List<Teacher> findByStatus(@Param("status") Teacher.Status status);
    
    @Query("SELECT t FROM Teacher t WHERE t.department = :department AND t.status = :status")
    List<Teacher> findByDepartmentAndStatus(@Param("department") String department, @Param("status") Teacher.Status status);
    
    @Query("SELECT t FROM Teacher t WHERE t.firstName LIKE %:name% OR t.lastName LIKE %:name%")
    List<Teacher> findByNameContaining(@Param("name") String name);
    
    boolean existsByEmployeeId(String employeeId);
    boolean existsByEmail(String email);
}
