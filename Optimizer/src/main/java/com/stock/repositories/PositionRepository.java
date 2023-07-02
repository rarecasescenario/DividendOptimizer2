package com.stock.repositories;

import org.springframework.data.repository.CrudRepository;
import com.stock.model.Position;

public interface PositionRepository extends CrudRepository<Position, Long> {
}
