package com.stock.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
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
import com.stock.data.DesicionData;
import com.stock.data.OutputDesicionData;
import com.stock.data.UserPosition;
import com.stock.model.Position;
import com.stock.model.UserData;
import com.stock.model.WatchSymbol;
import com.stock.repositories.PositionRepository;
import com.stock.repositories.UserDataRepository;
import com.stock.repositories.WatchSymbolRepository;
import com.stock.utils.Utility;
import com.stock.yahoo.CurrentYahooData;
import com.stock.yahoo.SymbolCurrentState;

@Service
public class CalculationServiceImpl implements CalculationService {

  private static final Logger log = LogManager.getLogger(CalculationServiceImpl.class);
  private static Logger dataLogger = LogManager.getLogger("data-log");

  @Autowired
  CurrentYahooData currentYahooData;
  @Autowired
  WatchSymbolRepository watchSymbolRepository;
  @Autowired
  private PositionRepository positionRepository;
  @Autowired
  UserDataRepository userDataRepository;

  public CalculationServiceImpl() {
    super();
  }

  /** TODO: Not in use*/
  public List<OutputDesicionData> processData2() {
 //   List<OutputDesicionData> posionData = new ArrayList<>();
    return null;
  }

  /**
   *
   * @return
   */
  @Override
  public DesicionData processData() {

    DesicionData desicionData = new DesicionData();

    List<OutputDesicionData> outputDesicionData = new ArrayList<>();

    Iterable<WatchSymbol> p = watchSymbolRepository.findAll();
    List<WatchSymbol> watchSymbols = Streamable.of(p).toList();

    // User symbols, shares with average prices
    List<UserPosition> userPositions = getUserPositions();
    userPositions.forEach(System.out::println);
    // Getting current Yahoo data
    List<SymbolCurrentState> symbolCurrentState = this.getSymbolCurrentState();

    List<UserData> userData = this.getUserData();

    // Calculate current assets
    BigDecimal availableCash = userData.get(0).getAvailableCash();
    BigDecimal investedAmount = userData.get(0).getInvestedAmount();

    BigDecimal totalDividends = new BigDecimal(0);
    BigDecimal totalAssets = new BigDecimal(0);
    BigDecimal totalAccount = new BigDecimal(0);
    String action = "";
    /* Result of comparing to zero for formatting */
    int res;
    int res2;
    int resHold;
    int resTotalAssets;
    int resDiv;
    int resSymbolQuaterlyDividends;
    int resSymbolProfit;

    System.out.println("\n" + new Utility().getDateTimeFormatter().format(LocalDateTime.now()));

    CalculationServiceImpl.dataLogger
        .info(new Utility().getDateTimeFormatter().format(LocalDateTime.now()));
    CalculationServiceImpl.dataLogger.info(
        "======================================================================================================================");
    CalculationServiceImpl.dataLogger.info(
        "| Symbol  |Shares|Avg.Price| Price  |  P/L    | Position,$| QDiv,$ | QDivAmt | UpperY| MidY | CYield| YDiff  | Action ");
    CalculationServiceImpl.dataLogger.info(
        "======================================================================================================================");

    for (int i = 0; i < watchSymbols.size(); i++) {

      OutputDesicionData odd = new OutputDesicionData();

      String symbol = watchSymbols.get(i).getSymbol();
      odd.setSymbol(symbol);

      Optional<SymbolCurrentState> symbolState = symbolCurrentState.stream()
          .filter(t -> t.getSymbol().equalsIgnoreCase(symbol)).findFirst();

      Optional<WatchSymbol> ws =
          watchSymbols.stream().filter(x -> x.getSymbol().equalsIgnoreCase(symbol)).findFirst();

      BigDecimal symbolQuaterlyDividends = new BigDecimal(0);
      BigDecimal upperYield = new BigDecimal(0);
      BigDecimal lowerYield = new BigDecimal(0);
      BigDecimal yield = new BigDecimal(0);
      BigDecimal middleOfYieldRange = new BigDecimal(0);
      BigDecimal yieldDiff = new BigDecimal(0);
      int numberOfShares = 0;
      BigDecimal symbolAveragePrice = new BigDecimal(0);
      BigDecimal symbolProfit = new BigDecimal(0);

      final Optional<UserPosition> cplR =
          userPositions.stream().filter(r -> r.getSymbol().equalsIgnoreCase(symbol)).findFirst();

      if (cplR != null && !cplR.isEmpty()) {
        numberOfShares = cplR.get().getNumberOfShares();
        symbolAveragePrice = cplR.get().getAveragePrice();

        symbolProfit = BigDecimal.valueOf(numberOfShares)
            .multiply(symbolState.get().getPrice().subtract(symbolAveragePrice));

        totalAssets = BigDecimal.valueOf(numberOfShares).multiply(symbolState.get().getPrice());
        symbolQuaterlyDividends =
            BigDecimal.valueOf(numberOfShares).multiply(ws.get().getQuoterlyDividendAmount());
        odd.setQuaterlyShareDividendAmount(symbolQuaterlyDividends);

        totalDividends = totalDividends.add(symbolQuaterlyDividends);
        totalAccount = totalAccount.add(totalAssets);
      } else {
        totalAssets = BigDecimal.valueOf(0.0);
      }

      upperYield = ws.get().getUpperYield();
      lowerYield = ws.get().getLowerYield();
      odd.setUpperYield(upperYield);
      odd.setLowerYield(lowerYield);

      if (symbolState.get().getPrice() == null) {
        continue;
      }

      yield = ws.get().getQuoterlyDividendAmount().multiply(BigDecimal.valueOf(400))
          .divide(symbolState.get().getPrice(), RoundingMode.HALF_EVEN);
      middleOfYieldRange =
          (upperYield.add(lowerYield)).divide(BigDecimal.valueOf(2), 3, RoundingMode.HALF_EVEN);
      odd.setMidleYield(middleOfYieldRange);

      yieldDiff = yield.subtract(middleOfYieldRange);

      // Action = "Strong Buy" if CYield is greater than UpYield
      res = yield.compareTo(ws.get().getUpperYield());
      res2 = ws.get().getUpperYield().compareTo(BigDecimal.valueOf(0.0));
      resHold = yield.compareTo(middleOfYieldRange);
      resSymbolProfit = symbolProfit.compareTo(BigDecimal.valueOf(0.0));

      if (res == 0 || res == 1 && res2 != 0) {
        action = "Buy";
      } else {
        action = "";
      }

      if (res2 == 0) {
        action = "";
      }

      if (resHold == 1 && res == -1) {
        action = "Hold";
      }
      if (resHold == -1) {
        action = "Sell";
      }
      odd.setAction(action);

      String strShares = (numberOfShares != 0) ? String.format("%3d", numberOfShares) : "   ";

      resSymbolQuaterlyDividends = symbolQuaterlyDividends.compareTo(BigDecimal.valueOf(0.0));
      String strSymbolAveragePrice =
          (resSymbolQuaterlyDividends != 0) ? String.format("%6.2f", symbolAveragePrice) : "   ";

      resTotalAssets = totalAssets.compareTo(BigDecimal.valueOf(0.0));
      String strPosition = (resTotalAssets == 1) ? String.format("%,9.2f", totalAssets) : "   ";

      resDiv = symbolQuaterlyDividends.compareTo(BigDecimal.valueOf(0.0));
      String strSymbolQDiv =
          (resDiv == 1) ? String.format("%6.2f", symbolQuaterlyDividends) : "   ";

      String strSymbolProfit =
          (resSymbolProfit == 1) ? String.format("% 6.2f", symbolProfit) : "   ";

      if (resSymbolProfit == -1) {
        strSymbolProfit = String.format("% 6.2f", symbolProfit);
      }



      // action = (numberOfShares == 0 && !action.equalsIgnoreCase("sell")) ? action :
      // " ";
      if (numberOfShares == 0 && action.equalsIgnoreCase("buy")) {
        // do nothing
      } else if (numberOfShares == 0 && action.equalsIgnoreCase("sell")) {
        action = "   ";
      } else if (numberOfShares == 0 && action.equalsIgnoreCase("hold")) {
        action = "   ";
      }


      String outputDecisionLine = String.format(
          "| %-7S | %4s |  %6s | %6.2f | %7s | %9s | %6.2f | %7s | %5.2f | %4.2f | %5.2f | % 5.3f | %-12s",
          symbol, strShares, strSymbolAveragePrice, symbolState.get().getPrice(), strSymbolProfit,
          strPosition, ws.get().getQuoterlyDividendAmount(), strSymbolQDiv, upperYield,
          middleOfYieldRange, yield, yieldDiff, action);

      // System.out.println(outputDecisionLine);
      CalculationServiceImpl.dataLogger.info(outputDecisionLine);
      // CalculationServiceImpl.log.info(outputDecisionLine);

      // System.out.printf(
      // "| %-7S | %4s | %6s | %6.2f | %9s | %6.2f | %7s | %5.2f | %4.2f | %5.2f | % 5.3f | %-12s
      // %n",
      // symbol, strShares, strSymbolAveragePrice, symbolState.get().getPrice(), strPosition,
      // ws.get().getQuoterlyDividendAmount(), strSymbolQDiv, upperYield, middleOfYieldRange,
      // yield, yieldDiff, action);
      odd.setShares(numberOfShares);
      odd.setSymbolAveragePrice(symbolAveragePrice);
      odd.setPrice(symbolState.get().getPrice());
      odd.setSymbolPosition(totalAssets);
      odd.setCurrentYield(yield);
      odd.setYieldDifference(yieldDiff);
      odd.setQuaterlyShareDividendAmount(ws.get().getQuoterlyDividendAmount());
      odd.setPositionDividendAmount(symbolQuaterlyDividends);

      outputDesicionData.add(odd);
    }
    totalAccount = totalAccount.add(availableCash);
    BigDecimal pl = totalAccount.subtract(investedAmount);
    CalculationServiceImpl.dataLogger.info(
        "======================================================================================================================");

    String strAccountSummary = String.format(
        "  Quaterly Dividends,$ %,8.2f  Account Total,$: %,10.2f", totalDividends, totalAccount);
    String strAvailableCash = String.format("  Available Cash: $%,10.2f", availableCash);
    String strPL =
        String.format("  Invested Amount: $%,10.2f    P/L: $%,10.2f", investedAmount, pl);

    CalculationServiceImpl.dataLogger.info(strAccountSummary);
    CalculationServiceImpl.dataLogger.info(strAvailableCash);
    CalculationServiceImpl.dataLogger.info(strPL);

    desicionData.setAccountTotal(totalAccount);
    desicionData.setAvailableCash(availableCash);
    desicionData.setProfit(pl);
    desicionData.setInvestedAmount(investedAmount);
    desicionData.setQuaterlyDividendAmount(totalDividends);
    desicionData.setPositionData(outputDesicionData);

    return desicionData;
  }

  /**
   * Getting Symbols work list derived from the watch list
   */
  @Override
  public List<String> getWorkList() {
    Iterable<WatchSymbol> p = watchSymbolRepository.findAll();
    List<WatchSymbol> watchSymbols = Streamable.of(p).toList();
    if (watchSymbols != null && !watchSymbols.isEmpty()) {
      return watchSymbols.stream().map(t -> t.getSymbol()).collect(Collectors.toList());
    }
    return null;
  }

  /**
   * Getting a list of Watch symbols with quoterly dividends, yiled ranges and converting them into
   */
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


  /**
   * Getting user symbols, shares with average prices
   */
  @Override
  public List<UserPosition> getUserPositions() {

    Iterable<Position> positions = positionRepository.findAll();
    List<Position> srcData = Streamable.of(positions).toList();
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
