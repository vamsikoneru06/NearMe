package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    @Autowired
    private StudentRepository repo;

    @GetMapping("/listStudents")
    public List<Student> getAll() {
        return repo.findAll();
    }

    @PostMapping("/addStudent")
    public Student addStudent(@RequestBody Student s) {
        return repo.save(s);
    }
}
