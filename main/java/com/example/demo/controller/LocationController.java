package com.example.demo.controller;
import com.example.demo.model.Location;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@CrossOrigin(origins = "*")
public class LocationController {
    @Autowired 
    private LocationRepository repo;
    
    @GetMapping("/listLocations")
    public List<Location> getAllLocations(@RequestParam(required = false) String location) {
        if (location != null && !location.isEmpty()) { 
            return repo.findByAreaContainingIgnoreCase(location); 
        }
        return repo.findAll();
    }
}