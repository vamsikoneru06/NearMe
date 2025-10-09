package com.example.demo.controller;
import com.example.demo.model.Shop;
import com.example.demo.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@CrossOrigin(origins = "*")
public class ShopController {
    @Autowired 
    private ShopRepository repo;
    
    @GetMapping("/listShops")
    public List<Shop> listShops(@RequestParam(required = false) String location) {
        if (location != null && !location.isEmpty()) { 
            return repo.findByLocationContainingIgnoreCase(location); 
        }
        return repo.findAll();
    }
}