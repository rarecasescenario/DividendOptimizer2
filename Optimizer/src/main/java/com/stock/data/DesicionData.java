package com.stock.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class DesicionData implements Serializable {

  private BigDecimal quaterlyDividendAmount;
  private BigDecimal accountTotal;
  private BigDecimal availableCash;
  private BigDecimal investedAmount;
  private BigDecimal Profit;

  private List<OutputDesicionData> desicionData;

  public DesicionData() {
    super();
  }

  public BigDecimal getQuaterlyDividendAmount() {
    return quaterlyDividendAmount;
  }

  public void setQuaterlyDividendAmount(final BigDecimal quaterlyDividendAmount) {
    this.quaterlyDividendAmount = quaterlyDividendAmount;
  }

  public BigDecimal getAccountTotal() {
    return accountTotal;
  }

  public void setAccountTotal(final BigDecimal accountTotal) {
    this.accountTotal = accountTotal;
  }

  public BigDecimal getAvailableCash() {
    return availableCash;
  }

  public void setAvailableCash(final BigDecimal availableCash) {
    this.availableCash = availableCash;
  }

  public BigDecimal getInvestedAmount() {
    return investedAmount;
  }

  public void setInvestedAmount(final BigDecimal investedAmount) {
    this.investedAmount = investedAmount;
  }

  public BigDecimal getProfit() {
    return Profit;
  }

  public void setProfit(final BigDecimal profit) {
    Profit = profit;
  }

  public List<OutputDesicionData> getDesicionData() {
    return desicionData;
  }

  public void setDesicionData(final List<OutputDesicionData> desicionData) {
    this.desicionData = desicionData;
  }
}
