package com.nearme.dto;

public class GooglePlaceDTO {

    private String id;
    private String name;
    private String address;
    private String location;   // alias for address — frontend card compatibility
    private double rating;
    private int    totalRatings;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private String type;
    private boolean openNow;

    public String  getId()           { return id; }
    public void    setId(String v)   { this.id = v; }

    public String  getName()           { return name; }
    public void    setName(String v)   { this.name = v; }

    public String  getAddress()           { return address; }
    public void    setAddress(String v)   { this.address = v; this.location = v; }

    public String  getLocation()          { return location; }

    public double  getRating()            { return rating; }
    public void    setRating(double v)    { this.rating = v; }

    public int     getTotalRatings()      { return totalRatings; }
    public void    setTotalRatings(int v) { this.totalRatings = v; }

    public String  getImageUrl()          { return imageUrl; }
    public void    setImageUrl(String v)  { this.imageUrl = v; }

    public double  getLatitude()          { return latitude; }
    public void    setLatitude(double v)  { this.latitude = v; }

    public double  getLongitude()         { return longitude; }
    public void    setLongitude(double v) { this.longitude = v; }

    public String  getType()              { return type; }
    public void    setType(String v)      { this.type = v; }

    public boolean isOpenNow()            { return openNow; }
    public void    setOpenNow(boolean v)  { this.openNow = v; }
}
