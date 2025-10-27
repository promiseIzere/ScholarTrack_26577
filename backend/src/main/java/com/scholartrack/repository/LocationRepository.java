package com.scholartrack.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholartrack.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    boolean existsByCode(String code);
    Optional<Location> findByCode(String code);
}
