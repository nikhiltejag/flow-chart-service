package com.demo.flowcharts.entity;

import jakarta.persistence.CascadeType;
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
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "edges",
        uniqueConstraints = {@UniqueConstraint(name = "distinct_edge_for_any_2_nodes", columnNames = {"node_from", "node_to"})})
public class EdgeEntity {

   @Id
   @Builder.Default
   @Column(name = "id", nullable = false)
   private String id = UUID.randomUUID().toString();

   @ManyToOne(cascade = CascadeType.PERSIST)
   @JoinColumn(name = "node_from")
   private NodeEntity nodeFrom;

   @ManyToOne(cascade = CascadeType.PERSIST)
   @JoinColumn(name = "node_to")
   private NodeEntity nodeTo;
}
