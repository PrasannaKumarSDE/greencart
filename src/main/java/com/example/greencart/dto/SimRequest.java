package com.example.greencart.dto;

public class SimRequest {
    private int availableDrivers;
    private String startTime; // HH:mm
    private int maxHoursPerDriver;

    public SimRequest() {}

    public int getAvailableDrivers() { return availableDrivers; }
    public void setAvailableDrivers(int availableDrivers) { this.availableDrivers = availableDrivers; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public int getMaxHoursPerDriver() { return maxHoursPerDriver; }
    public void setMaxHoursPerDriver(int maxHoursPerDriver) { this.maxHoursPerDriver = maxHoursPerDriver; }
}
