package com.example.demo.controller;
import com.example.demo.model.Food;
import com.example.demo.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@CrossOrigin(origins = "*")
public class FoodController {
    @Autowired 
    private FoodRepository repo;
    
    @GetMapping("/listFoods")
    public List<Food> listFoods(@RequestParam(required = false) String location) {
        if (location != null && !location.isEmpty()) { 
            return repo.findByLocationContainingIgnoreCase(location); 
        }
        return repo.findAll();
    }
}