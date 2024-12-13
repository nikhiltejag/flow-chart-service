package com.demo.flowcharts.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FlowChartException extends RuntimeException {

   private final HttpStatus status;
   private final String message;

   public FlowChartException(HttpStatus status, String message) {
      super();
      this.status = status;
      this.message = message;
   }

   public static FlowChartException get(int code, String message) {
      return new FlowChartException(HttpStatus.valueOf(code), message);
   }

   public static FlowChartException badRequest(String message) {
      return new FlowChartException(HttpStatus.BAD_REQUEST, message);
   }
}
