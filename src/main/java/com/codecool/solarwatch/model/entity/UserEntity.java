package com.codecool.solarwatch.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public record UserEntity(
        @Id
        @GeneratedValue
        Long id,
        String username,
        String password,
        @ManyToMany
        Set<Role> roles) {

}
