package com.demo.flowcharts.service;

import com.demo.flowcharts.entity.EdgeEntity;
import com.demo.flowcharts.entity.FlowChartEntity;
import com.demo.flowcharts.entity.NodeEntity;
import com.demo.flowcharts.exception.FlowChartException;
import com.demo.flowcharts.mapper.FlowChartMapper;
import com.demo.flowcharts.model.FlowChartReq;
import com.demo.flowcharts.model.FlowChartRes;
import com.demo.flowcharts.repo.EdgeEntityRepository;
import com.demo.flowcharts.repo.FlowChartRepository;
import com.demo.flowcharts.repo.NodeEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.demo.flowcharts.exception.FlowChartException.badRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlowChartService implements FlowChartServiceable {

   private final EdgeEntityRepository edgeEntityRepo;
   private final FlowChartRepository flowChartRepo;
   private final NodeEntityRepository nodeRepo;

   @Override
   public FlowChartRes create(FlowChartReq req) {
      validate(req);
      FlowChartEntity savedEntity = flowChartRepo.save(FlowChartMapper.toCreate(req));
      List<NodeEntity> nodes = createNodes(savedEntity, req.getNodes());
      List<EdgeEntity> edges = createEdges(req, nodes);
      return FlowChartMapper.toRes(savedEntity, nodes, edges);
   }

   private List<EdgeEntity> createEdges(FlowChartReq req, List<NodeEntity> nodes) {
      var map = new HashMap<String, NodeEntity>();
      for (NodeEntity node : nodes) {
         map.put(node.getLabel(), node);
      }
      List<EdgeEntity> edges = req.getEdges().stream().map(e -> FlowChartMapper.toCreate(map.get(e.get(0)), map.get(e.get(1)))).toList();
      edgeEntityRepo.saveAll(edges);
      return edges;
   }

   private List<NodeEntity> createNodes(FlowChartEntity entity, Set<String> labels) {
      List<NodeEntity> nodes = labels.stream().map(label -> FlowChartMapper.toCreate(label, entity)).toList();
      return nodeRepo.saveAll(nodes);
   }

   @Override
   public FlowChartRes getById(String id) {
      FlowChartEntity flowChart = getFlowChartOrElseThrow(id);
      List<NodeEntity> nodes = nodeRepo.findAllByFlowChart(flowChart.getId());
      List<EdgeEntity> edges = edgeEntityRepo.findAllByNodeFrom(nodes.stream().map(NodeEntity::getId).toList());
      return FlowChartMapper.toRes(flowChart, nodes, edges);
   }

   private FlowChartEntity getFlowChartOrElseThrow(String id) {
      return flowChartRepo.findById(id)
              .orElseThrow(() -> FlowChartException.get(404, "Flow chart not found"));
   }

   @Override
   public FlowChartRes update(String id, String operation, FlowChartReq req) {
      return switch (operation) {
         case "add" -> add(id, req);
         case "remove" -> remove(id, req);
         default -> throw FlowChartException.get(400,"Invalid operation, please choose add/remove");
      };
   }

   private FlowChartRes remove(String id, FlowChartReq req) {
      getFlowChartOrElseThrow(id);
      List<NodeEntity> nodes = nodeRepo.findAllByFlowChart(id);
      List<NodeEntity> nodesToDelete = new ArrayList<>();
      if (req.getNodes() != null && !req.getNodes().isEmpty())
         nodesToDelete.addAll(nodes.stream().filter(n -> req.getNodes().contains(n.getLabel())).toList());

      var edges = edgeEntityRepo.findAllByNodeFrom(nodes.stream().map(NodeEntity::getId).toList());
      List<EdgeEntity> edgesToDelete = edges.stream()
              .filter(e -> {
                         NodeEntity from = e.getNodeFrom();
                         NodeEntity to = e.getNodeTo();
                         return (req.getEdges() != null && (req.getEdges().contains(List.of(from.getLabel(), to.getLabel())))
                                 || nodesToDelete.contains(from)
                                 || nodesToDelete.contains(to));
                      }
              ).toList();
      edgeEntityRepo.deleteAll(edgesToDelete);
      nodeRepo.deleteAll(nodesToDelete);
      return getById(id);
   }

   public FlowChartRes add(String id, FlowChartReq req) {
      FlowChartEntity flowChart = getFlowChartOrElseThrow(id);
      if (req.getName() != null) {
         flowChart.setName(req.getName());
         flowChartRepo.save(flowChart);
      }
      var nodes = getAllByFlowChart(flowChart);
      var edges = edgeEntityRepo.findAllByNodeFrom(nodes.stream().map(NodeEntity::getId).toList());

      validate(req, nodes, edges);

      if (req.getNodes() != null && !req.getNodes().isEmpty()) {
         nodes.addAll(createNodes(flowChart, req.getNodes()));
      }
      if (req.getEdges() != null && !req.getEdges().isEmpty()) {
         edges.addAll(createEdges(req, nodes));
      }
      return FlowChartMapper.toRes(flowChart, nodes, edges);
   }

   private static void validate(FlowChartReq req, List<NodeEntity> nodes, List<EdgeEntity> edges) {
      if (req.getNodes() != null) {
         var existingNodes = nodes.stream().map(NodeEntity::getLabel).collect(Collectors.toSet());
         if (req.getNodes() != null && req.getNodes().stream().anyMatch(existingNodes::contains))
            throw badRequest("Some of the given nodes already present.");
      }

      if (req.getEdges() != null) {
         var existingEdges = edges.stream().map(e -> List.of(e.getNodeFrom().getLabel(), e.getNodeTo().getLabel())).collect(Collectors.toSet());
         req.getEdges().forEach(e -> {
            if (existingEdges.contains(e) || existingEdges.contains(List.of(e.get(1), e.get(0))))
               throw badRequest("Edge already present between nodes " + e.get(0) + " & " + e.get(1));
         });
      }
   }

   public List<NodeEntity> getAllByFlowChart(FlowChartEntity flowChart) {
      return nodeRepo.findAllByFlowChart(flowChart.getId());
   }

   @Override
   @Transactional
   public void delete(String id) {
      FlowChartEntity flowChart = getFlowChartOrElseThrow(id);
      List<NodeEntity> nodes = nodeRepo.findAllByFlowChart(flowChart.getId());
      edgeEntityRepo.deleteByNodeFromOrNodeTo(nodes.stream().map(NodeEntity::getId).toList());
      nodeRepo.deleteAll(nodes);
      flowChartRepo.delete(flowChart);
   }

   private void validate(FlowChartReq req) {
      Set<String> nodes = req.getNodes();

      Set<List<String>> edges = req.getEdges();
      edges.forEach(e -> {
         if (e.size() != 2)
            throw badRequest("Edges should have exactly 2 nodes");

         if (!nodes.contains(e.get(0)) || !nodes.contains(e.get(1)))
            throw badRequest("Edge should have only nodes present in nodes list");

         if (edges.contains(List.of(e.get(1), e.get(0))))
            throw badRequest("Only 1 edge should present between any 2 nodes, but 2 present between nodes " + e.get(0) + " & " + e.get(1));
      });
   }
}
