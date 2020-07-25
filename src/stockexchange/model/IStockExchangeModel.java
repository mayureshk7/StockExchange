package stockexchange.model;

import java.io.IOException;

/**
 * This is the interface of the StockExchangeModel. It offers all the methods and operations that
 * can be performed to create and edit a portfolio.
 */
public interface IStockExchangeModel {
  /**
   * Creates a portfolio with the given name.The user can perform various functions on this
   * portfolio like buy shares and add to this portfolio, sell shares present in this portfolio, get
   * the current contents of this portfolio and remove the portfolio.
   *
   * @param portfolioName the name with which the portfolio will be created.
   * @throws IllegalArgumentException if there already exists a portfolio with the given name
   */
  void createPortfolio(String portfolioName) throws IllegalArgumentException;

  /**
   * Selects a portfolio before performing and action on the intended portfolio. A portfolio needs
   * to be selected before it can be viewed or updated (Buy shares to add to a portfolio, sell
   * shares from the portfolio, remove the portfolio or actions like getCurrentPortfolioDetails.
   *
   * @param portfolioName the portfolio that needs to be selected
   * @throws IllegalArgumentException if there exists no portfolio with the given name for
   *                                  selection.
   */
  void selectPortfolio(String portfolioName) throws IllegalArgumentException;


  /**
   * Sets the commission of each transaction.
   *
   * @param commission commission on each transaction
   * @throws IllegalArgumentException if portfolio is not selected
   * @throws IllegalStateException    if the commission is negative
   */
  void setCommission(double commission) throws IllegalArgumentException, IllegalStateException;


  /**
   * Removes an existing portfolio with a given name.
   *
   * @param portfolioName the portfolio that needs to be deleted
   * @throws IllegalArgumentException if there exists no portfolio with the given name for deletion
   */
  void removePortfolio(String portfolioName) throws IllegalArgumentException;

  /**
   * Displays the details of the shares that the user intends to buy and add to his/her portfolio.
   *
   * @param nameOfShare is the ticker Symbol of the share that the user needs to know the
   *                    information about.
   * @param date        is the date on which the user needs to know the information about a
   *                    particular share
   * @return the details of the requested Stock - Ticker Symbol of the share opening Price - Highest
   *         price of the Day - Lowest Price of the day - Closing Price - Volume of the shares
   * @throws IllegalArgumentException if the date given is not in the specified format or the given
   *                                  date is a weekend or a national holiday or if the portfolio is
   *                                  not selected
   */
  String displayShareDetails(String nameOfShare, String date) throws IllegalArgumentException,
          IOException;

  /**
   * Buy the specified number of shares of the specified company.
   *
   * @param name     is the name of the company whose stocks are intended to be bought
   * @param quantity is the quantity of the intended stocks
   * @throws IllegalArgumentException if the portfolio on which this operation needs to be performed
   *                                  is not selected.
   */
  void buyShares(String name, int quantity) throws IllegalArgumentException;

  /**
   * Sells all the shares of the specified company existing in the portfolio.
   *
   * @param name is the ticker Symbol company whose shares are intended to be sold
   * @param date date on which share is to be sold
   * @throws IllegalArgumentException if the portfolio on which this operation needs to be performed
   *                                  is not selected.
   */
  void sellShares(String name, String date) throws IllegalArgumentException;

  /**
   * Sells the specified number of shares of the specified company.
   *
   * @param name     is the ticker symbol of the company
   * @param quantity is the number of shares
   * @param date     date on which share is to be sold
   * @throws IllegalArgumentException if the portfolio on which this operation needs to be performed
   *                                  is not selected.
   */
  void sellShares(String name, int quantity, String date) throws IllegalArgumentException;

  /**
   * Sells the shares of specified cost of the specified company.
   *
   * @param name is the ticker symbol of the company
   * @param cost is the cost of the shares
   * @param date date on which share is to be sold
   * @throws IllegalArgumentException if the portfolio on which this operation needs to be performed
   *                                  is not selected
   */
  void sellShares(String name, Double cost, String date) throws IllegalArgumentException;

  /**
   * Sells the specified number of shares of the specified cost of the specified company.
   *
   * @param name     is the ticker symbol of the company whose shares need to be sold
   * @param quantity of shares that need to be sold
   * @param cost     of the shares that need to be sold
   * @param date     date on which share is to be sold
   * @throws IllegalArgumentException if the portfolio on which this operation needs to be performed
   *                                  is not selected
   */
  void sellShares(String name, int quantity, Double cost, String date)
          throws IllegalArgumentException;

  /**
   * Displays all the details of the specified existing portfolios.
   *
   * @return the details of the portfolio
   * @throws IllegalArgumentException if the portfolio on which this operation needs to be performed
   *                                  is not selected
   */
  String getCurrentPortfolioDetails() throws IllegalArgumentException;

}
