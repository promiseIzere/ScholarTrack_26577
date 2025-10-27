package com.scholartrack.service;

import com.scholartrack.model.Teacher;
import com.scholartrack.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher create(Teacher teacher) {
        if (teacher.getTeacherId() == null || teacher.getTeacherId().trim().isEmpty()) {
            teacher.setTeacherId(generateNextTeacherId());
        }
        return teacherRepository.save(teacher);
    }

    @Transactional(readOnly = true)
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Teacher> findById(UUID id) {
        return teacherRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Teacher> findByTeacherId(String teacherId) {
        return teacherRepository.findByTeacherId(teacherId);
    }

    @Transactional(readOnly = true)
    public Optional<Teacher> findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    // @Transactional(readOnly = true)
    // public List<Teacher> findByDepartment(String department) {
    //     return teacherRepository.findByDepartment(department);
    // }

    @Transactional(readOnly = true)
    public List<Teacher> findByStatus(Teacher.Status status) {
        return teacherRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Teacher> findByNameContaining(String name) {
        return teacherRepository.findByNameContaining(name);
    }

    public Optional<Teacher> update(UUID id, Teacher update) {
        return teacherRepository.findById(id).map(existing -> {
            existing.setTeacherId(update.getTeacherId());
            existing.setFirstName(update.getFirstName());
            existing.setLastName(update.getLastName());
            existing.setEmail(update.getEmail());
            existing.setPhone(update.getPhone());
            // existing.setDepartment(update.getDepartment());
            existing.setHireDate(update.getHireDate());
            existing.setStatus(update.getStatus());
            existing.setUser(update.getUser());
            return existing;
        });
    }

    public void delete(UUID id) {
        teacherRepository.deleteById(id);
    }

    
        /**
     * Generates the next teacher ID automatically.
     * Format: TEA001, TEA002, TEA003, etc.
     * 
     * @return the next available teacher ID
     */
    private String generateNextTeacherId() {
        // Get the highest existing teacher ID
        String lastTeacherId = teacherRepository.findTopByOrderByTeacherIdDesc()
            .map(Teacher::getTeacherId)
            .orElse("TEA000");
        
        // Extract the numeric part and increment it
        String prefix = "TEA";
        
        // Ensure the teacher ID starts with the prefix
        if (!lastTeacherId.startsWith(prefix)) {
            return "TEA001";
        }
        
        String numericPart = lastTeacherId.substring(prefix.length());
        
        try {
            int nextNumber = Integer.parseInt(numericPart) + 1;
            return String.format("%s%03d", prefix, nextNumber);
        } catch (NumberFormatException e) {
            // If parsing fails, start from 1
            return "TEA001";
        }
    }

    public Teacher getTeacherById(UUID id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if(teacher == null) {
            System.out.println("Teacher not found with ID: " + id);
            return null;
        } else {
            return teacher;
        }
    }
}
