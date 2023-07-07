package com.stock.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.stock.model.Position;

@Repository
public interface PositionRepository extends CrudRepository<Position, Long> {
}
