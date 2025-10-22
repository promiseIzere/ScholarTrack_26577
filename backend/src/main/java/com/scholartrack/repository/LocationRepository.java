package com.scholartrack.repository;

import com.scholartrack.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    @Query("SELECT l FROM Location l WHERE l.province = :province AND l.district = :district")
    List<Location> findByProvinceAndDistrict(@Param("province") String province, @Param("district") String district);

    @Query("SELECT DISTINCT l.province FROM Location l ORDER BY l.province")
    List<String> findAllProvinces();

    @Query("SELECT DISTINCT l.district FROM Location l WHERE l.province = :province ORDER BY l.district")
    List<String> findDistrictsByProvince(@Param("province") String province);
}


