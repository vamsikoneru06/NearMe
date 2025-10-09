package com.example.demo.controller;
import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@CrossOrigin(origins = "*")
public class EventController {
    @Autowired 
    private EventRepository repo;
    
    @GetMapping("/listEvents")
    public List<Event> listEvents(@RequestParam(required = false) String location) {
        if (location != null && !location.isEmpty()) { 
            return repo.findByVenueContainingIgnoreCase(location); 
        }
        return repo.findAll();
    }
}