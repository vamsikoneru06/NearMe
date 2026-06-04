package com.nearme.dto;

import com.nearme.model.Category;
import jakarta.validation.constraints.*;

public class LocationRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    private String name;

    @NotNull(message = "Category is required")
    private Category category;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;

    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0",  message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0",   message = "Latitude must be between -90 and 90")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0",  message = "Longitude must be between -180 and 180")
    private Double longitude;

    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;

    public String getName()         { return name; }
    public void setName(String n)   { this.name = n; }
    public Category getCategory()   { return category; }
    public void setCategory(Category c) { this.category = c; }
    public String getDescription()  { return description; }
    public void setDescription(String d) { this.description = d; }
    public String getAddress()      { return address; }
    public void setAddress(String a) { this.address = a; }
    public Double getLatitude()     { return latitude; }
    public void setLatitude(Double lat) { this.latitude = lat; }
    public Double getLongitude()    { return longitude; }
    public void setLongitude(Double lng) { this.longitude = lng; }
    public String getImageUrl()     { return imageUrl; }
    public void setImageUrl(String u) { this.imageUrl = u; }
}
