package com.demo.flowcharts.mapper;

import com.demo.flowcharts.entity.EdgeEntity;
import com.demo.flowcharts.entity.FlowChartEntity;
import com.demo.flowcharts.entity.NodeEntity;
import com.demo.flowcharts.model.FlowChartReq;
import com.demo.flowcharts.model.FlowChartRes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FlowChartMapper {
   public static FlowChartRes toRes(FlowChartEntity entity, List<NodeEntity> nodes, List<EdgeEntity> edges) {
      return FlowChartRes.builder()
              .id(entity.getId())
              .name(entity.getName())
              .nodes(toRes(nodes))
              .edges(toEdgesRes(edges))
              .build();
   }

   public static List<List<String>> toEdgesRes(List<EdgeEntity> edges) {
      return edges.stream().map(e -> List.of(e.getNodeFrom().getLabel(), e.getNodeTo().getLabel())).toList();
   }

   public static Set<String> toRes(List<NodeEntity> nodes) {
      return nodes.stream().map(NodeEntity::getLabel).collect(Collectors.toSet());
   }

   public static EdgeEntity toCreate(NodeEntity from, NodeEntity to) {
      return EdgeEntity.builder().nodeFrom(from).nodeTo(to).build();
   }

   public static FlowChartEntity toCreate(FlowChartReq req) {
      return FlowChartEntity.builder()
              .name(req.getName())
              .build();
   }

   public static NodeEntity toCreate(String node, FlowChartEntity chartEntity) {
      return NodeEntity.builder().label(node).flowChart(chartEntity).build();
   }
}
