package com.demo.flowcharts.repo;

import com.demo.flowcharts.entity.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface NodeEntityRepository extends JpaRepository<NodeEntity, String> {
   @Query("select n from NodeEntity n where n.flowChart.id in ?1")
   List<NodeEntity> findAllByFlowChart(String flowChartId);

   @Query("select n from NodeEntity n where n.flowChart.id = ?1 and n.label in ?2")
   List<NodeEntity> findAllByFlowChartAndLabels(String flowChartId, Set<String> nodes);
}
