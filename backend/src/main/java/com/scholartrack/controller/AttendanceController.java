package com.scholartrack.controller;

import com.scholartrack.model.Attendance;
import com.scholartrack.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
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
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.create(attendance));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudentId(@PathVariable UUID studentId) {
        return ResponseEntity.ok(attendanceService.findByStudentId(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Attendance>> getAttendanceByCourseId(@PathVariable UUID courseId) {
        return ResponseEntity.ok(attendanceService.findByCourseId(courseId));
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudentAndCourse(
            @PathVariable UUID studentId, 
            @PathVariable UUID courseId) {
        return ResponseEntity.ok(attendanceService.findByStudentIdAndCourseId(studentId, courseId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Attendance>> getAttendanceByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(attendanceService.findByAttendanceDateBetween(
                LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Attendance>> getAttendanceByStatus(@PathVariable String status) {
        return ResponseEntity.ok(attendanceService.findByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable UUID id, @RequestBody Attendance attendance) {
        return attendanceService.update(id, attendance)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable UUID id) {
        attendanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}/stats")
    public ResponseEntity<Object> getStudentAttendanceStats(@PathVariable UUID studentId) {
        Long present = attendanceService.getPresentCountByStudentId(studentId);
        Long total = attendanceService.getTotalCountByStudentId(studentId);
        Double rate = attendanceService.getAttendanceRateByStudentId(studentId);
        
        return ResponseEntity.ok(new Object() {
            public final Long presentCount = present;
            public final Long totalCount = total;
            public final Double attendanceRate = rate;
        });
    }

    @GetMapping("/course/{courseId}/stats")
    public ResponseEntity<Object> getCourseAttendanceStats(@PathVariable UUID courseId) {
        Long present = attendanceService.getPresentCountByCourseId(courseId);
        Long total = attendanceService.getTotalCountByCourseId(courseId);
        Double rate = attendanceService.getAttendanceRateByCourseId(courseId);
        
        return ResponseEntity.ok(new Object() {
            public final Long presentCount = present;
            public final Long totalCount = total;
            public final Double attendanceRate = rate;
        });
    }
}