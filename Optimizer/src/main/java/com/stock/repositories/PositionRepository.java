package com.stock.repositories;

import org.springframework.data.repository.CrudRepository;

import com.stock.model.Position;

//@Repository
public interface PositionRepository extends CrudRepository<Position, Long> {
//    List<Position> findAllUserCurrentPositions(Long userId);

//    public List<Position> findAll();
}
