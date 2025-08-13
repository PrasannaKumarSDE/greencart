package com.example.greencart.controller;

import com.example.greencart.dto.SimRequest;
import com.example.greencart.model.SimulationResult;
import com.example.greencart.service.SimulationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SimulationController {
    private final SimulationService simService;

    public SimulationController(SimulationService s) {
        this.simService = s;
    }

    @PostMapping("/simulate")
    public ResponseEntity<?> simulate(@RequestBody SimRequest req) {
        try {
            SimulationResult res = simService.runSimulation(
                    req.getAvailableDrivers(),
                    req.getStartTime(),   // matches DTO
                    req.getMaxHoursPerDriver()
            );
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(500).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Internal error",
                    "detail", e.getMessage()
            ));
        }
    }

    @GetMapping("/simulation/history")
    public ResponseEntity<List<SimulationResult>> history() {
        return ResponseEntity.ok(simService.history());
    }
}
