package com.example.greencart.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SimulationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private double totalProfit;
    private double efficiencyScore;
    private int onTimeDeliveries;
    private int totalDeliveries;
    @Column(length = 4000)
    private String rawJson;

    private double totalFuelCost;   // <--- Add this

    // Optional extra metrics for richer charts
    private double totalBonuses;
    private double totalPenalties;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public double getEfficiencyScore() {
		return efficiencyScore;
	}

	public void setEfficiencyScore(double efficiencyScore) {
		this.efficiencyScore = efficiencyScore;
	}

	public int getOnTimeDeliveries() {
		return onTimeDeliveries;
	}

	public void setOnTimeDeliveries(int onTimeDeliveries) {
		this.onTimeDeliveries = onTimeDeliveries;
	}

	public int getTotalDeliveries() {
		return totalDeliveries;
	}

	public void setTotalDeliveries(int totalDeliveries) {
		this.totalDeliveries = totalDeliveries;
	}

	public String getRawJson() {
		return rawJson;
	}

	public void setRawJson(String rawJson) {
		this.rawJson = rawJson;
	}

	public double getTotalFuelCost() {
		return totalFuelCost;
	}

	public void setTotalFuelCost(double totalFuelCost) {
		this.totalFuelCost = totalFuelCost;
	}

	public double getTotalBonuses() {
		return totalBonuses;
	}

	public void setTotalBonuses(double totalBonuses) {
		this.totalBonuses = totalBonuses;
	}

	public double getTotalPenalties() {
		return totalPenalties;
	}

	public void setTotalPenalties(double totalPenalties) {
		this.totalPenalties = totalPenalties;
	}

	public SimulationResult(Long id, LocalDateTime createdAt, double totalProfit, double efficiencyScore,
			int onTimeDeliveries, int totalDeliveries, String rawJson, double totalFuelCost, double totalBonuses,
			double totalPenalties) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.totalProfit = totalProfit;
		this.efficiencyScore = efficiencyScore;
		this.onTimeDeliveries = onTimeDeliveries;
		this.totalDeliveries = totalDeliveries;
		this.rawJson = rawJson;
		this.totalFuelCost = totalFuelCost;
		this.totalBonuses = totalBonuses;
		this.totalPenalties = totalPenalties;
	}

	public SimulationResult() {}
}

  