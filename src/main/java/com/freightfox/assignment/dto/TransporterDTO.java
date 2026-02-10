package com.freightfox.assignment.dto;

import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class TransporterDTO {
    private Long id;
    private String name;

    // The JSON field is called "laneQuotes", so we map it here
    @JsonProperty("laneQuotes")
    private List<QuoteDTO> laneQuotes;
}