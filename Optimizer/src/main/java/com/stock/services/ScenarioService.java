package com.stock.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stock.exceptions.ScenarioNotFoundException;
import com.stock.model.Scenario;
import com.stock.repositories.ScenarioRepository;

@Service
public class ScenarioService {

	private final ScenarioRepository scenarioRepository;

	public ScenarioService(ScenarioRepository scenarioRepository) {
		super();
		this.scenarioRepository = scenarioRepository;
	}
	
	
	public Scenario addScenario(Scenario scenario) {
        // Check values for nulls
		return scenarioRepository.save(scenario);
	}
	
	
	public List<Scenario> findAllScenarios() {
		return scenarioRepository.findAll();
	}
	
	
	public Scenario updateScenario(Scenario scenario) {
		return scenarioRepository.save(scenario);
	}
	
	
	public Scenario findScenarioById(Long id) {
		return scenarioRepository
				.findById(id)
				.orElseThrow(() -> new ScenarioNotFoundException("Sceanrio by id " + id + " was not found"));
	}
	
	
	public void deleteScenario(Long id) {
		scenarioRepository.deleteById(id);
	}
}
