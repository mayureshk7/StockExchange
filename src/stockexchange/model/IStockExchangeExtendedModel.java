package stockexchange.model;

/**
 * This is the interface of the StockExchangeExtendedModel. It offers all the methods and operations
 * that can be performed additional to the existing functionalities provided by
 * IStockExchangeModel.
 */
public interface IStockExchangeExtendedModel extends IStockExchangeModel {
  /**
   * Displays all the details of the specified existing portfolios before specified date.
   *
   * @param date date on which data is to be extracted
   * @return the details of the portfolio
   * @throws IllegalArgumentException if the portfolio on which this operation needs to be performed
   *                                  is not selected
   */
  String getCurrentPortfolioDetails(String date) throws IllegalArgumentException;

  /**
   * This method is responsible for giving cost basis at current time.
   *
   * @return cost basis as of today
   */
  String getCurrentCostBasis() throws IllegalArgumentException;

  /**
   * This method is responsible for giving cost basis on certain date.
   *
   * @param date on which cost basis is required
   * @return cost basis on certain date
   */
  String getCostBasisOnDate(String date) throws IllegalArgumentException;

  /**
   * Invests the given amount in the existing shares by distributing amount equally.
   *
   * @param totalAmount amount to be invested
   * @param date        date on which investment is to be done
   * @return status after investment
   */
  String investMoney(double totalAmount, String date);

  /**
   * Invests the given amount in the existing shares using the percentage splits.
   *
   * @param totalAmount amount to be investment
   * @param date        date on which investment is to be done
   * @param percentage  split in which investment is to be made
   * @return status after investment
   */
  String investMoney(double totalAmount, String date, double[] percentage);


  /**
   * Invests the total amount regularly from starting date in the existing shares in regular
   * intervals.
   *
   * @param totalAmount amount to be invested
   * @param date        on which investment is to be done
   * @param interval    in which investment is to be done
   * @return status after the operation
   */
  String regularInvestMoney(double totalAmount, String date, int interval);

  /**
   * Invest the total amount regularly from starting date till ending date in the existing in
   * regular intervals.
   *
   * @param totalAmount amount to be invested
   * @param startDate   from which investment is to be done
   * @param endDate     till which investment is to be done
   * @param interval    in which investment is to be done
   * @return status after investment
   */
  String regularInvestMoney(double totalAmount, String startDate,
                            String endDate, int interval);

  /**
   * Invests the total amount regularly from starting date in the existing shares in regular
   * intervals using percentage splits.
   *
   * @param totalAmount       amount to be invested
   * @param date              on which investment is to be done
   * @param interval          in which investment is to be done
   * @param percentageWeights percentage split in which investment is to be done
   * @return status after the operation
   */
  String regularInvestMoneyWithPercentage(double totalAmount, String date,
                                          int interval, double[] percentageWeights);

  /**
   * Invest the total amount regularly from starting date till ending date in the existing in
   * regular intervals using percentage split.
   *
   * @param totalAmount       amount to be invested
   * @param startDate         from which investment is to be done
   * @param endDate           till which investment is to be done
   * @param interval          in which investment is to be done
   * @param percentageWeights percentage split in which investment is to be done
   * @return status after investment
   */
  String regularInvestMoneyWithPercentage(double totalAmount, String startDate,
                                          String endDate, int interval,
                                          double[] percentageWeights);
}
