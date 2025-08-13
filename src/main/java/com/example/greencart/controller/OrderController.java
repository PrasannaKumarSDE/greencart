package com.example.greencart.controller;

import com.example.greencart.model.OrderEntity;
import com.example.greencart.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
    private final OrderRepository repo;
    public OrderController(OrderRepository repo) { this.repo = repo; }

    @GetMapping
    public List<OrderEntity> all() { return repo.findAll(); }

    @PostMapping
    public OrderEntity create(@RequestBody OrderEntity o) { return repo.save(o); }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody OrderEntity o) {
        return repo.findById(id).map(existing -> {
            existing.setValueRs(o.getValueRs());
            existing.setRouteId(o.getRouteId());
            existing.setDeliveryTimestamp(o.getDeliveryTimestamp());
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
