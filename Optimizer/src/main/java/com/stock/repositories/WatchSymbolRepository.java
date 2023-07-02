package com.stock.repositories;

import org.springframework.data.repository.CrudRepository;
import com.stock.model.WatchSymbol;

public interface WatchSymbolRepository extends CrudRepository<WatchSymbol, String> {
}
