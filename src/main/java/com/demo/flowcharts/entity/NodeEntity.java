package com.demo.flowcharts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nodes",
uniqueConstraints = {@UniqueConstraint(name = "distinct_nodes_for_a_flow_chart", columnNames = {"flow_chart_id", "label"})})
public class NodeEntity {

   @Id
   @Builder.Default
   @Column(name = "id", nullable = false)
   private String id = UUID.randomUUID().toString();

   @Column
   private String label;

   @ManyToOne
   @JoinColumn(name = "flow_chart_id")
   private FlowChartEntity flowChart;
}
