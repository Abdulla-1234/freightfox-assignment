package com.freightfox.assignment.dto;

import lombok.Data;

@Data
public class QuoteDTO {
    private Long laneId;
    private Double quote; // The price
}