package com.scholartrack.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholartrack.model.Location;
import com.scholartrack.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepo;

    public String saveProvince(Location location){
        if(!locationRepo.existsByCode(location.getCode())){
            locationRepo.save(location);
            return "Parent saved succesfully";
        }
        else{
            return "Parent location already exists";
        }
    }

    public String saveChildren(String parentCode, Location location){

        if(parentCode != null){
            Optional<Location> getParent = locationRepo.findByCode(parentCode);

            if(getParent.isPresent()){
                location.setParent(getParent.get());

                    if(!locationRepo.existsByCode(location.getCode())){
                        locationRepo.save(location);
                        return "child is saved successfully";
                    }else{
                        return "child with this code exists";
                    }
                
            }else{
                return "The parent with this code does not exist";
            }
        }else{
                if(!locationRepo.existsByCode(location.getCode())){
                locationRepo.save(location);
                        return "parent is saved successfully";
                }else{
                        return "parent exists";
                }
            }
    }

    public Optional<Location> getLocation(String code){
        return locationRepo.findByCode(code);
    }

    public String getProvinceBySector(String code){
        Optional<Location> response = locationRepo.findByCode(code);
        if(response.isPresent()){
            Location location = response.get();
            return location.getParent().getParent().getName();
        }
        else{
            return "No location found";
        }
    }

    public Optional<Location> updateByCode(String code, Location updates){
        Optional<Location> existingOpt = locationRepo.findByCode(code);
        if(existingOpt.isEmpty()){
            return Optional.empty();
        }

        Location existing = existingOpt.get();

        if(updates.getName() != null){
            existing.setName(updates.getName());
        }
        if(updates.getCode() != null){
            existing.setCode(updates.getCode());
        }
        if(updates.getType() != null){
            existing.setType(updates.getType());
        }
        if(updates.getParent() != null){
            Location providedParent = updates.getParent();
            if(providedParent.getId() != null){
                locationRepo.findById(providedParent.getId()).ifPresent(existing::setParent);
            } else if(providedParent.getCode() != null){
                locationRepo.findByCode(providedParent.getCode()).ifPresent(existing::setParent);
            } else {
                existing.setParent(null);
            }
        }

        return Optional.of(locationRepo.save(existing));
    }


    public boolean deleteByCode(String code){
        Optional<Location> existing = locationRepo.findByCode(code);
        if(existing.isEmpty()){
            return false;
        }
        locationRepo.delete(existing.get());
        return true;
    }
}
