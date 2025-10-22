package com.scholartrack.controller;

import com.scholartrack.model.Student;
import com.scholartrack.service.CSVService;
import com.scholartrack.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping
    public ResponseEntity<Student> create(@Validated @RequestBody Student student) {
        return ResponseEntity.ok(studentService.create(student));
    }

    @GetMapping
    public ResponseEntity<List<Student>> list() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable UUID id) {
        return studentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable UUID id, @RequestBody Student student) {
        return studentService.update(id, student)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // @PostMapping("/import")
    // public ResponseEntity<List<Student>> importCsv(@RequestParam("file") MultipartFile file) throws IOException {
    //     return ResponseEntity.ok(csvService.importStudentsCsv(file));
    // }
}


