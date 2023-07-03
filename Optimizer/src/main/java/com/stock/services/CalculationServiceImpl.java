package com.stock.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import com.stock.data.CurrentPosition;
import com.stock.data.OutputDesicionData;
import com.stock.model.WatchSymbol;
import com.stock.repositories.WatchSymbolRepository;
import com.stock.yahoo.CurrentYahooData;
import com.stock.yahoo.SymbolCurrentState;

@Service
public class CalculationServiceImpl implements CalculationService {

  private static final Logger log = LogManager.getLogger(CalculationServiceImpl.class);

  @Autowired
  CurrentYahooData currentYahooData;

  @Autowired
  WatchSymbolRepository watchSymbolRepository;

  private List<WatchSymbol> watchSymbols;

  // built localy
  private List<String> symbolWorkList;
  // current yahoo data
  private List<SymbolCurrentState> symbolCurrentState;
  private List<OutputDesicionData> desicionData;

  public CalculationServiceImpl() {
    super();
  }

  public CalculationServiceImpl(List<WatchSymbol> watchSymbols, List<String> symbolWorkList,
      List<SymbolCurrentState> symbolCurrentState, List<OutputDesicionData> desicionData) {
    super();
    this.watchSymbols = watchSymbols;
    this.symbolWorkList = symbolWorkList;
    this.symbolCurrentState = symbolCurrentState;
    this.desicionData = desicionData;
  }

  /**
   *
   * @return
   */
  @Override
  public List<OutputDesicionData> processData() {

    List<String> workList = getWorkList();

    for (int i = 0; i < watchSymbols.size(); i++) {

      String symbol = watchSymbols.get(i).getSymbol();
      Optional<SymbolCurrentState> symbolState = symbolCurrentState.stream()
          .filter(t -> t.getSymbol().equalsIgnoreCase(symbol)).findFirst();
    }
    return desicionData;
  }

  /**
   * Getting Symbol work list derived from the watch list
   */
  @Override
  public List<String> getWorkList() {
    if (watchSymbols != null && !watchSymbols.isEmpty()) {
      return watchSymbols.stream().map(t -> t.getSymbol()).collect(Collectors.toList());
    }
    return null;
  }

  @Override
  public List<String> getWatchSymbols() {
    Iterable<WatchSymbol> p = watchSymbolRepository.findAll();
    List<WatchSymbol> result = Streamable.of(p).toList();
    result.forEach(t -> {
      CalculationServiceImpl.log.info(t.toString());
    });
    List<String> r = result.stream().map(t -> t.getSymbol()).collect(Collectors.toList());
    CalculationServiceImpl.log.info(r.toString());
    return r;
  }

  @Override
  public void setWatchSymbols(List<WatchSymbol> watchSymbols) {
    this.watchSymbols = watchSymbols;
  }


  @Override
  public List<String> getSymbolWorkList() {
    return symbolWorkList;
  }

  // public void setSymbolWorkList(List<String> symbolWorkList) {
  // this.symbolWorkList = symbolWorkList;
  // }

  /**
   * Getting Yahoo current state of the list of symbol.
   *
   */
  @Override
  public List<SymbolCurrentState> getSymbolCurrentState() {

    // 1. Get work list of symbols
    List<String> symbols = getWatchSymbols();
    List<SymbolCurrentState> symbolCurrentState = null;
    try {
      symbolCurrentState = currentYahooData.getData(symbols);
      CalculationServiceImpl.log.info(symbolCurrentState.toString());
    } catch (InterruptedException | ExecutionException e) {
      CalculationServiceImpl.log
          .error("Cannot get a watch symbols from the database: " + e.getMessage());
      e.printStackTrace();
    }
    return symbolCurrentState;
  }

  @Override
  public void setSymbolCurrentState(List<SymbolCurrentState> symbolCurrentState) {
    this.symbolCurrentState = symbolCurrentState;
  }

  @Override
  public List<OutputDesicionData> getDesicionData() {
    return desicionData;
  }

  @Override
  public void setDesicionData(List<OutputDesicionData> desicionData) {
    this.desicionData = desicionData;
  }

  @Override
  public List<CurrentPosition> getCurrentPositions() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setCurrentPositions(List<CurrentPosition> currentPositions) {
    // TODO Auto-generated method stub

  }
}
