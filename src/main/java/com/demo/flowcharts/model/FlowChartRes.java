package com.demo.flowcharts.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class FlowChartRes {

   private String id;
   private String name;
   private Set<String> nodes;
   private List<List<String>> edges;
}
