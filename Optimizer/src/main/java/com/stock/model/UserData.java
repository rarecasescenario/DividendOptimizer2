package com.stock.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_DATA")
public class UserData {

  @Id
  @Column(name = "USER_ID")
  private Long userId;
  @Column(name = "INVESTED_AMOUNT")
  private BigDecimal investedAmount;
  @Column(name = "AVAILABLE_CASH")
  private BigDecimal availableCash;
  @Column(name = "UPDATED_ON")
  private LocalDateTime updatedOn;

  public UserData() {
    super();
  }

  public UserData(Long userId, BigDecimal investedAmount, BigDecimal availableCash) {
    super();
    this.userId = userId;
    this.investedAmount = investedAmount;
    this.availableCash = availableCash;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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

  @Override
  public String toString() {
    return "UserData [userId=" + userId + ", investedAmount=" + investedAmount + ", availableCash="
        + availableCash + ", updatedOn=" + updatedOn + "]";
  }
}
