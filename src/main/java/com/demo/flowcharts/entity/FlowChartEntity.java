package com.demo.flowcharts.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "flow_charts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowChartEntity {

   @Id
   @Builder.Default
   @Column(name = "id", nullable = false)
   private String id = UUID.randomUUID().toString();

   @Column
   private String name;

//   @OneToMany(mappedBy = "flowChart", cascade = CascadeType.ALL)
//   @Builder.Default
//   private List<NodeEntity> nodes = new ArrayList<>();

//   public void addNode(NodeEntity node) {
//      this.nodes.add(node);
//      node.setFlowChart(this);
//   }
}
