package com.stock.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.model.Scenario;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

	Optional<Scenario> findScenarioById(Long id);
}
