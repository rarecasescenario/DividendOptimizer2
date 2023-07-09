package com.stock.controllers;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.stock.data.OutputDesicionData;
import com.stock.data.UserPosition;
import com.stock.model.UserData;
import com.stock.services.CalculationService;
import com.stock.yahoo.SymbolCurrentState;

@RestController
@RequestMapping("/api")
public class OptimizerController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

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

  @GetMapping("/user-data")
  public @ResponseBody List<UserData> getUserData() {
    return calculationService.getUserData();
  }

  @GetMapping("/decision-data2")
  public @ResponseBody List<OutputDesicionData> getDesicionData2() {
    return calculationService.processData();
  }

  @GetMapping("/decision-data")
  public @ResponseBody List<OutputDesicionData> getDesicionData() {
    return calculationService.processData();
  }

  @GetMapping("/user-positions")
  public @ResponseBody List<UserPosition> getPositions() {
    List<UserPosition> up = calculationService.getUserPositions();
    if (up == null || up.size() == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Not Found");
    }
    return up;
  }

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(),
        String.format(OptimizerController.template, name));
  }
}
