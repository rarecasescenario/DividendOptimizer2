package com.stock.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import com.stock.data.OutputDesicionData;
import com.stock.data.UserPosition;
import com.stock.model.Position;
import com.stock.model.UserData;
import com.stock.model.WatchSymbol;
import com.stock.repositories.PositionRepository;
import com.stock.repositories.UserDataRepository;
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
  @Autowired
  private PositionRepository positionRepository;
  @Autowired
  UserDataRepository userDataRepository;

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
  public List<OutputDesicionData> getDesicionData() {
    return desicionData;
  }


  @Override
  public List<UserPosition> getUserPositions() {
    Iterable<Position> p = positionRepository.findAll();
    List<Position> srcData = Streamable.of(p).toList();
    List<UserPosition> avgData = new ArrayList<>();

    HashSet<String> workList =
        srcData.stream().map(t -> t.getSymbol()).collect(Collectors.toCollection(HashSet::new));
    for (String s : workList) {
      CalculationServiceImpl.log.info("Processing symbol: " + s);
      BigDecimal totalSum = srcData.stream().filter(x -> x.getSymbol().equalsIgnoreCase(s))
          .map(t -> t.getPrice().multiply(BigDecimal.valueOf(t.getShares())).add(t.getCommission()))
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      int totalShares = srcData.stream().filter(x -> x.getSymbol().equalsIgnoreCase(s))
          .mapToInt(x -> x.getShares()).sum();

      BigDecimal avgPrice =
          totalSum.divide(BigDecimal.valueOf(totalShares), RoundingMode.HALF_EVEN);

      avgData.add(new UserPosition(s, totalShares, avgPrice));

      System.out.println(
          s + " = " + totalSum + " shares: " + totalShares + " Averagge Price: " + avgPrice);

    }
    return avgData;
  }

  @Override
  public List<UserData> getUserData() {
    Iterable<UserData> p = userDataRepository.findAll();
    return Streamable.of(p).toList();
  }
}
