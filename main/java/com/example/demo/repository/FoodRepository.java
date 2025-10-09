package com.example.demo.repository;
import com.example.demo.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByLocationContainingIgnoreCase(String location);
}