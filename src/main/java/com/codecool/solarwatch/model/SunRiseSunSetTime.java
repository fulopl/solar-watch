package com.codecool.solarwatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class SunRiseSunSetTime {

    @Id
    @GeneratedValue
    private long id;
    private LocalDate localDate;
    private long cityId;
    private String sunRise;
    private String sunSet;

    public long getId() {
        return id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
    public long getCityId() {
        return cityId;
    }

    public String getSunRise() {
        return sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }
}
