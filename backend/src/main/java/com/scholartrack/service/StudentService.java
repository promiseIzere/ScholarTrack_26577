package com.scholartrack.service;

import com.scholartrack.model.Student;
import com.scholartrack.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Student> findById(UUID id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> update(UUID id, Student update) {
        return studentRepository.findById(id).map(existing -> {
            existing.setStudentNumber(update.getStudentNumber());
            existing.setFirstName(update.getFirstName());
            existing.setLastName(update.getLastName());
            existing.setEmail(update.getEmail());
            existing.setGender(update.getGender());
            existing.setDateOfBirth(update.getDateOfBirth());
            existing.setStatus(update.getStatus());
            existing.setEnrollmentDate(update.getEnrollmentDate());
            existing.setLocation(update.getLocation());
            return existing;
        });
    }

    public void delete(UUID id) {
        studentRepository.deleteById(id);
    }
}


