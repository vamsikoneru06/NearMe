package com.example.demo.controller;
import com.example.demo.model.Restaurant; // Changed
import com.example.demo.repository.RestaurantRepository; // Changed
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@CrossOrigin(origins = "*")
public class RestaurantController { // Changed
    @Autowired 
    private RestaurantRepository repo; // Changed
    
    @GetMapping("/listRestaurants") // CHANGED ENDPOINT
    public List<Restaurant> listRestaurants(@RequestParam(required = false) String location) { // Changed
        if (location != null && !location.isEmpty()) { 
            return repo.findByLocationContainingIgnoreCase(location); 
        }
        return repo.findAll();
    }
}