package com.scholartrack.controller;

import com.scholartrack.model.Student;
import com.scholartrack.service.CSVService;
import com.scholartrack.service.StudentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;
    private final CSVService csvService;

    public StudentController(StudentService studentService, CSVService csvService) {
        this.studentService = studentService;
        this.csvService = csvService;
    }
// add student validation
    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        if(student.getFirstName() == null || student.getLastName() == null || student.getEmail() == null || student.getGender() == null || student.getDateOfBirth() == null || student.getLocation() == null) {
            throw new IllegalArgumentException("All fields are required");
        }
        return ResponseEntity.ok(studentService.create(student));
    }
// add student list
    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<List<Student>> listOfAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Student> get(@PathVariable UUID id) {
        return studentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Student> update(@PathVariable UUID id, @RequestBody Student student) {
        return studentService.update(id, student)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        studentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/locations", produces = "application/json")
    public ResponseEntity<List<com.scholartrack.model.Location>> getAvailableLocations() {
        return ResponseEntity.ok(studentService.getAvailableLocations());
    }

    // @PostMapping("/import")
    // public ResponseEntity<List<Student>> importCsv(@RequestParam("file") MultipartFile file) throws IOException {
    //     return ResponseEntity.ok(csvService.importStudentsCsv(file));
    // }
}


