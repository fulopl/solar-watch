package com.codecool.solarwatch.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class City implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String state;
    private String country;
    private double longitude;
    private double latitude;
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("localDate desc")
    private List<SunRiseSunSetTime> sunRiseSunSetTimes;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", sunRiseSunSetTimes=" + sunRiseSunSetTimes +
                '}';
    }
}
