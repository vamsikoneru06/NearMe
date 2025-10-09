package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private double rating;
    @Lob
    @Column(columnDefinition="TEXT")
    private String poster;
    private String location;

    public Movie() {}
    public Movie(String title, String genre, double rating, String poster, String location) {
        this.title = title; this.genre = genre; this.rating = rating; this.poster = poster; this.location = location;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}