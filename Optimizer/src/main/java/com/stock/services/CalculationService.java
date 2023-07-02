package com.stock.services;

import java.util.List;
import com.stock.data.CurrentPosition;
import com.stock.data.OutputDesicionData;
import com.stock.model.WatchSymbol;
import com.stock.yahoo.SymbolCurrentState;

public interface CalculationService {

  List<OutputDesicionData> processData();

  List<String> getWorkList();

  List<String> getWatchSymbols();

  void setWatchSymbols(List<WatchSymbol> watchSymbols);

  List<CurrentPosition> getCurrentPositions();

  void setCurrentPositions(List<CurrentPosition> currentPositions);

  List<String> getSymbolWorkList();

  List<SymbolCurrentState> getSymbolCurrentState();

  void setSymbolCurrentState(List<SymbolCurrentState> symbolCurrentState);

  List<OutputDesicionData> getDesicionData();

  void setDesicionData(List<OutputDesicionData> desicionData);
}
