package com.freightfox.assignment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freightfox.assignment.dto.InputDTO;
import com.freightfox.assignment.dto.LaneDTO;
import com.freightfox.assignment.dto.QuoteDTO;
import com.freightfox.assignment.dto.TransporterDTO;
import com.freightfox.assignment.entity.Lane;
import com.freightfox.assignment.entity.LaneQuote;
import com.freightfox.assignment.entity.Transporter;
import com.freightfox.assignment.repository.LaneRepository;
import com.freightfox.assignment.repository.TransporterRepository;

@Service
public class TransporterService {

    @Autowired
    private LaneRepository laneRepository;

    @Autowired
    private TransporterRepository transporterRepository;

    // --- PART 1: SAVE DATA ---
    public void saveData(InputDTO input) {
        // 1. Convert DTOs to Entities for Lanes
        List<Lane> lanes = new ArrayList<>();
        for (LaneDTO lDto : input.getLanes()) {
            Lane lane = new Lane();
            lane.setId(lDto.getId());
            lane.setOrigin(lDto.getOrigin());
            lane.setDestination(lDto.getDestination());
            lanes.add(lane);
        }
        laneRepository.saveAll(lanes);

        // 2. Convert DTOs to Entities for Transporters & Quotes
        List<Transporter> transporters = new ArrayList<>();
        for (TransporterDTO tDto : input.getTransporters()) {
            Transporter t = new Transporter();
            t.setId(tDto.getId());
            t.setName(tDto.getName());
            
            List<LaneQuote> quotes = new ArrayList<>();
            // Check if laneQuotes is not null to avoid NullPointerException
            if (tDto.getLaneQuotes() != null) {
                for (QuoteDTO qDto : tDto.getLaneQuotes()) {
                    LaneQuote q = new LaneQuote();
                    q.setLaneId(qDto.getLaneId());
                    q.setQuote(qDto.getQuote());
                    q.setTransporter(t); // Set the relationship
                    quotes.add(q);
                }
            }
            t.setLaneQuotes(quotes);
            transporters.add(t);
        }
        transporterRepository.saveAll(transporters);
    }

    // --- PART 2: ASSIGNMENT ALGORITHM ---
    public Map<String, Object> assignTransporters(int maxTransporters) {
        List<Lane> allLanes = laneRepository.findAll();
        List<Transporter> allTransporters = transporterRepository.findAll();
        
        double minTotalCost = Double.MAX_VALUE;
        List<Transporter> bestCombination = null;
        Map<Long, Long> bestAssignments = null;

        // Try combinations of size 1 up to maxTransporters
        for (int i = 1; i <= maxTransporters; i++) {
            List<List<Transporter>> combinations = generateCombinations(allTransporters, i);
            
            for (List<Transporter> combo : combinations) {
                if (coversAllLanes(combo, allLanes)) {
                    Map<String, Object> result = calculateCostForCombination(combo, allLanes);
                    double currentCost = (double) result.get("cost");
                    
                    if (currentCost < minTotalCost) {
                        minTotalCost = currentCost;
                        bestCombination = combo;
                        bestAssignments = (Map<Long, Long>) result.get("assignments");
                    }
                }
            }
        }

        Map<String, Object> response = new HashMap<>();
        if (bestCombination != null) {
            response.put("status", "success");
            response.put("totalCost", minTotalCost);
            
            List<Map<String, Long>> assignmentList = new ArrayList<>();
            for (Map.Entry<Long, Long> entry : bestAssignments.entrySet()) {
                Map<String, Long> map = new HashMap<>();
                map.put("laneId", entry.getKey());
                map.put("transporterId", entry.getValue());
                assignmentList.add(map);
            }
            response.put("assignments", assignmentList);
            
            List<Long> selectedIds = bestCombination.stream().map(Transporter::getId).collect(Collectors.toList());
            response.put("selectedTransporters", selectedIds);
        } else {
            response.put("status", "failure");
            response.put("message", "No valid combination found within limit.");
        }
        return response;
    }

    // Helper: Generate combinations
    private List<List<Transporter>> generateCombinations(List<Transporter> list, int k) {
        List<List<Transporter>> result = new ArrayList<>();
        combine(list, k, 0, new ArrayList<>(), result);
        return result;
    }

    private void combine(List<Transporter> list, int k, int start, List<Transporter> current, List<List<Transporter>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < list.size(); i++) {
            current.add(list.get(i));
            combine(list, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    // Helper: Check coverage
    private boolean coversAllLanes(List<Transporter> transporters, List<Lane> allLanes) {
        Set<Long> coveredLanes = new HashSet<>();
        for (Transporter t : transporters) {
            for (LaneQuote q : t.getLaneQuotes()) {
                coveredLanes.add(q.getLaneId());
            }
        }
        return coveredLanes.containsAll(allLanes.stream().map(Lane::getId).collect(Collectors.toSet()));
    }

    // Helper: Calculate best price
    private Map<String, Object> calculateCostForCombination(List<Transporter> transporters, List<Lane> lanes) {
        double totalCost = 0;
        Map<Long, Long> assignments = new HashMap<>();

        for (Lane lane : lanes) {
            double cheapestPrice = Double.MAX_VALUE;
            Long selectedId = null;

            for (Transporter t : transporters) {
                for (LaneQuote q : t.getLaneQuotes()) {
                    if (q.getLaneId().equals(lane.getId())) {
                        if (q.getQuote() < cheapestPrice) {
                            cheapestPrice = q.getQuote();
                            selectedId = t.getId();
                        }
                    }
                }
            }
            totalCost += cheapestPrice;
            assignments.put(lane.getId(), selectedId);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("cost", totalCost);
        res.put("assignments", assignments);
        return res;
    }
}