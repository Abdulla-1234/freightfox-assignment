package com.freightfox.assignment.dto;

import lombok.Data;
import java.util.List;

@Data
public class InputDTO {
    private List<LaneDTO> lanes;
    private List<TransporterDTO> transporters;
}