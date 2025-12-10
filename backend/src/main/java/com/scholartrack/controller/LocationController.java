package com.scholartrack.controller;

import com.scholartrack.dto.LocationCreateDTO;
import com.scholartrack.model.Location;
import com.scholartrack.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/province")
    public ResponseEntity<Location> createProvince(@Valid @RequestBody LocationCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.createProvince(request));
    }

    @PostMapping("/district")
    public ResponseEntity<Location> createDistrict(@Valid @RequestBody LocationCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.createDistrict(request));
    }

    @PostMapping("/sector")
    public ResponseEntity<Location> createSector(@Valid @RequestBody LocationCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.createSector(request));
    }

    @PostMapping("/cell")
    public ResponseEntity<Location> createCell(@Valid @RequestBody LocationCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.createCell(request));
    }

    @PostMapping("/village")
    public ResponseEntity<Location> createVillage(@Valid @RequestBody LocationCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.createVillage(request));
    }

    @GetMapping("/children/{parentId}")
    public ResponseEntity<Page<Location>> getChildren(@PathVariable UUID parentId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size) {
        PageRequest pageable = PageRequest.of(Math.max(page,0), Math.max(size,1));
        return ResponseEntity.ok(locationService.getChildrenByParent(parentId, pageable));
    }
}
