package com.example.demo.repository;
import com.example.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByLocationContainingIgnoreCase(String location);
}