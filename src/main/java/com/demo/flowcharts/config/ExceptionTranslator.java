package com.demo.flowcharts.config;

import com.demo.flowcharts.exception.FlowChartException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.demo.flowcharts.mapper.FlowChartMapper.body;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

   private static final String ERROR_STATUS = "error";

   @ExceptionHandler(value = DataIntegrityViolationException.class)
   public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
      return ResponseEntity.status(400)
              .body(body(ERROR_STATUS, "Duplicate data found please check the chart and input"));
   }

   @ExceptionHandler(value = FlowChartException.class)
   public ResponseEntity<Object> handleFlowChartException(FlowChartException ex) {
      return ResponseEntity.status(ex.getStatus()).body(body( ERROR_STATUS, ex.getMessage()));
   }

   @ExceptionHandler(value = RuntimeException.class)
   public ResponseEntity<Object> handleRunTimeException(RuntimeException ex) {
      return ResponseEntity.status(400).body(body(ERROR_STATUS, ex.getMessage()));
   }
}
