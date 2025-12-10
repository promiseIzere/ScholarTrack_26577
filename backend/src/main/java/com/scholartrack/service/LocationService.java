package com.scholartrack.service;

import com.scholartrack.dto.LocationCreateDTO;
import com.scholartrack.exception.BadRequestException;
import com.scholartrack.exception.ConflictException;
import com.scholartrack.exception.NotFoundException;
import com.scholartrack.model.Location;
import com.scholartrack.model.ELocationType;
import com.scholartrack.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createProvince(LocationCreateDTO dto) {
        return persistLocation(dto, ELocationType.PROVINCE, null);
    }

    public Location createDistrict(LocationCreateDTO dto) {
        Location parent = loadAndValidateParent(dto.parentCode(), ELocationType.PROVINCE, "Province");
        return persistLocation(dto, ELocationType.DISTRICT, parent);
    }

    public Location createSector(LocationCreateDTO dto) {
        Location parent = loadAndValidateParent(dto.parentCode(), ELocationType.DISTRICT, "District");
        return persistLocation(dto, ELocationType.SECTOR, parent);
    }

    public Location createCell(LocationCreateDTO dto) {
        Location parent = loadAndValidateParent(dto.parentCode(), ELocationType.SECTOR, "Sector");
        return persistLocation(dto, ELocationType.CELL, parent);
    }

    public Location createVillage(LocationCreateDTO dto) {
        Location parent = loadAndValidateParent(dto.parentCode(), ELocationType.CELL, "Cell");
        return persistLocation(dto, ELocationType.VILLAGE, parent);
    }

    @Transactional(readOnly = true)
    public Page<Location> getChildrenByParent(UUID parentId, Pageable pageable) {
        Location parent = locationRepository.findById(parentId)
                .orElseThrow(() -> new NotFoundException("Parent location not found with id " + parentId));
        return locationRepository.findByParent(parent, pageable);
    }

    @Transactional(readOnly = true)
    public List<Location> getHierarchy(Location location) {
        Objects.requireNonNull(location, "location is required");
        Deque<Location> stack = new ArrayDeque<>();
        Location current = location;
        while (current != null) {
            stack.push(current);
            current = current.getParent();
        }
        return new ArrayList<>(stack);
    }

    private Location persistLocation(LocationCreateDTO dto, ELocationType type, Location parent) {
        validateName(dto.name());
        validateCode(dto.code());
        if (locationRepository.existsByCode(dto.code().trim())) {
            throw new ConflictException("Location with code '" + dto.code() + "' already exists");
        }
        locationRepository.findByNameAndType(dto.name().trim(), type).ifPresent(existing -> {
            throw new ConflictException("Location with name '" + dto.name() + "' already exists for level " + type);
        });
        Location location = new Location(dto.name().trim(), dto.code().trim(), type, parent);
        return locationRepository.save(location);
    }

    private Location loadAndValidateParent(String parentCode, ELocationType expectedType, String expectedLabel) {
        if (parentCode == null || parentCode.isBlank()) {
            throw new BadRequestException(expectedLabel + " parentCode is required");
        }
        Location parent = locationRepository.findByCode(parentCode.trim())
                .orElseThrow(() -> new NotFoundException(expectedLabel + " not found with code " + parentCode));
        if (parent.getType() != expectedType) {
            throw new BadRequestException("Parent must be a " + expectedLabel.toLowerCase() + " when creating this location");
        }
        return parent;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("Location name is required");
        }
    }

    private void validateCode(String code) {
        if (code == null || code.isBlank()) {
            throw new BadRequestException("Location code is required");
        }
    }
}
