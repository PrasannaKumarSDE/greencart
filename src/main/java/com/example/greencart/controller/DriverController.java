package com.example.greencart.controller;

import com.example.greencart.model.Driver;
import com.example.greencart.repository.DriverRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin
public class DriverController {
    private final DriverRepository repo;
    public DriverController(DriverRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Driver> all() { return repo.findAll(); }

    @PostMapping
    public Driver create(@RequestBody Driver d) { return repo.save(d); }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Driver d) {
        return repo.findById(id).map(existing -> {
            existing.setName(d.getName());
            existing.setCurrentShiftHours(d.getCurrentShiftHours());
            existing.setPast7DaysHours(d.getPast7DaysHours());
            repo.save(existing);
            return ResponseEntity.ok(existing);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(Map.of("deleted", true));
    }
}
