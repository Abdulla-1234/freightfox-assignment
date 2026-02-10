package com.freightfox.assignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lane_quotes")
public class LaneQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID for this specific price entry

    private Long laneId; // The ID of the lane this price is for
    private Double quote; // The cost (e.g., 5000.00)

    @ManyToOne
    @JoinColumn(name = "transporter_id")
    @JsonIgnore // Important: Prevents infinite loops when converting to JSON
    private Transporter transporter;
}