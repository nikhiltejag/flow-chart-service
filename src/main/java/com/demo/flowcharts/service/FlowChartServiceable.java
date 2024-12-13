package com.demo.flowcharts.service;

import com.demo.flowcharts.model.FlowChartReq;
import com.demo.flowcharts.model.FlowChartRes;
import org.springframework.transaction.annotation.Transactional;

public interface FlowChartServiceable {
   FlowChartRes create(FlowChartReq req);

   FlowChartRes getById(String id);

   FlowChartRes update(String id, String operation, FlowChartReq req);

   @Transactional
   void delete(String id);
}
