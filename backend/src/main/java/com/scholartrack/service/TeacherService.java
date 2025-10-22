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
    public Optional<Teacher> findByEmployeeId(String employeeId) {
        return teacherRepository.findByEmployeeId(employeeId);
    }

    @Transactional(readOnly = true)
    public Optional<Teacher> findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Teacher> findByDepartment(String department) {
        return teacherRepository.findByDepartment(department);
    }

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
            existing.setEmployeeId(update.getEmployeeId());
            existing.setFirstName(update.getFirstName());
            existing.setLastName(update.getLastName());
            existing.setEmail(update.getEmail());
            existing.setPhone(update.getPhone());
            existing.setDepartment(update.getDepartment());
            existing.setHireDate(update.getHireDate());
            existing.setStatus(update.getStatus());
            existing.setUser(update.getUser());
            return existing;
        });
    }

    public void delete(UUID id) {
        teacherRepository.deleteById(id);
    }
}
