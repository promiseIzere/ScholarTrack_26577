package com.scholartrack.controller;

import com.scholartrack.dto.StudentCreateDTO;
import com.scholartrack.dto.StudentResponseDTO;
import com.scholartrack.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> register(@Valid @RequestBody StudentCreateDTO request) {
        return new ResponseEntity<>(studentService.registerStudent(request), HttpStatus.CREATED);
    }

    @GetMapping("/by-cell/{cellId}")
    public ResponseEntity<Page<StudentResponseDTO>> findByCell(@PathVariable UUID cellId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "20") int size) {
        PageRequest pageable = PageRequest.of(Math.max(page,0), Math.max(size,1));
        return ResponseEntity.ok(studentService.getStudentsByCellId(cellId, pageable));
    }

    @GetMapping("/by-sector/{sectorId}")
    public ResponseEntity<Page<StudentResponseDTO>> findBySector(@PathVariable UUID sectorId,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "20") int size) {
        PageRequest pageable = PageRequest.of(Math.max(page,0), Math.max(size,1));
        return ResponseEntity.ok(studentService.getStudentsBySectorId(sectorId, pageable));
    }

    @GetMapping("/by-district/{districtId}")
    public ResponseEntity<Page<StudentResponseDTO>> findByDistrict(@PathVariable UUID districtId,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "20") int size) {
        PageRequest pageable = PageRequest.of(Math.max(page,0), Math.max(size,1));
        return ResponseEntity.ok(studentService.getStudentsByDistrictId(districtId, pageable));
    }

    @GetMapping("/by-province/{provinceId}")
    public ResponseEntity<Page<StudentResponseDTO>> findByProvince(@PathVariable UUID provinceId,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "20") int size) {
        PageRequest pageable = PageRequest.of(Math.max(page,0), Math.max(size,1));
        return ResponseEntity.ok(studentService.getStudentsByProvinceId(provinceId, pageable));
    }
}
