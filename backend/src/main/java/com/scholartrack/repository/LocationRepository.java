package com.scholartrack.repository;

import com.scholartrack.model.Location;
import com.scholartrack.model.ELocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    List<Location> findByParent(Location parent);
    Page<Location> findByParent(Location parent, Pageable pageable);

    Optional<Location> findByNameAndType(String name, ELocationType type);
    boolean existsByNameAndType(String name, ELocationType type);

    Optional<Location> findByCode(String code);
    boolean existsByCode(String code);
}
