package com.demo.flowcharts.repo;

import com.demo.flowcharts.entity.EdgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdgeEntityRepository extends JpaRepository<EdgeEntity, String> {

   @Query("select e from EdgeEntity e where e.nodeFrom.id in ?1")
   List<EdgeEntity> findAllByNodeFrom(List<String> nodeFromId);

   @Modifying
   @Query("delete from EdgeEntity e where e.nodeFrom.id in ?1")
   void deleteByNodeFrom(List<String> list);

   @Modifying
   @Query("delete from EdgeEntity e where e.nodeFrom.id in ?1 or e.nodeTo.id in ?1")
   void deleteByNodeFromOrNodeTo(List<String> nodeIds);
}
