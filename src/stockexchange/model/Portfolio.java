package stockexchange.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Portfolio class describes each portfolio in the stock exhange. Each portfolio has number of
 * shares and it is capable of calculating profit and overall cost basis.
 */
public class Portfolio {

  private Map<String, LinkedList<Share>> shares;
  private Map<String, LinkedList<Share>> transactions;
  private String name;
  private int maximumQuantity = 0;
  private double currentSharePrice = 0.00;
  private double costBasis = 0.00;
  private double totalProfit = 0.00;
  private String date = "";
  private double totalCommission = 0.0;
  private double eachCommission = 0.0;

  /**
   * Constructor that initialises the Portfolio object by the given name of the portfolio and
   * shares.
   *
   * @param name is the name of the portfolio that needs to be created
   */
  public Portfolio(String name) {
    this.name = name;
    shares = new HashMap<String, LinkedList<Share>>();
    transactions = new HashMap<String, LinkedList<Share>>();
  }

  /**
   * Sets the value of the commission.
   *
   * @param commission value of commission
   */
  public void setEachCommission(double commission) {
    if (commission > 0) {
      this.eachCommission = commission;
    } else {
      throw new IllegalStateException("Value of commission cannot be negative");
    }
  }

  /**
   * Displays the details of the specified shares on the specified date.
   *
   * @param nameOfShare is the ticker symbol of the share whose details need to be known
   * @param date        the date in YYYY-MM-DD format
   * @return the following information - Ticker Symbol of the share - Opening Price - Highest Price
   *         of the Day - Lowest Price of the day - Closing Price - Volume of the shares
   * @throws IllegalArgumentException if the ticker symbol of the shares is incorrect or if the date
   *                                  is not given
   */
  public String displayShare(String nameOfShare, String date) throws IllegalArgumentException,
          IOException {
    String shareData = "";
    this.date = date;
    File dataFile = new File(FileSystems.getDefault().getPath(".")
            + nameOfShare + ".txt");
    if (!dataFile.createNewFile()) {
      try {
        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        String text = null;

        while ((text = reader.readLine()) != null) {
          if (text.contains(date)) {
            String[] currentShareData = text.trim().split(",");
            shareData = "Share : " + nameOfShare + "\n"
                    + "Open : " + currentShareData[1] + "\n"
                    + "High : " + currentShareData[2] + "\n"
                    + "Low : " + currentShareData[3] + "\n"
                    + "Close : " + currentShareData[4] + "\n"
                    + "Volume : " + currentShareData[5] + "\n";
            currentSharePrice = Double.parseDouble(currentShareData[4]);
            maximumQuantity = Integer.parseInt(currentShareData[5]);
            //closingAmount = Double.parseDouble(currentShareData[4]);
            return shareData;
          }
        }
        throw new IOException("Cannot read from the file");
      } catch (IOException e) {
        dataFile.delete();
        throw new IOException("Cannot read from existing file");
      }
    } else {
      AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI(nameOfShare);
      String allShareData = "";
      try {
        allShareData = alphaVantageAPI.getData();
      } catch (IllegalStateException e) {
        throw new IllegalStateException("Unable to fetch data");
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("No price data found for " + nameOfShare);
      }
      //Write in the file
      FileWriter writer = new FileWriter(dataFile);
      writer.write(allShareData);
      writer.close();
      String[] data = allShareData.split("\n");

      for (String str : data) {
        if (str.contains(date)) {
          String[] currentShareData = str.trim().split(",");
          shareData = "Share : " + nameOfShare + "\n"
                  + "Open : " + currentShareData[1] + "\n"
                  + "High : " + currentShareData[2] + "\n"
                  + "Low : " + currentShareData[3] + "\n"
                  + "Close : " + currentShareData[4] + "\n"
                  + "Volume : " + currentShareData[5] + "\n";
          currentSharePrice = Double.parseDouble(currentShareData[4]);
          maximumQuantity = Integer.parseInt(currentShareData[5]);
          break;
        }
      }
      if (shareData.equals("")) {
        throw new IllegalArgumentException("Data for date not available");
      }
      return shareData;
    }
  }

  /**
   * Buy the specified number of shares of the specified company.
   *
   * @param name     is the name of the company whose stocks are intended to be bought
   * @param quantity is the quantity of the intended stocks
   * @throws IllegalArgumentException if the Quantity of shares exceed the Quantity available for
   *                                  sale or if the Quantity of the shares is less than 1
   */
  public void buyShare(String name, int quantity) throws IllegalArgumentException {
    if (quantity > maximumQuantity) {
      throw new IllegalArgumentException("CEO of " + name + " " +
              "has to resign if you buy " + quantity + "shares of " + name);
    }
    if (quantity < 1) {
      throw new IllegalArgumentException("Incorrect number of shares to be bought");
    }
    Share newShare = new Share(name, currentSharePrice, quantity, date, eachCommission);
    costBasis = costBasis + currentSharePrice * quantity;
    totalCommission = totalCommission + eachCommission;
    if (shares.containsKey(name)) {
      boolean foundSamePrice = false;
      for (int i = 0; i < shares.get(name).size(); i++) {
        if (shares.get(name).get(i).getCost() == currentSharePrice) {
          foundSamePrice = true;
          int num = shares.get(name).get(i).getNumberOfShares();
          shares.get(name).remove(i);
          shares.get(name).add(new Share(name, currentSharePrice,
                  quantity + num, date, eachCommission));
          transactions.get(name).add(new Share(name, currentSharePrice,
                  quantity + num, date, eachCommission));
        }
      }
      if (!foundSamePrice) {
        shares.get(name).add(newShare);
        transactions.get(name).add(newShare);
      }
    } else {
      LinkedList<Share> newSharedList = new LinkedList<Share>();
      newSharedList.add(newShare);
      LinkedList<Share> newSharedListForTransaction = new LinkedList<Share>();
      newSharedListForTransaction.add(new Share(name, currentSharePrice,
              quantity, date, eachCommission));
      shares.put(name, newSharedList);
      transactions.put(name, newSharedListForTransaction);
    }
  }

  /**
   * Sells the shares of the specified company.
   *
   * @param shareToRemove is the ticker symbol of the company
   * @param date          on which share is to be sold
   * @throws IllegalArgumentException if there are multiple shares of the same company bought on
   *                                  different dates or the specified ShareCompany does not exists
   *                                  in the portfolio
   */
  public void sellShare(String shareToRemove, String date) throws IllegalArgumentException {
    if (shares.containsKey(shareToRemove)) {
      if (shares.get(shareToRemove).size() == 1) {
        double currentPrice;
        try {
          currentPrice = this.getCurrentSharePrice(shareToRemove, date);
        } catch (Exception e) {
          throw new IllegalArgumentException("Data for date not avilable");
        }
        totalProfit = totalProfit +
                (currentPrice - shares.get(shareToRemove).get(0).getCost())
                        * (shares.get(shareToRemove).get(0).getNumberOfShares());
        totalCommission = totalCommission + eachCommission;
        LinkedList<Share> removedShares = shares.remove(shareToRemove);
        transactions.get(shareToRemove).add(new Share(shareToRemove, currentPrice,
                removedShares.get(0).getNumberOfShares() * (-1), date, eachCommission));
      } else {
        throw new IllegalStateException("Multiple Shares of " + shareToRemove +
                " Need to specify the cost value to delete");
      }
    } else {
      throw new IllegalStateException("Does not have shares");
    }
  }

  /**
   * Sells the specified number of shares of the specified company.
   *
   * @param shareToRemove is the ticker symbol of the company
   * @param toBeSold      is the number the shares
   * @param date          on which share is to be sold
   * @throws IllegalArgumentException if there are multiple shares of the same company bought on
   *                                  different dates or the specified ShareCompany does not exists
   *                                  in the portfolio
   */
  public void sellShare(String shareToRemove, int toBeSold, String date)
          throws IllegalArgumentException {
    if (shares.containsKey(shareToRemove)) {
      if (shares.get(shareToRemove).size() == 1) {
        if (shares.get(shareToRemove).get(0).getNumberOfShares() < toBeSold) {
          throw new IllegalArgumentException("Not enough shares to sell");
        } else if (shares.get(shareToRemove).get(0).getNumberOfShares() == toBeSold) {
          double currentPrice;
          try {
            currentPrice = this.getCurrentSharePrice(shareToRemove, date);
          } catch (Exception e) {
            throw new IllegalArgumentException("data for date not avilable");
          }
          totalProfit = totalProfit +
                  (currentPrice - shares.get(shareToRemove).get(0).getCost())
                          * (toBeSold);
          totalCommission = totalCommission + eachCommission;
          LinkedList<Share> removedShares = shares.remove(shareToRemove);
          transactions.get(shareToRemove).add(new Share(shareToRemove, currentPrice,
                  toBeSold * (-1), date, eachCommission));
        } else {
          double currentPrice;
          try {
            currentPrice = this.getCurrentSharePrice(shareToRemove, date);
          } catch (Exception e) {
            throw new IllegalArgumentException("data for date not avilable");
          }
          shares.get(shareToRemove).get(0).sellSomeShares(toBeSold);
          totalProfit = totalProfit +
                  (currentPrice - shares.get(shareToRemove).get(0).getCost())
                          * (toBeSold);
          totalCommission = totalCommission + eachCommission;
          transactions.get(shareToRemove).add(new Share(shareToRemove, currentPrice,
                  toBeSold * (-1), date, eachCommission));
        }
      } else {
        throw new IllegalStateException("Multiple Shares of " + shareToRemove);
      }
    } else {
      throw new IllegalStateException("Does not have shares");
    }
  }

  /**
   * Sells the shares of specified cost of the specified company.
   *
   * @param shareToRemove is the ticker symbol of the company
   * @param cost          is the cost of the shares
   * @param date          on which share is to be sold
   * @throws IllegalArgumentException if the shares of the specified cost does not exists
   */
  public void sellShare(String shareToRemove, double cost, String date)
          throws IllegalArgumentException {
    if (shares.get(shareToRemove).size() == 1) {
      double currentPrice;
      try {
        currentPrice = this.getCurrentSharePrice(shareToRemove, date);
      } catch (Exception e) {
        throw new IllegalArgumentException("Data for date not avilable");
      }
      totalProfit = totalProfit +
              (currentPrice - shares.get(shareToRemove).get(0).getCost())
                      * shares.get(shareToRemove).get(0).getNumberOfShares();
      totalCommission = totalCommission + eachCommission;
      LinkedList<Share> removedShares = shares.remove(shareToRemove);
      transactions.get(shareToRemove).add(new Share(shareToRemove, currentPrice,
              removedShares.get(0).getNumberOfShares() * (-1), date, eachCommission));
      return;
    } else if (shares.containsKey(shareToRemove)) {
      for (int i = 0; i < shares.get(shareToRemove).size(); i++) {
        if (shares.get(shareToRemove).get(i).getCost() == cost) {
          double currentPrice;
          try {
            currentPrice = this.getCurrentSharePrice(shareToRemove, date);
          } catch (Exception e) {
            throw new IllegalArgumentException("data for date not avilable");
          }
          totalProfit = totalProfit +
                  (currentPrice - shares.get(shareToRemove).get(i).getCost())
                          * shares.get(shareToRemove).get(i).getNumberOfShares();
          totalCommission = totalCommission + eachCommission;
          Share removedShare = shares.get(shareToRemove).remove(i);
          transactions.get(shareToRemove).add(new Share(shareToRemove, currentPrice,
                  removedShare.getNumberOfShares() * (-1), date, eachCommission));
          return;
        }
      }
    }
    throw new IllegalArgumentException("Does not have shares");
  }

  /**
   * Sells the specified number of shares of the specified cost of the specified company.
   *
   * @param shareToRemove is the ticker symbol of the company whose shares need to be sold
   * @param toBeSold      Qty of shares that need to be sold
   * @param cost          of the shares that need to be sold
   * @param date          on which share is to be sold
   * @throws IllegalArgumentException if the qty of shares is more than the available shares to be
   *                                  sold or if the specified shares are not available in the
   *                                  portfolio
   */
  public void sellShare(String shareToRemove, int toBeSold, double cost, String date)
          throws IllegalArgumentException {
    if (shares.containsKey(shareToRemove)) {
      for (int i = 0; i < shares.get(shareToRemove).size(); i++) {
        if (shares.get(shareToRemove).get(i).getCost() == cost) {
          if (shares.get(shareToRemove).get(i).getNumberOfShares() < toBeSold) {
            throw new IllegalArgumentException("Not enough shares to sell");
          } else if (shares.get(shareToRemove).get(i).getNumberOfShares() == toBeSold) {
            double currentPrice;
            try {
              currentPrice = this.getCurrentSharePrice(shareToRemove, date);
            } catch (Exception e) {
              throw new IllegalArgumentException("data for date not avilable");
            }
            totalProfit = totalProfit +
                    (currentPrice - shares.get(shareToRemove).get(i).getCost())
                            * toBeSold;
            totalCommission = totalCommission + eachCommission;
            Share removedShare = shares.get(shareToRemove).remove(i);
            transactions.get(shareToRemove).add(new Share(shareToRemove, currentPrice,
                    removedShare.getNumberOfShares() * (-1), date, eachCommission));
            return;
          } else {
            double currentPrice;
            try {
              currentPrice = this.getCurrentSharePrice(shareToRemove, date);
            } catch (Exception e) {
              throw new IllegalArgumentException("data for date not avilable");
            }
            totalProfit = totalProfit +
                    (currentPrice - shares.get(shareToRemove).get(i).getCost())
                            * toBeSold;
            totalCommission = totalCommission + eachCommission;
            shares.get(shareToRemove).get(i).sellSomeShares(toBeSold);
            transactions.get(shareToRemove).add(new Share(shareToRemove, currentPrice,
                    toBeSold * (-1), date, eachCommission));
            return;
          }
        }
      }
    }
    throw new IllegalStateException("Does not have shares");
  }

  /**
   * Displays all the details of the specified existing portfolios.
   *
   * @return the following details of the portfolio - Portfolio Name - Total Investment TotalReturn
   *         Total Profit - Profit Rate - Share Name - Cost Basis - Number of Shares
   *         Total Cost of Basis
   */
  public String getState() {
    StringBuilder output = new StringBuilder();
    output.append("Portfolio Name : " + this.name + "\n");
    output.append("Total Investment : " + (this.costBasis - this.totalCommission) + "\n");
    output.append("Total Spent as Commission : " + this.totalCommission + "\n");
    output.append("Total Return : " + (this.totalProfit
            + this.costBasis - this.totalCommission) + "\n");
    output.append("Total Profit : " + (this.totalProfit - this.totalCommission) + "\n");
    output.append("Profit Rate : " +
            (100 * (this.totalProfit - this.totalCommission) / this.costBasis) + "\n" + "\n");
    for (String name : shares.keySet()) {
      output.append("Share Name : " + name + "\n");
      for (Share share : shares.get(name)) {
        output.append(share.toString());
      }
    }
    return output.toString();
  }

  /**
   * Displays all the details of the specified existing portfolios.
   *
   * @return the following details of the portfolio - Portfolio Name - Total Investment TotalReturn
   *         Total Profit - Profit Rate - Share Name - Cost Basis - Number of Shares
   *         Total Cost of Basis
   */
  public String getStateBasedOnDate(String stringDate) {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    Date date = null;
    try {
      cal.setTime(sdf.parse(stringDate));
    } catch (Exception e) {
      throw new IllegalArgumentException("Unable to Parse, kindly enter the date in the " +
              "correct format");
    }
    date = cal.getTime();
    StringBuilder output = new StringBuilder();

    double totalInvestment = 0.0;
    double totalCommission = 0.0;

    for (String name : shares.keySet()) {
      output.append("Share Name : " + name + "\n");
      for (Share share : shares.get(name)) {
        Date shareDate = null;
        Calendar cal1 = Calendar.getInstance();
        try {
          cal1.setTime(sdf.parse(share.getDate()));
        } catch (Exception e) {
          throw new IllegalArgumentException("Unable to Parse, kindly enter the date in the " +
                  "correct format");
        }
        shareDate = cal1.getTime();
        if (shareDate.compareTo(date) < 0) {
          output.append(share.toString());
          totalInvestment = totalInvestment + share.getCost() * share.getNumberOfShares();
          totalCommission = totalCommission + share.getCommission();
        }
      }
    }
    output.insert(0, "Total Commission : " + totalCommission + "\n\n");
    output.insert(0, "Total Investment : " + totalInvestment + "\n");
    output.insert(0, "Portfolio Name : " + this.name + "\n");
    return output.toString();
  }

  /**
   * Responsible for providing current cost basis.
   *
   * @return current cost basis
   */
  public String getCurrentCostBasis() {
    return String.valueOf(this.costBasis - this.totalCommission);
  }

  /**
   * Responsible for providing cost basis on certain date.
   *
   * @param stringDate date on which cost basis is required
   * @return cost basis on provided date
   */
  public String getCostBasisOnDate(String stringDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    Date date = null;
    try {
      cal.setTime(sdf.parse(stringDate));
    } catch (Exception e) {
      throw new IllegalArgumentException("Unable to Parse, kindly enter the date in the " +
              "correct format");
    }
    date = cal.getTime();

    double totalInvestment = 0.0;
    double totalCommission = 0.0;

    for (String name : shares.keySet()) {
      for (Share share : shares.get(name)) {
        Date shareDate = null;
        Calendar cal1 = Calendar.getInstance();
        try {
          cal1.setTime(sdf.parse(share.getDate()));
        } catch (Exception e) {
          throw new IllegalArgumentException("Unable to Parse, kindly enter the date in the " +
                  "correct format");
        }
        shareDate = cal1.getTime();
        if (shareDate.compareTo(date) < 0) {
          totalInvestment = totalInvestment + share.getCost() * share.getNumberOfShares();
          totalCommission = totalCommission + share.getCommission();
        }
      }
    }
    return String.valueOf(totalInvestment - totalCommission);
  }

  /**
   * Gives the current price of the share.
   *
   * @param nameOfShare name for which price has to be calculated
   * @return price of share
   * @throws IllegalArgumentException if their is issue extracting data
   */
  private double getCurrentSharePrice(String nameOfShare, String date)
          throws IllegalArgumentException, IOException {

    String shareData = "";
    this.date = date;
    File dataFile = new File(FileSystems.getDefault().getPath(".")
            + nameOfShare + ".txt");
    if (!dataFile.createNewFile()) {
      try {
        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        String text = null;

        while ((text = reader.readLine()) != null) {
          if (text.contains(date)) {
            String[] currentShareData = text.trim().split(",");
            shareData = "Share : " + nameOfShare + "\n"
                    + "Open : " + currentShareData[1] + "\n"
                    + "High : " + currentShareData[2] + "\n"
                    + "Low : " + currentShareData[3] + "\n"
                    + "Close : " + currentShareData[4] + "\n"
                    + "Volume : " + currentShareData[5] + "\n";
            currentSharePrice = Double.parseDouble(currentShareData[4]);
            maximumQuantity = Integer.parseInt(currentShareData[5]);
            return currentSharePrice;
          }
        }
        throw new IOException("Cannot read from the file");
      } catch (IOException e) {
        dataFile.delete();
        throw new IOException("Cannot read from existing file");
      }
    } else {
      AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI(nameOfShare);
      String allShareData = alphaVantageAPI.getData();
      String[] data = allShareData.split("\n");
      for (String str : data) {
        if (str.contains(date)) {
          String[] currentShareData = str.trim().split(",");
          currentSharePrice = Double.parseDouble(currentShareData[4]);
          maximumQuantity = Integer.parseInt(currentShareData[5]);
          return currentSharePrice;
        }
      }
      throw new IllegalArgumentException("Unable ");
    }
  }

  /**
   * Invests the given amount in the existing shares by distributing amount equally.
   *
   * @param totalAmount amount to be invested
   * @param date        date on which investment is to be done
   * @return status after investment
   */
  public String investMoney(double totalAmount, String date) {
    String status = "";
    double remainingAmount = totalAmount;
    int totalNoOfSharesInPort = shares.size();
    double eachInvestment = totalAmount / totalNoOfSharesInPort;

    for (String shareName : shares.keySet()) {
      try {
        double costofShare = getCurrentSharePrice(shareName, date);
        if (eachInvestment < costofShare) {
          status = status + "Bought 0 Shares of " + shareName + " at price " + costofShare
                  + " per share\n";
        } else {
          int noOfShares = (int) (eachInvestment / costofShare);
          try {
            buyShare(shareName, noOfShares);
            status = status + "Bought " + noOfShares + " Shares of " + shareName +
                    " at price " + costofShare + " per share\n";
            remainingAmount = remainingAmount - (costofShare * noOfShares);
          } catch (Exception e) {
            status = status + "Bought 0 Shares of " + shareName + " at price " + costofShare
                    + " per share\n";
          }
        }
      } catch (Exception e) {
        status = status + "Bought 0 Shares of " + shareName + " at price unknown"
                + " per share\n";
      }
    }
    status = status + "Total Investment : " + (totalAmount - remainingAmount);
    return status;
  }

  /**
   * Invests the given amount in the existing shares using the percentage splits.
   *
   * @param totalAmount amount to be investment
   * @param date        date on which investment is to be done
   * @param weights     split in which investment is to be made
   * @return status after investment
   */
  public String investMoney(double totalAmount, String date, double[] weights) {
    String status = "";
    double remainingAmount = 0.0;
    if (weights.length != shares.size()) {
      throw new IllegalStateException("The number of weights should only be equal " +
              "to the number of shares in the portfolio, i.e " + shares.size());
    }
    double sumofWeights = 0;
    for (int i = 0; i < weights.length; i++) {
      sumofWeights = sumofWeights + weights[i];
    }
    if (sumofWeights != 100) {
      throw new IllegalStateException("The sum of the weights should be 100.");
    }

    //converting weights to eachInvestment and checking if it's sufficient
    double[] eachInvestment = new double[shares.size()];
    int i = 0;
    for (String shareName : shares.keySet()) {
      try {
        eachInvestment[i] = (weights[i] * totalAmount) / 100;
        double costofShare = getCurrentSharePrice(shareName, date);
        if (eachInvestment[i] < costofShare) {
          status = status + "Bought 0 Shares of " + shareName + " at price " + costofShare
                  + " per share\n";
        } else {
          int noOfShares = (int) (eachInvestment[i] / costofShare);
          try {
            buyShare(shareName, noOfShares);
            status = status + "Bought " + noOfShares + " Shares of " + shareName +
                    " at price " + costofShare + " per share\n";
            remainingAmount = remainingAmount - (costofShare * noOfShares);
          } catch (Exception e) {
            status = status + "Bought 0 Shares of " + shareName + " at price " + costofShare
                    + " per share\n";
          }
        }
      } catch (Exception e) {
        status = status + "Bought 0 Shares of " + shareName + " at price unknown"
                + " per share\n";
      }
      i = i + 1;
    }
    status = status + "Total Investment : " + (totalAmount - remainingAmount);
    return status;
  }

  /**
   * This method is used to save portfolio in a file.
   *
   * @param fileName file name in which portfolio is to be saved
   * @throws IOException file input output error
   */
  public void savePortfolioInFile(String fileName) throws IOException {
    File dataFile = new File(FileSystems.getDefault().getPath("")
            + fileName + ".txt");
    if (!dataFile.createNewFile()) {
      throw new IllegalArgumentException("File already Exists");
    } else {
      FileWriter writer = new FileWriter(dataFile);
      writer.write(String.valueOf(costBasis));
      writer.write(System.getProperty("line.separator"));
      writer.write(String.valueOf(totalProfit));
      writer.write(System.getProperty("line.separator"));
      writer.write(String.valueOf(totalCommission));
      writer.write(System.getProperty("line.separator"));
      writer.write(String.valueOf(eachCommission));
      for (String key : transactions.keySet()) {
        LinkedList<Share> shares = transactions.get(key);
        for (int i = 0; i < shares.size(); i++) {
          writer.write(System.getProperty("line.separator"));
          writer.write(shares.get(i).transactionData());
        }
      }
      writer.write(System.getProperty("line.separator"));
      writer.write("END");
      for (String key : shares.keySet()) {
        LinkedList<Share> details = shares.get(key);
        for (int i = 0; i < details.size(); i++) {
          writer.write(System.getProperty("line.separator"));
          writer.write(details.get(i).transactionData());
        }
      }
      writer.close();
    }
  }

  /**
   * This method is used to save investment strategy in a file.
   *
   * @param fileName file name in which investment strategy is to be saved
   * @throws IOException file input output error
   */
  public void saveInvestmentInFile(String fileName, String investmentPlan) throws IOException {
    File dataFile = new File(FileSystems.getDefault().getPath("")
            + fileName + ".txt");
    if (!dataFile.createNewFile()) {
      throw new IllegalArgumentException("File already Exists");
    } else {
      FileWriter writer = new FileWriter(dataFile);
      writer.write(String.valueOf(investmentPlan));
      writer.close();
    }
  }

  /**
   * This method is used to load portfolio from a file.
   *
   * @param fileName file name from which portfolio is to be retrieved
   * @throws IOException file input output error
   */
  public void retrivePortfolioFromFile(String fileName) throws IOException {
    File dataFile = new File(FileSystems.getDefault().getPath("")
            + fileName + ".txt");
    double[] values = new double[4];
    Map<String, LinkedList<Share>> tempShares = new HashMap<String, LinkedList<Share>>();
    Map<String, LinkedList<Share>> tempTransactions = new HashMap<String, LinkedList<Share>>();
    if (!dataFile.createNewFile()) {
      try {
        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        String text = null;
        for (int i = 0; i < 4; i++) {
          if ((text = reader.readLine()) != null) {
            values[i] = Double.parseDouble(text);
          } else {
            throw new IllegalArgumentException("File empty");
          }
        }
        while ((text = reader.readLine()) != null) {
          if (text.equals("END")) {
            break;
          }
          String[] data = text.trim().split(" ");
          if (tempTransactions.containsKey(data[0])) {
            tempTransactions.get(data[0]).add(new Share(data[0], Double.parseDouble(data[1]),
                    Integer.parseInt(data[2]), data[3], Double.parseDouble(data[4])));
          } else {
            LinkedList<Share> shares = new LinkedList<Share>();
            shares.add(new Share(data[0], Double.parseDouble(data[1]),
                    Integer.parseInt(data[2]), data[3], Double.parseDouble(data[4])));
            tempTransactions.put(data[0], shares);
          }
        }
        while ((text = reader.readLine()) != null) {
          String[] data = text.trim().split(" ");
          if (tempShares.containsKey(data[0])) {
            tempShares.get(data[0]).add(new Share(data[0], Double.parseDouble(data[1]),
                    Integer.parseInt(data[2]), data[3], Double.parseDouble(data[4])));
          } else {
            LinkedList<Share> shares = new LinkedList<Share>();
            shares.add(new Share(data[0], Double.parseDouble(data[1]),
                    Integer.parseInt(data[2]), data[3], Double.parseDouble(data[4])));
            tempShares.put(data[0], shares);
          }
        }

        //Everthing okay, reassign
        costBasis = values[0];
        totalProfit = values[1];
        totalCommission = values[2];
        eachCommission = values[3];
        transactions = tempTransactions;
        shares = tempShares;

      } catch (IOException e) {
        throw new IllegalStateException("Something went wrong");
      }
    } else {
      //file does not exists
      throw new IllegalArgumentException("Saved file does not exists");
    }
  }

  /**
   * This method is used to load investment strategy from a file.
   *
   * @param fileName file name from which investment strategy is to be retrieved
   * @throws IOException file input output error
   */
  public InvestmentPlans retriveInvestmentFromFile(String fileName) throws IOException {
    File dataFile = new File(FileSystems.getDefault().getPath("")
            + fileName + ".txt");
    double amount = 0;
    String startDate = "";
    String endDate = null;
    int interval = 0;
    if (!dataFile.createNewFile()) {
      try {
        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        String investmentPlan = reader.readLine();
        String[] individualInput = investmentPlan.split(" ");
        amount = Double.parseDouble(individualInput[0]);
        startDate = individualInput[1];
        if (!individualInput[2].equals("null")) {
          endDate = individualInput[2];
        }
        interval = Integer.parseInt(individualInput[3]);
        if (endDate == null) {
          return new InvestmentPlans(amount, startDate, "", interval, null);
        } else {
          return new InvestmentPlans(amount, startDate, endDate, interval, null);
        }
      } catch (IOException e) {
        throw new IllegalStateException("Something went wrong");
      }
    } else {
      throw new IllegalArgumentException("Saved file does not exists");
    }
  }

}
