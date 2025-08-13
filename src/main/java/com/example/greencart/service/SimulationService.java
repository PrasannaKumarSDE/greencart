package com.example.greencart.service;

import com.example.greencart.model.*;
import com.example.greencart.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SimulationService {

    private final DriverRepository driverRepo;
    private final RouteRepository routeRepo;
    private final OrderRepository orderRepo;
    private final SimulationResultRepository simRepo;

    public SimulationService(DriverRepository d, RouteRepository r, OrderRepository o, SimulationResultRepository s) {
        this.driverRepo = d;
        this.routeRepo = r;
        this.orderRepo = o;
        this.simRepo = s;
    }

    @Transactional
    public SimulationResult runSimulation(int availableDrivers, String startTime, int maxHoursPerDriver) {

        if (availableDrivers <= 0) throw new IllegalArgumentException("availableDrivers must be > 0");
        if (maxHoursPerDriver <= 0) throw new IllegalArgumentException("maxHoursPerDriver must be > 0");

        try {
            LocalTime.parse(startTime);
        } catch (Exception e) {
            throw new IllegalArgumentException("startTime must be in HH:mm format");
        }

        List<OrderEntity> orders = orderRepo.findAll();
        List<Driver> drivers = driverRepo.findAll();
        if (drivers.isEmpty()) throw new IllegalStateException("No drivers available in DB");
        if (availableDrivers > drivers.size()) availableDrivers = drivers.size();
        drivers = drivers.subList(0, availableDrivers);

        Map<String, Route> routeMap = routeRepo.findAll().stream()
                .collect(Collectors.toMap(Route::getRouteId, r -> r));

        int totalOrders = orders.size();
        int onTimeCount = 0;
        double totalProfit = 0.0;
        double totalFuelCost = 0.0;
        double totalBonuses = 0.0;
        double totalPenalties = 0.0;

        double[] driverHours = new double[drivers.size()];
        int di = 0;

        for (OrderEntity order : orders) {
            Driver assigned = drivers.get(di % drivers.size());

            if (driverHours[di % drivers.size()] >= maxHoursPerDriver) {
                di++;
                continue;
            }
            di++;

            order.setDriverId(assigned.getId());

            Route route = routeMap.get(order.getRouteId());
            if (route == null) continue;

            double baseTime = route.getBaseTimeMinutes();
            double deliveryTime = baseTime;

            // Traffic delay
            if ("High".equalsIgnoreCase(route.getTrafficLevel())) deliveryTime += 15;
            else if ("Medium".equalsIgnoreCase(route.getTrafficLevel())) deliveryTime += 7;

            // Fatigue slowdown
            if (assigned.getCurrentShiftHours() > 8) deliveryTime *= 1.3;

            driverHours[(di - 1) % drivers.size()] += deliveryTime / 60.0;

            boolean isOnTime = deliveryTime <= (baseTime + 10);
            if (isOnTime) onTimeCount++;

            double penalty = isOnTime ? 0 : 50;
            totalPenalties += penalty;

            double bonus = (order.getValueRs() > 1000 && isOnTime)
                    ? 0.10 * order.getValueRs()
                    : 0;
            totalBonuses += bonus;

            double fuel = route.getDistanceKm() * 5;
            if ("High".equalsIgnoreCase(route.getTrafficLevel())) {
                fuel += route.getDistanceKm() * 2;
            }
            totalFuelCost += fuel;

            double orderProfit = order.getValueRs() + bonus - penalty - fuel;
            totalProfit += orderProfit;

            order.setDeliveredOnTime(isOnTime);
            orderRepo.save(order);
        }

        double efficiency = totalOrders == 0 ? 0.0 : (onTimeCount * 100.0 / totalOrders);

        SimulationResult result = new SimulationResult();
        result.setCreatedAt(LocalDateTime.now());
        result.setOnTimeDeliveries(onTimeCount);
        result.setTotalDeliveries(totalOrders);
        result.setEfficiencyScore(Math.round(efficiency * 100.0) / 100.0);
        result.setTotalProfit(Math.round(totalProfit * 100.0) / 100.0);
        result.setTotalFuelCost(Math.round(totalFuelCost * 100.0) / 100.0);
        result.setTotalBonuses(Math.round(totalBonuses * 100.0) / 100.0);
        result.setTotalPenalties(Math.round(totalPenalties * 100.0) / 100.0);
        result.setRawJson("{}");

        simRepo.save(result);
        return result;
    }

    public List<SimulationResult> history() {
        return simRepo.findAll();
    }
}
