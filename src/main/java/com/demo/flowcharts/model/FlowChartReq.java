package com.demo.flowcharts.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class FlowChartReq {

   private String name;
   private Set<String> nodes;

   @Schema
   private Set<List<String>> edges;
}
