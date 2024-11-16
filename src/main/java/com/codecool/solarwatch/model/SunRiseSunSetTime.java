package com.codecool.solarwatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class SunRiseSunSetTime {

    @Id
    @GeneratedValue
    private long id;
    private LocalDate localDate;
    private String sunRise;
    private String sunSet;
    @ManyToOne
    @JoinColumn(name = "cityId2")
    private City city;

    public SunRiseSunSetTime() {
    }

    public SunRiseSunSetTime(long id, LocalDate localDate, String sunRise, String sunSet, City city) {
        this.id = id;
        this.localDate = localDate;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "SunRiseSunSetTime{" +
                "id=" + id +
                ", localDate=" + localDate +
                ", sunRise='" + sunRise + '\'' +
                ", sunSet='" + sunSet + '\'' +
                '}';
    }
}
