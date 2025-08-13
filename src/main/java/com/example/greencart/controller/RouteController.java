package com.example.greencart.controller;

import com.example.greencart.model.Route;
import com.example.greencart.repository.RouteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin
public class RouteController {
    private final RouteRepository repo;
    public RouteController(RouteRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Route> all() { return repo.findAll(); }

    @PostMapping
    public Route create(@RequestBody Route r) { return repo.save(r); }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Route r) {
        return repo.findById(id).map(existing -> {
            existing.setDistanceKm(r.getDistanceKm());
            existing.setTrafficLevel(r.getTrafficLevel());
            existing.setBaseTimeMinutes(r.getBaseTimeMinutes());
            repo.save(existing);
            return ResponseEntity.ok(existing);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        repo.deleteById(id);
        return ResponseEntity.ok(Map.of("deleted", true));
    }
}
