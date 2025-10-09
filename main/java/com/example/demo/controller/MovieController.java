package com.example.demo.controller;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@CrossOrigin(origins = "*")
public class MovieController {
    @Autowired 
    private MovieRepository repo;
    
    @GetMapping("/listMovies")
    public List<Movie> getAllMovies(@RequestParam(required = false) String location) {
        if (location != null && !location.isEmpty()) { 
            return repo.findByLocationContainingIgnoreCase(location); 
        }
        return repo.findAll();
    }
}