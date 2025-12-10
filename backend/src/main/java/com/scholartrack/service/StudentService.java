package com.scholartrack.service;

import com.scholartrack.dto.StudentCreateDTO;
import com.scholartrack.dto.StudentResponseDTO;
import com.scholartrack.model.Location;
import com.scholartrack.model.ELocationType;
import com.scholartrack.model.Student;
import com.scholartrack.repository.LocationRepository;
import com.scholartrack.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final LocationRepository locationRepository;
    private final LocationService locationService;

    public StudentService(StudentRepository studentRepository,
                          LocationRepository locationRepository,
                          LocationService locationService) {
        this.studentRepository = studentRepository;
        this.locationRepository = locationRepository;
        this.locationService = locationService;
    }

    public StudentResponseDTO registerStudent(StudentCreateDTO request) {
        Location village = locationRepository.findById(request.villageId())
                .orElseThrow(() -> new IllegalArgumentException("Village not found with id " + request.villageId()));
        if (village.getType() != ELocationType.VILLAGE) {
            throw new IllegalArgumentException("Provided location id does not point to a village");
        }

        Student student = new Student();
        student.setStudentNumber(generateNextStudentNumber());
        student.setFullName(request.fullName().trim());
        deriveNameParts(student);
        student.setVillage(village);

        Student saved = studentRepository.save(student);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> getStudentsByCellId(UUID cellId, Pageable pageable) {
        Location cell = locationRepository.findById(cellId)
                .orElseThrow(() -> new IllegalArgumentException("Cell not found with id " + cellId));
        if (cell.getType() != ELocationType.CELL) {
            throw new IllegalArgumentException("Location must be a cell to fetch its students");
        }
        List<Location> villages = collectVillagesUnder(cell);
        return toResponses(villages, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Student> findById(UUID id) {
        return studentRepository.findById(id);
    }

    public StudentResponseDTO toResponse(Student student) {
        List<Location> chain = locationService.getHierarchy(student.getVillage());
        Location province = chain.stream().filter(l -> l.getType() == ELocationType.PROVINCE).findFirst().orElse(null);
        Location district = chain.stream().filter(l -> l.getType() == ELocationType.DISTRICT).findFirst().orElse(null);
        Location sector = chain.stream().filter(l -> l.getType() == ELocationType.SECTOR).findFirst().orElse(null);
        Location cell = chain.stream().filter(l -> l.getType() == ELocationType.CELL).findFirst().orElse(null);
        Location village = chain.stream().filter(l -> l.getType() == ELocationType.VILLAGE).findFirst().orElse(null);

        return new StudentResponseDTO(
                student.getId(),
                student.getStudentNumber(),
                student.getFullName(),
                province != null ? province.getName() : null,
                district != null ? district.getName() : null,
                sector != null ? sector.getName() : null,
                cell != null ? cell.getName() : null,
                village != null ? village.getName() : null
        );
    }

    private void deriveNameParts(Student student) {
        String[] parts = student.getFullName().trim().split("\\s+", 2);
        student.setFirstName(parts[0]);
        if (parts.length > 1) {
            student.setLastName(parts[1]);
        }
    }

    private String generateNextStudentNumber() {
        String lastStudentNumber = studentRepository.findTopByOrderByStudentNumberDesc()
                .map(Student::getStudentNumber)
                .orElse("STU000");

        String prefix = "STU";
        String numericPart = lastStudentNumber.substring(prefix.length());

        try {
            int nextNumber = Integer.parseInt(numericPart) + 1;
            return String.format("%s%03d", prefix, nextNumber);
        } catch (NumberFormatException e) {
            return "STU001";
        }
    }

    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> getStudentsBySectorId(UUID sectorId, Pageable pageable) {
        Location sector = locationRepository.findById(sectorId)
                .orElseThrow(() -> new IllegalArgumentException("Sector not found with id " + sectorId));
        if (sector.getType() != ELocationType.SECTOR) {
            throw new IllegalArgumentException("Location must be a sector to fetch its students");
        }
        List<Location> villages = collectVillagesUnder(sector);
        return toResponses(villages, pageable);
    }

    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> getStudentsByDistrictId(UUID districtId, Pageable pageable) {
        Location district = locationRepository.findById(districtId)
                .orElseThrow(() -> new IllegalArgumentException("District not found with id " + districtId));
        if (district.getType() != ELocationType.DISTRICT) {
            throw new IllegalArgumentException("Location must be a district to fetch its students");
        }
        List<Location> villages = collectVillagesUnder(district);
        return toResponses(villages, pageable);
    }

    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> getStudentsByProvinceId(UUID provinceId, Pageable pageable) {
        Location province = locationRepository.findById(provinceId)
                .orElseThrow(() -> new IllegalArgumentException("Province not found with id " + provinceId));
        if (province.getType() != ELocationType.PROVINCE) {
            throw new IllegalArgumentException("Location must be a province to fetch its students");
        }
        List<Location> villages = collectVillagesUnder(province);
        return toResponses(villages, pageable);
    }

    private Page<StudentResponseDTO> toResponses(List<Location> villages, Pageable pageable) {
        if (villages.isEmpty()) {
            return Page.empty(pageable);
        }
        Page<Student> students = studentRepository.findByVillageIn(villages, pageable);
        return students.map(this::toResponse);
    }

    private List<Location> collectVillagesUnder(Location root) {
        List<Location> villages = new ArrayList<>();
        Deque<Location> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Location current = stack.pop();
            if (current.getType() == ELocationType.VILLAGE) {
                villages.add(current);
            } else {
                List<Location> children = locationRepository.findByParent(current);
                stack.addAll(children);
            }
        }
        return villages;
    }
}