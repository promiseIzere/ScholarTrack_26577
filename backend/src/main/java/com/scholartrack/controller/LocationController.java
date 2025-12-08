package com.scholartrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scholartrack.model.Location;
import com.scholartrack.service.LocationService;

@RestController
@RequestMapping(value = "/api/location")
public class LocationController {
    
    @Autowired
    private LocationService locationService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveParent(@RequestBody Location location){
        
        String response = locationService.saveProvince(location);

        if(response.equals("Parent saved succesfully")){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/saveChild", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveChildren(@RequestBody Location location, @RequestParam String parentCode){
        return ResponseEntity.ok(locationService.saveChildren(parentCode, location));
    }

    @GetMapping(value = "/getLocation")
    public ResponseEntity<?> getLocation(@RequestParam String code){
        return ResponseEntity.ok(locationService.getLocation(code));
    }

    @GetMapping(value = "/getProvinceNameBySector")
    public ResponseEntity<?> getProvinceNameBySector(@RequestParam String code){
        return ResponseEntity.ok(locationService.getProvinceBySector(code));
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateLocation(@RequestParam String code, @RequestBody Location location){
        return locationService.updateByCode(code, location)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(new ResponseEntity<>("Location not found", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteLocation(@RequestParam String code){
        boolean deleted = locationService.deleteByCode(code);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Location not found", HttpStatus.NOT_FOUND);
        }
    }
}
