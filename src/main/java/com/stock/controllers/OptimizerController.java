package com.stock.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stock.model.Position;
import com.stock.repositories.PositionRepository;

@RestController
@RequestMapping("/api")
public class OptimizerController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private PositionRepository positionRepository;

    private static Logger logger = LogManager.getLogger(OptimizerController.class);

//    @GetMapping("/all-current-positions")
//    public @ResponseBody Iterable<Position> getPositionByUser(String user) {
//	Iterable<Position> positions = positionRepository.
//	logger.info("All current postions");
//	return positions;
//    }

    @GetMapping("/all-current-positions")
    public @ResponseBody Iterable<Position> getAllCurrentPositions() {
	Iterable<Position> positions = positionRepository.findAll();
	logger.info("All current postions");
	return positions;
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
	return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
