package com.scholartrack.controller;

import com.scholartrack.model.Attendance;
import com.scholartrack.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable UUID id) {
        return attendanceService.findById(id)
                .map(attendance -> new ResponseEntity<>(attendance, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        return new ResponseEntity<>(attendanceService.create(attendance), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudentId(@PathVariable UUID studentId) {
        return new ResponseEntity<>(attendanceService.findByStudentId(studentId), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Attendance>> getAttendanceByCourseId(@PathVariable UUID courseId) {
        return new ResponseEntity<>(attendanceService.findByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudentAndCourse(
            @PathVariable UUID studentId, 
            @PathVariable UUID courseId) {
        return new ResponseEntity<>(attendanceService.findByStudentIdAndCourseId(studentId, courseId), HttpStatus.OK);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Attendance>> getAttendanceByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return new ResponseEntity<>(attendanceService.findByAttendanceDateBetween(
                LocalDate.parse(startDate), LocalDate.parse(endDate)), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Attendance>> getAttendanceByStatus(@PathVariable String status) {
        return new ResponseEntity<>(attendanceService.findByStatus(status), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable UUID id, @RequestBody Attendance attendance) {
        return attendanceService.update(id, attendance)
                .map(updatedAttendance -> new ResponseEntity<>(updatedAttendance, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable UUID id) {
        attendanceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/student/{studentId}/stats")
    public ResponseEntity<Object> getStudentAttendanceStats(@PathVariable UUID studentId) {
        Long present = attendanceService.getPresentCountByStudentId(studentId);
        Long total = attendanceService.getTotalCountByStudentId(studentId);
        Double rate = attendanceService.getAttendanceRateByStudentId(studentId);
        
        return new ResponseEntity<>(new Object() {
            public final Long presentCount = present;
            public final Long totalCount = total;
            public final Double attendanceRate = rate;
        }, HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}/stats")
    public ResponseEntity<Object> getCourseAttendanceStats(@PathVariable UUID courseId) {
        Long present = attendanceService.getPresentCountByCourseId(courseId);
        Long total = attendanceService.getTotalCountByCourseId(courseId);
        Double rate = attendanceService.getAttendanceRateByCourseId(courseId);
        
        return new ResponseEntity<>(new Object() {
            public final Long presentCount = present;
            public final Long totalCount = total;
            public final Double attendanceRate = rate;
        }, HttpStatus.OK);
    }
}