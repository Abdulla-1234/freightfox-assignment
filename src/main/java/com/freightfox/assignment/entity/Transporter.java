package com.freightfox.assignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transporters")
public class Transporter {
    @Id
    private Long id; // We use the ID provided in the JSON
    private String name;

    // A transporter has many quotes (prices) for different lanes
    @OneToMany(mappedBy = "transporter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LaneQuote> laneQuotes = new ArrayList<>();
}