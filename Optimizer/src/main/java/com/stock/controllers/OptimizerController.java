package com.stock.controllers;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.stock.data.OutputDesicionData;
import com.stock.model.Position;
import com.stock.repositories.PositionRepository;
import com.stock.services.CalculationService;
import com.stock.yahoo.SymbolCurrentState;

@RestController
@RequestMapping("/api")
public class OptimizerController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @Autowired
  private PositionRepository positionRepository;
  @Autowired
  CalculationService calculationService;

  private static Logger logger = LogManager.getLogger(OptimizerController.class);


  @GetMapping("/symbol-current-state")
  public @ResponseBody List<SymbolCurrentState> getYahooState() {
    return calculationService.getSymbolCurrentState();
  }

  @GetMapping("/watch-symbols")
  public @ResponseBody List<String> getWatchSymbols() {
    return calculationService.getWatchSymbols();
  }


  @GetMapping("/positions")
  public @ResponseBody Iterable<Position> getPositionByUser() {
    Iterable<Position> p = positionRepository.findAll();
    List<Position> result = Streamable.of(p).toList();
    OptimizerController.logger.info("All current postions");
    return result;
  }

  @GetMapping("/full-data")
  public @ResponseBody Iterable<OutputDesicionData> getFullData() {
    Iterable<OutputDesicionData> result = null;
    // Call Processing Service here

    return result;
  }


  @GetMapping("/all-current-positions")
  public @ResponseBody Iterable<Position> getAllCurrentPositions() {
    Iterable<Position> positions = positionRepository.findAll();
    OptimizerController.logger.info("All current postions");
    return positions;
  }

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(),
        String.format(OptimizerController.template, name));
  }
}
