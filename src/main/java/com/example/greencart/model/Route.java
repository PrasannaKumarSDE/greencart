package com.example.greencart.model;

import jakarta.persistence.Column;
//import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "route")
public class Route {
    @Id
    @Column(name = "route_id")
    private String routeId;

    @Column(name = "distance_km")
    private double distanceKm;

    @Column(name = "traffic_level")
    private String trafficLevel;

    @Column(name = "base_time_minutes")  // Match the exact column name in DB
    private int baseTimeMinutes;

    public Route() {}  // Default constructor

    public Route(String routeId, double distanceKm, String trafficLevel, int baseTimeMinutes) {
        this.routeId = routeId;
        this.distanceKm = distanceKm;
        this.trafficLevel = trafficLevel;
        this.baseTimeMinutes = baseTimeMinutes;
    }

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public double getDistanceKm() {
		return distanceKm;
	}

	public void setDistanceKm(double distanceKm) {
		this.distanceKm = distanceKm;
	}

	public String getTrafficLevel() {
		return trafficLevel;
	}

	public void setTrafficLevel(String trafficLevel) {
		this.trafficLevel = trafficLevel;
	}

	public int getBaseTimeMinutes() {
		return baseTimeMinutes;
	}

	public void setBaseTimeMinutes(int baseTimeMinutes) {
		this.baseTimeMinutes = baseTimeMinutes;
	}

    // getters and setters...
}
