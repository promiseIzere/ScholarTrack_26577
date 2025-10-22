package com.scholartrack.controller;

import com.scholartrack.model.Location;
import com.scholartrack.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable UUID id) {
        return locationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        return ResponseEntity.ok(locationService.create(location));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable UUID id, @RequestBody Location location) {
        return locationService.update(id, location)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/provinces")
    public ResponseEntity<List<String>> getAllProvinces() {
        return ResponseEntity.ok(locationService.findAllProvinces());
    }

    @GetMapping("/districts")
    public ResponseEntity<List<String>> getDistrictsByProvince(@RequestParam String province) {
        return ResponseEntity.ok(locationService.findDistrictsByProvince(province));
    }
}