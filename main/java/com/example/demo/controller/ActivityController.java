package com.example.demo.controller;
import com.example.demo.model.Activity;
import com.example.demo.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@CrossOrigin(origins = "*")
public class ActivityController {
    @Autowired 
    private ActivityRepository repo;
    
    @GetMapping("/listActivities")
    public List<Activity> listActivities(@RequestParam(required = false) String location) {
        if (location != null && !location.isEmpty()) { 
            return repo.findByPlaceContainingIgnoreCase(location); 
        }
        return repo.findAll();
    }
}