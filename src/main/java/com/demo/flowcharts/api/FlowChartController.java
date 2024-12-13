package com.demo.flowcharts.api;

import com.demo.flowcharts.model.ErrorRes;
import com.demo.flowcharts.model.FlowChartReq;
import com.demo.flowcharts.model.FlowChartRes;
import com.demo.flowcharts.service.FlowChartServiceable;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

@OpenAPIDefinition(info = @Info(title = "Flow chart APIs", description = "APIs to create, update, fetch, delete flow charts."))
@RestController
@RequestMapping("/flow-charts")
@RequiredArgsConstructor
public class FlowChartController {

   private final FlowChartServiceable flowChartService;

   @Operation(
           method = "POST",
           summary = "Creates Flow Chart",
           description = "Creates flow chart with given name, nodes and edges. It validates the request first and then create the resources",
           requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                   content = @Content(schema = @Schema(implementation = FlowChartReq.class)),
                   required = true),
           responses = {
                   @ApiResponse(
                           responseCode = "200",
                           description = "Created flow chart",
                           content = @Content(schema = @Schema(accessMode = Schema.AccessMode.READ_WRITE, implementation = FlowChartRes.class))
                   ),
                   @ApiResponse(
                           responseCode = "400",
                           description = "Error response",
                           content = @Content(schema = @Schema(accessMode = Schema.AccessMode.READ_ONLY, implementation = ErrorRes.class))
                   ),
           }
   )
   @PostMapping
   public ResponseEntity<FlowChartRes> create(@RequestBody FlowChartReq req) {
      var res = flowChartService.create(req);
      return ResponseEntity.status(HttpStatus.CREATED).body(res);
   }

   @Operation(
           method = "GET",
           summary = "Fetched Flow Chart",
           description = "Fetched flow chart with given id",
           responses = {
                   @ApiResponse(
                           responseCode = "200",
                           description = "Fetches flow chart",
                           content = @Content(schema = @Schema(accessMode = Schema.AccessMode.READ_WRITE, implementation = FlowChartRes.class))
                   ),
                   @ApiResponse(
                           responseCode = "404",
                           description = "Error response if no flow chart exits given id",
                           content = @Content(schema = @Schema(accessMode = Schema.AccessMode.READ_ONLY, implementation = ErrorRes.class))
                   ),
           }
   )
   @GetMapping("/{id}")
   public ResponseEntity<FlowChartRes> getById(@PathVariable("id") String id) {
      var res = flowChartService.getById(id);
      return ResponseEntity.ok(res);
   }

   @Operation(
           method = "PATCH",
           summary = "Updates Flow Chart",
           description = "Updates flow chart with given name, nodes and edges. It validates the request first and then updates the resources",
           requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                   content = @Content(schema = @Schema(implementation = FlowChartReq.class)),
                   required = true),
           responses = {
                   @ApiResponse(
                           responseCode = "200",
                           description = "Created flow chart",
                           content = @Content(schema = @Schema(accessMode = Schema.AccessMode.READ_WRITE, implementation = FlowChartRes.class))
                   ),
                   @ApiResponse(
                           responseCode = "400",
                           description = "Error response",
                           content = @Content(schema = @Schema(accessMode = Schema.AccessMode.READ_ONLY, implementation = ErrorRes.class))
                   ),
           }
   )
   @PatchMapping("/{id}")
   public ResponseEntity<FlowChartRes> update(@PathVariable("id") String id,
                                                @RequestParam(value = "operation", required = false, defaultValue = "add") String operation,
                                                @RequestBody FlowChartReq req) {
      FlowChartRes res = flowChartService.update(id, operation, req);
      return ResponseEntity.ok(res);
   }

   @Operation(
           method = "DELETE",
           summary = "Deletes Flow Chart",
           description = "Deletes flow chart with given id. It validates the request first and then deletes the entire flow chart",
           responses = {
                   @ApiResponse(
                           responseCode = "204",
                           description = "Deleted flow chart"
                   ),
                   @ApiResponse(
                           responseCode = "404",
                           description = "Error response if no flow chart exits given id",
                           content = @Content(schema = @Schema(accessMode = Schema.AccessMode.READ_ONLY, implementation = ErrorRes.class))
                   ),
           }
   )
   @DeleteMapping("/{id}")
   public ResponseEntity<Object> deleteChart(@PathVariable("id") String id) {
      flowChartService.delete(id);
      return ResponseEntity.noContent().build();
   }
}
