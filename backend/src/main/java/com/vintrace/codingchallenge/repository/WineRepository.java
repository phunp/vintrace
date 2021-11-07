package com.vintrace.codingchallenge.repository;

import com.vintrace.codingchallenge.repository.entity.WineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WineRepository extends JpaRepository<WineEntity, Integer> {
    WineEntity findOneByLotCode(String lotCode);
}
