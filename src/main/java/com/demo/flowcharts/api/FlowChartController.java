package com.demo.flowcharts.api;

import com.demo.flowcharts.model.FlowChartReq;
import com.demo.flowcharts.model.FlowChartRes;
import com.demo.flowcharts.service.FlowChartServiceable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flow-charts")
@RequiredArgsConstructor
public class FlowChartController {

   private final FlowChartServiceable flowChartService;

   @PostMapping
   public ResponseEntity<FlowChartRes> create(@RequestBody FlowChartReq req) {
      var res = flowChartService.create(req);
      return ResponseEntity.status(HttpStatus.CREATED).body(res);
   }

   @GetMapping("/{id}")
   public ResponseEntity<FlowChartRes> getById(@PathVariable("id") String id) {
      var res = flowChartService.getById(id);
      return ResponseEntity.ok(res);
   }

   @PatchMapping("/{id}")
   public ResponseEntity<FlowChartRes> update(@PathVariable("id") String id,
                                                @RequestParam(value = "operation", required = false, defaultValue = "add") String operation,
                                                @RequestBody FlowChartReq req) {
      FlowChartRes res = flowChartService.update(id, operation, req);
      return ResponseEntity.ok(res);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Object> deleteChart(@PathVariable("id") String id) {
      flowChartService.delete(id);
      return ResponseEntity.noContent().build();
   }
}
