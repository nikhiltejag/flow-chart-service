package com.demo.flowcharts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRes {

   @Builder.Default
   private String status = "error";
   private String message;

   public static ErrorRes getInstance(String message) {
      ErrorRes errorRes = new ErrorRes();
      errorRes.setMessage(message);
      return errorRes;
   }
}
