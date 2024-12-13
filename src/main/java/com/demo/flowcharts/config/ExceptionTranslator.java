package com.demo.flowcharts.config;

import com.demo.flowcharts.exception.FlowChartException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

   @ExceptionHandler(value = DataIntegrityViolationException.class)
   public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
      return ResponseEntity.status(400)
              .body(Map.of(
                      "status", "error",
                      "message", "Duplicate data found please check the chart and input"));
   }

   @ExceptionHandler(value = FlowChartException.class)
   public ResponseEntity<Object> handleFlowChartException(FlowChartException ex) {
      return ResponseEntity.status(ex.getStatus()).body(Map.of("status", "error", "message", ex.getMessage()));
   }

   @ExceptionHandler(value = RuntimeException.class)
   public ResponseEntity<Object> handleRunTimeException(RuntimeException ex) {
      return ResponseEntity.status(400).body(Map.of("status", "error", "message", ex.getMessage()));
   }
}
