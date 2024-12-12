package com.demo.flowcharts.repo;

import com.demo.flowcharts.entity.FlowChartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowChartRepository extends JpaRepository<FlowChartEntity, String> {
}
