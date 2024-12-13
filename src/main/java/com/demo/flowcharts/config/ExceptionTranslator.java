package com.demo.flowcharts.config;

import com.demo.flowcharts.exception.FlowChartException;
import com.demo.flowcharts.model.ErrorRes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

   @ExceptionHandler(value = DataIntegrityViolationException.class)
   public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
      return ResponseEntity.status(400)
              .body(ErrorRes.getInstance("Duplicate data found please check the chart and input"));
   }

   @ExceptionHandler(value = FlowChartException.class)
   public ResponseEntity<Object> handleFlowChartException(FlowChartException ex) {
      return ResponseEntity.status(ex.getStatus()).body(ErrorRes.getInstance(ex.getMessage()));
   }

   @ExceptionHandler(value = RuntimeException.class)
   public ResponseEntity<Object> handleRunTimeException(RuntimeException ex) {
      return ResponseEntity.status(400).body(ErrorRes.getInstance(ex.getMessage()));
   }
}
