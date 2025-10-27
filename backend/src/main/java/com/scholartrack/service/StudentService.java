package com.scholartrack.service;

import com.scholartrack.model.Location;
import com.scholartrack.model.Student;
import com.scholartrack.repository.LocationRepository;
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
    private final LocationRepository locationRepository;

    public StudentService(StudentRepository studentRepository, LocationRepository locationRepository) {
        this.studentRepository = studentRepository;
        this.locationRepository = locationRepository;
    }

    public Student create(Student student) {
        // Generate student number automatically if not provided
        if (student.getStudentNumber() == null || student.getStudentNumber().trim().isEmpty()) {
            student.setStudentNumber(generateNextStudentNumber());
        }
        
        // Validate that the location exists if provided
        if (student.getLocation() != null && student.getLocation().getId() != null) {
            Location location = locationRepository.findById(student.getLocation().getId())
                .orElseThrow(()-> new IllegalArgumentException("Location with ID " + student.getLocation().getId() + " not found"));
            student.setLocation(location);
        }
        Student savedStudent = studentRepository.save(student);
        if (savedStudent.getLocation() != null) {
            savedStudent.getLocation().getId();
        }
        return savedStudent;
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
    
    @Transactional(readOnly = true)
    public List<Location> getAvailableLocations() {
        return locationRepository.findAll();
    }
    
   
    private String generateNextStudentNumber() {
        // Get the highest existing student number
        String lastStudentNumber = studentRepository.findTopByOrderByStudentNumberDesc()
            .map(Student::getStudentNumber)
            .orElse("STU000");
        
        // Extract the numeric part and increment it
        String prefix = "STU";
        String numericPart = lastStudentNumber.substring(prefix.length());
        
        try {
            int nextNumber = Integer.parseInt(numericPart) + 1;
            return String.format("%s%03d", prefix, nextNumber);
        } catch (NumberFormatException e) {
            // If parsing fails, start from 1
            return "STU001";
        }
    }
}


