package com.scholartrack.service;

import com.scholartrack.model.Location;
import com.scholartrack.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location create(Location location) {
        return locationRepository.save(location);
    }

    @Transactional(readOnly = true)
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Location> findById(UUID id) {
        return locationRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Location> findByProvinceAndDistrict(String province, String district) {
        return locationRepository.findByProvinceAndDistrict(province, district);
    }

    @Transactional(readOnly = true)
    public List<String> findAllProvinces() {
        return locationRepository.findAllProvinces();
    }

    @Transactional(readOnly = true)
    public List<String> findDistrictsByProvince(String province) {
        return locationRepository.findDistrictsByProvince(province);
    }

    public Optional<Location> update(UUID id, Location update) {
        return locationRepository.findById(id).map(existing -> {
            existing.setProvince(update.getProvince());
            existing.setDistrict(update.getDistrict());
            existing.setSector(update.getSector());
            existing.setCell(update.getCell());
            existing.setVillage(update.getVillage());
            return existing;
        });
    }

    public void delete(UUID id) {
        locationRepository.deleteById(id);
    }
}
