package com.freightfox.assignment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freightfox.assignment.dto.InputDTO;
import com.freightfox.assignment.service.TransporterService;

@RestController
@RequestMapping("/api/v1/transporters") // Matches the URL in the PDF [cite: 105]
public class TransporterController {

    @Autowired
    private TransporterService service;

    // API 1: Submit Input Data
    @PostMapping("/input")
    public ResponseEntity<?> submitInputData(@RequestBody InputDTO input) {
        service.saveData(input);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Input data saved successfully."));
    }

    // API 2: Get Optimized Results
    @PostMapping("/assignment")
    public ResponseEntity<?> getOptimizedAssignment(@RequestBody Map<String, Integer> request) {
        int max = request.getOrDefault("maxTransporters", 3); // Default to 3 if missing
        Map<String, Object> result = service.assignTransporters(max);
        return ResponseEntity.ok(result);
    }
}