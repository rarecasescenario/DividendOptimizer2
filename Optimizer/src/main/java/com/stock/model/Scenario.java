package com.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SCENARIO")
public class Scenario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, updatable=false)
	private Long id;
	@Column(name="SCENARIO_NAME")
	private String scenarioName;
	@Column(name="INVESTED_AMOUNT")
	private BigDecimal investedAmount;
	@Column(name="AVAILABLE_CASH")
	private BigDecimal availableCash;
	@Column(name = "INVESTED_ON")
	private LocalDateTime investedOn;
	@Column(name = "UPDATED_ON")
	private LocalDateTime updatedOn;
	
	public Scenario() {
		super();
	}

	public Scenario(Long id, String scenarioName, BigDecimal investedAmount, BigDecimal availableCash, LocalDateTime investedOn, LocalDateTime updatedOn) {
		super();
		id = id;
		this.scenarioName = scenarioName;
		this.investedAmount = investedAmount;
		this.availableCash = availableCash;
		this.updatedOn = updatedOn;
		this.investedOn = investedOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public BigDecimal getInvestedAmount() {
		return investedAmount;
	}

	public void setInvestedAmount(BigDecimal investedAmount) {
		this.investedAmount = investedAmount;
	}

	public BigDecimal getAvailableCash() {
		return availableCash;
	}

	public void setAvailableCash(BigDecimal availableCash) {
		this.availableCash = availableCash;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	public LocalDateTime getInvestedOn() {
		return investedOn;
	}

	public void setInvestedOn(LocalDateTime investedOn) {
		this.investedOn = investedOn;
	}

	@Override
	public String toString() {
		return "Scenario [id=" + id + ", scenarioName=" + scenarioName + ", investedAmount=" + investedAmount
				+ ", availableCash=" + availableCash + ", investedOn=" + investedOn + ", updatedOn=" + updatedOn + "]";
	}

}
