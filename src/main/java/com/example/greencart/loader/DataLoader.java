package com.example.greencart.loader;

import com.example.greencart.model.*;
import com.example.greencart.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Component
public class DataLoader implements CommandLineRunner {
    private final DriverRepository driverRepo;
    private final RouteRepository routeRepo;
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    public DataLoader(DriverRepository d, RouteRepository r, OrderRepository o, UserRepository u) {
        this.driverRepo = d;
        this.routeRepo = r;
        this.orderRepo = o;
        this.userRepo = u;
    }

    @Override
    public void run(String... args) throws Exception {
        loadDrivers();
        loadRoutes();
        loadOrders();
        createDefaultUser();
    }

    private void loadDrivers() {
        try {
            ClassPathResource res = new ClassPathResource("data/drivers.csv");
            if(!res.exists()) { System.out.println("drivers.csv not found in resources/data/"); return; }
            BufferedReader br = new BufferedReader(new InputStreamReader(res.getInputStream()));
            String line; boolean first=true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; } // skip header
                // split on commas but allow quoted commas
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parts.length < 3) continue;
                String name = parts[0].trim();
                double current = Double.parseDouble(parts[1].trim());
                String past = parts[2].trim().replaceAll("\"", "");
                driverRepo.save(new Driver(name, current, past));
            }
            br.close();
            System.out.println("Drivers loaded");
        } catch (Exception e) {
            System.out.println("drivers load: " + e.getMessage());
        }
    }


    private void loadRoutes() {
        try {
            ClassPathResource res = new ClassPathResource("data/routes.csv");
            if (!res.exists()) {
                System.out.println("routes.csv not found");
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(res.getInputStream()));
            String line;
            boolean first = true;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; } // skip header
                String[] p = line.split(",");
                if (p.length < 4) continue;
                routeRepo.save(new Route(
                        p[0].trim(),                           // route_id
                        Double.parseDouble(p[1].trim()),       // distance_km
                        p[2].trim(),                           // traffic_level
                        Integer.parseInt(p[3].trim())          // base_time_min
                ));
                count++;
            }
            br.close();
            System.out.println("Routes loaded: " + count);
        } catch (Exception e) {
            System.out.println("routes load error: " + e.getMessage());
        }
    }


    private void loadOrders() {
        try {
            ClassPathResource res = new ClassPathResource("data/orders.csv");
            if (!res.exists()) {
                System.out.println("orders.csv not found in resources/data/");
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(res.getInputStream()));
            String line;
            boolean first = true;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; } // skip header
                String[] p = line.split(",");
                if (p.length < 4) continue;

                String orderId = p[0].trim();
                double value = Double.parseDouble(p[1].trim());
                String routeId = p[2].trim();
                String timeStr = p[3].trim(); // HH:mm

                // Convert HH:mm → LocalDateTime for today
                LocalTime time = LocalTime.parse(timeStr);
                LocalDateTime dt = LocalDateTime.of(LocalDate.now(), time);

                OrderEntity o = new OrderEntity();
                o.setOrderId(orderId);
                o.setValueRs(value);
                o.setRouteId(routeId);
                o.setDeliveryTimestamp(dt);
                orderRepo.save(o);
                count++;
            }
            br.close();
            System.out.println("Orders loaded: " + count);
        } catch (Exception e) {
            System.out.println("orders load error: " + e.getMessage());
        }
    }


    private void createDefaultUser() {
        try {
            if (userRepo.findByUsername("manager").isEmpty()) {
                BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
                User u = new User("manager", enc.encode("password123"), "ROLE_MANAGER");
                userRepo.save(u);
                System.out.println("Default user created -> manager / password123");
            }
        } catch (Exception e) { System.out.println("user create: " + e.getMessage()); }
    }
}
