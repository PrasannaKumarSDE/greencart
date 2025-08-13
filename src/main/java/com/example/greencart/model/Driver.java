package com.example.greencart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//import javax.persistence.*;

@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double currentShiftHours;
    @Column(length = 1000)
    private String past7DaysHours; // e.g. "8,8,7.5,6,8,8,7"

    public Driver() {}

    public Driver(String name, double currentShiftHours, String past7DaysHours) {
        this.name = name;
        this.currentShiftHours = currentShiftHours;
        this.past7DaysHours = past7DaysHours;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCurrentShiftHours() { return currentShiftHours; }
    public void setCurrentShiftHours(double currentShiftHours) { this.currentShiftHours = currentShiftHours; }

    public String getPast7DaysHours() { return past7DaysHours; }
    public void setPast7DaysHours(String past7DaysHours) { this.past7DaysHours = past7DaysHours; }
}

