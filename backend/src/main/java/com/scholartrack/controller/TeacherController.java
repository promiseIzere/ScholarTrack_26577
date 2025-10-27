package com.scholartrack.controller;

import com.scholartrack.model.Teacher;
import com.scholartrack.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "*")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable UUID id) {
        Teacher teacher = teacherService.getTeacherById(id); // get teacher by id from service
        if (teacher != null) {
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<Teacher> getTeacherByTeacherId(@PathVariable String teacherId) {
        return teacherService.findByTeacherId(teacherId)
                .map(teacher -> new ResponseEntity<>(teacher, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Teacher> getTeacherByEmail(@PathVariable String email) {
        return teacherService.findByEmail(email)
                .map(teacher -> new ResponseEntity<>(teacher, HttpStatus.FOUND))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // @GetMapping("/department/{department}")
    // public ResponseEntity<List<Teacher>> getTeachersByDepartment(@PathVariable String department) {
    //     return new ResponseEntity<>(teacherService.findByDepartment(department), HttpStatus.FOUND);
    // }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Teacher>> getTeachersByStatus(@PathVariable Teacher.Status status) {
        return new ResponseEntity<>(teacherService.findByStatus(status), HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Teacher>> searchTeachersByName(@RequestParam String name) {
        return new ResponseEntity<>(teacherService.findByNameContaining(name), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        return new ResponseEntity<>(teacherService.create(teacher), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable UUID id, @RequestBody Teacher teacher) {
        return teacherService.update(id, teacher)
                .map(updatedTeacher -> new ResponseEntity<>(updatedTeacher, HttpStatus.FOUND))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable UUID id) {
        teacherService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
