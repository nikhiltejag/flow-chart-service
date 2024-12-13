package com.demo.flowcharts.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class HealthController {
   @GetMapping("/health")
   public ResponseEntity<Object> getHealth() {
      return ResponseEntity.ok(Map.of(
              "status", "Success",
              "message", "FlowChartsApplication is up and running..."
      ));
   }
}
