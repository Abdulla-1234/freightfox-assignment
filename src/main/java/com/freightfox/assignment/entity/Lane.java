package com.freightfox.assignment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lanes")
public class Lane {
    @Id
    private Long id; // We use the ID provided in the JSON (e.g., 1, 2)
    private String origin;
    private String destination;
}