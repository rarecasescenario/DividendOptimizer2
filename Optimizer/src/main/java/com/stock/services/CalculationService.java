package com.stock.services;

import java.util.List;
import com.stock.data.OutputDesicionData;
import com.stock.data.UserPosition;
import com.stock.model.UserData;
import com.stock.yahoo.SymbolCurrentState;

public interface CalculationService {

  List<OutputDesicionData> processData();

  List<String> getWorkList();

  List<String> getWatchSymbols();

  List<UserPosition> getUserPositions();

  List<SymbolCurrentState> getSymbolCurrentState();

  List<UserData> getUserData();

}
