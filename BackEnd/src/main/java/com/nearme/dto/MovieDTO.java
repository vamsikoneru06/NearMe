package com.nearme.dto;

import com.nearme.model.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieDTO {

    private Long id;
    private String title;
    private String genre;
    private double rating;
    private String poster;
    private String location;
    private List<SeatDTO> seatingLayout;

    public static MovieDTO from(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.id = movie.getId();
        dto.title = movie.getTitle();
        dto.genre = movie.getGenre();
        dto.rating = movie.getRating();
        dto.poster = movie.getPoster();
        dto.location = movie.getLocation();
        dto.seatingLayout = movie.getSeatingLayout().stream()
                .map(SeatDTO::from)
                .collect(Collectors.toList());
        return dto;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public double getRating() { return rating; }
    public String getPoster() { return poster; }
    public String getLocation() { return location; }
    public List<SeatDTO> getSeatingLayout() { return seatingLayout; }
}
