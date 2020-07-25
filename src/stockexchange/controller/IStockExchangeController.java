package stockexchange.controller;

import stockexchange.model.StockExchangeModel;
import stockexchange.view.IStockExchangeView;

/**
 * Interface for the StockExchange controller. An implementation will work with the
 * IStockExchangeModel interface to provide all the functionalities of StockExchange model.
 */
public interface IStockExchangeController {

  /**
   * Start and begin to take input from user for the available functionalities for the
   * StockExchangeModel. This method checks all the available operations for the it and returns only
   * when the user decides to quit.
   *
   * @param model model of the StockExchange
   * @param view  view of the StockExchange
   */
  public void run(StockExchangeModel model, IStockExchangeView view) throws Exception;
}
