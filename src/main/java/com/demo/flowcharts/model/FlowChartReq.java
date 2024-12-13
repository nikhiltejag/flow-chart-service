package com.demo.flowcharts.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class FlowChartReq {

   private String name;
   private Set<String> nodes;
   private Set<List<String>> edges;
}
