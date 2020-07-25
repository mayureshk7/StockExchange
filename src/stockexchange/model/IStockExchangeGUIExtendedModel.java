package stockexchange.model;

import java.io.IOException;

/**
 * This is the extended GUI interface of the IStockExchangeExtendedModel. It offers all the methods
 * and operations that can be performed additional to the existing functionalities provided by
 * IStockExchangeExtendedModel.
 */

public interface IStockExchangeGUIExtendedModel extends IStockExchangeExtendedModel {

  /**
   * Saves the portfolio to a file.
   *
   * @param fileName is the filename to which the portfolio will be saved
   * @throws IOException when file operations not performed correctly
   */

  void savePortfolioInFile(String fileName) throws IOException;

  /**
   * Retrives the details of the portfolio form a file to which it was saved.
   *
   * @param fileName is the name of the file from which the saved portfolio will be retrived
   * @throws IOException when file operations not performed correctly
   */
  void retrivePortfolioFromFile(String fileName) throws IOException;

  /**
   * Saves the investment strategy of a portfolio to a file.
   *
   * @param fileName is the file name which has the saved portfolio, to which the strategy will be
   *                 applied and saved
   * @throws IOException when file operations not performed correctly
   */
  void saveInvestmentInFile(String fileName) throws IOException;

  /**
   * Retrives the investment from a file.
   *
   * @param fileName is the file from which the saved investment is retrived
   * @throws IOException when file operations not performed correctly
   */
  void retriveInvestmentFromFile(String fileName) throws IOException;
}