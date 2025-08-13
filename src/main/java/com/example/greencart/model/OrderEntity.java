package com.example.greencart.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "value_rs")
    private double valueRs;

    @Column(name = "route_id")
    private String routeId; // <-- matches DataLoader

    private LocalDateTime deliveryTimestamp;
    private boolean deliveredOnTime;
    private Long driverId;

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public double getValueRs() { return valueRs; }
    public void setValueRs(double valueRs) { this.valueRs = valueRs; }

    public String getRouteId() { return routeId; }
    public void setRouteId(String routeId) { this.routeId = routeId; }

    public LocalDateTime getDeliveryTimestamp() { return deliveryTimestamp; }
    public void setDeliveryTimestamp(LocalDateTime deliveryTimestamp) { this.deliveryTimestamp = deliveryTimestamp; }

    public boolean isDeliveredOnTime() { return deliveredOnTime; }
    public void setDeliveredOnTime(boolean deliveredOnTime) { this.deliveredOnTime = deliveredOnTime; }

    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
}
