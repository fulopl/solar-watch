package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.SunRiseSunSetTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SunRiseSunSetTimeRepository extends JpaRepository<SunRiseSunSetTime, Long> {

    Optional<SunRiseSunSetTime> findByCityAndLocalDate(City city, LocalDate localDate);

}
