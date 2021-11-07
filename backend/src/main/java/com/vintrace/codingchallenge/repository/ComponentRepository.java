package com.vintrace.codingchallenge.repository;

import com.vintrace.codingchallenge.repository.entity.ComponentEntity;
import com.vintrace.codingchallenge.repository.projection.IBreakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<ComponentEntity, Integer> {
    @Query(value = "select c.year as key, sum(c.percentage) as percentage from ComponentEntity c where c.wineLotCode = :wineLotCode group by c.year order by percentage desc")
    List<IBreakdown> getBreakdownByYear(@Param("wineLotCode") String wineLotCode);

    @Query(value = "select c.variety as key, sum(c.percentage) as percentage from ComponentEntity c where c.wineLotCode = :wineLotCode group by c.variety order by percentage desc")
    List<IBreakdown> getBreakdownByVariety(@Param("wineLotCode") String wineLotCode);

    @Query(value = "select c.region as key, sum(c.percentage) as percentage from ComponentEntity c where c.wineLotCode = :wineLotCode group by c.region order by percentage desc")
    List<IBreakdown> getBreakdownByRegion(@Param("wineLotCode") String wineLotCode);

    @Query(value = "select concat(c.year, '-', c.variety) as key, sum(c.percentage) as percentage from ComponentEntity c where c.wineLotCode = :wineLotCode group by c.year, c.variety order by percentage desc")
    List<IBreakdown> getBreakdownByYearVariety(@Param("wineLotCode") String wineLotCode);
}