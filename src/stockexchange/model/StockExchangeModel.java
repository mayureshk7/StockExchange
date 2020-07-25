package stockexchange.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * StockExchange model is the class for the StockExchangeModel. It implements The IStockExchange
 * Interface and all its methods.
 */
public class StockExchangeModel implements IStockExchangeGUIExtendedModel {

  private Portfolio currentPortfolio;
  private Map<String, Portfolio> portfolios;
  private InvestmentPlans investmentPlan = null;

  /**
   * Constructor of the StockExchangeModel initialises the portfolios and currentPortfolio to null.
   */
  public StockExchangeModel() {
    portfolios = new HashMap<String, Portfolio>();
    currentPortfolio = null;
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {

    if (!portfolios.containsKey(portfolioName)) {
      Portfolio newPortfolio = new Portfolio(portfolioName);
      portfolios.put(portfolioName, newPortfolio);
    } else {
      throw new IllegalArgumentException("The name of the portfolio already exists");
    }
  }

  @Override
  public void selectPortfolio(String portfolioName) throws IllegalArgumentException {
    if (portfolios.containsKey(portfolioName)) {
      currentPortfolio = portfolios.get(portfolioName);
    } else {
      throw new IllegalArgumentException("Portfolio profile does not exist.");
    }
  }

  @Override
  public void setCommission(double commission) throws IllegalArgumentException {
    if (currentPortfolio != null) {
      currentPortfolio.setEachCommission(commission);
    } else {
      throw new IllegalArgumentException("Select portfolio first");
    }
  }

  @Override
  public void removePortfolio(String portfolioName) throws IllegalArgumentException {
    if (portfolios.containsKey(portfolioName)) {
      if (currentPortfolio == portfolios.get(portfolioName)) {
        currentPortfolio = null;
      }
      portfolios.remove(portfolioName);
    } else {
      throw new IllegalArgumentException("Portfolio profile does not exist.");
    }
  }

  @Override
  public String displayShareDetails(String nameOfShare, String date) {
    try {
      if (isHoliday(date)) {
        throw new IllegalArgumentException("Trading cannot be done on holidays");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Illegal date format");
    }
    if (currentPortfolio != null) {
      try {
        return currentPortfolio.displayShare(nameOfShare, date);
      } catch (Exception e) {
        return "Something went wrong";
      }
    } else {
      throw new IllegalArgumentException("Select portfolio first");
    }
  }

  @Override
  public void buyShares(String name, int quantity) throws IllegalArgumentException {
    if (currentPortfolio != null) {
      currentPortfolio.buyShare(name, quantity);
    } else {
      throw new IllegalArgumentException("Select portfolio first");
    }
  }

  @Override
  public void sellShares(String name, String date) throws IllegalArgumentException,
          IllegalStateException {
    if (!isHoliday(date)) {
      if (currentPortfolio != null) {
        currentPortfolio.sellShare(name, date);
      } else {
        throw new IllegalArgumentException("Select portfolio first");
      }
    } else {
      throw new IllegalStateException("Cannot sell shares on holiday");
    }
  }

  @Override
  public void sellShares(String name, int quantity, String date) throws IllegalArgumentException,
          IllegalStateException {
    if (!isHoliday(date)) {
      if (currentPortfolio != null) {
        currentPortfolio.sellShare(name, quantity, date);
      } else {
        throw new IllegalArgumentException("Select portfolio first");
      }
    } else {
      throw new IllegalArgumentException("Cannot sell shares on holiday");
    }
  }

  @Override
  public void sellShares(String name, Double cost, String date) throws IllegalArgumentException {
    if (!isHoliday(date)) {
      if (currentPortfolio != null) {
        currentPortfolio.sellShare(name, cost, date);
      } else {
        throw new IllegalArgumentException("Select portfolio first");
      }
    } else {
      throw new IllegalStateException("Cannot sell shares on holiday");
    }
  }

  @Override
  public void sellShares(String name, int quantity, Double cost, String date)
          throws IllegalArgumentException {
    if (!isHoliday(date)) {
      if (currentPortfolio != null) {
        currentPortfolio.sellShare(name, quantity, cost, date);
      } else {
        throw new IllegalArgumentException("Select portfolio first");
      }
    } else {
      throw new IllegalStateException("Cannot sell shares on holiday");
    }
  }

  @Override
  public String getCurrentPortfolioDetails() throws IllegalArgumentException {
    if (currentPortfolio != null) {
      return currentPortfolio.getState();
    } else {
      throw new IllegalArgumentException("Select portfolio first");
    }
  }

  @Override
  public String getCurrentPortfolioDetails(String date) throws IllegalArgumentException {
    if (currentPortfolio != null) {
      return currentPortfolio.getStateBasedOnDate(date);
    } else {
      throw new IllegalArgumentException("Select portfolio first");
    }
  }

  @Override
  public String getCurrentCostBasis() throws IllegalArgumentException {
    if (currentPortfolio != null) {
      return currentPortfolio.getCurrentCostBasis();
    } else {
      throw new IllegalArgumentException("Select portfolio first");
    }
  }

  @Override
  public String getCostBasisOnDate(String date) throws IllegalArgumentException {
    if (currentPortfolio != null) {
      return currentPortfolio.getCostBasisOnDate(date);
    } else {
      throw new IllegalArgumentException("Select portfolio first");
    }
  }

  @Override
  public String investMoney(double totalAmount, String date) {
    try {
      if (isHoliday(date)) {
        throw new IllegalStateException("Trading cannot be done on holidays");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Illegal date format");
    }
    if (currentPortfolio != null) {
      return currentPortfolio.investMoney(totalAmount, date);
    } else {
      throw new IllegalArgumentException("Select portfolio first");
    }
  }

  @Override
  public String investMoney(double totalAmount, String date, double[] percentage) {
    try {
      if (isHoliday(date)) {
        throw new Exception("Trading cannot be done on holidays");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Illegal date format");
    }
    if (currentPortfolio != null) {
      try {
        return currentPortfolio.investMoney(totalAmount, date, percentage);
      } catch (IllegalStateException s) {
        throw new IllegalStateException("Sum of percentages is not 100");
      }
    } else {
      throw new IllegalArgumentException("Select portfolio first");
    }
  }

  @Override
  public String regularInvestMoney(double totalAmount, String date, int interval) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date today = new Date();
    Calendar c = Calendar.getInstance();
    try {
      c.setTime(formatter.parse(date));
    } catch (Exception e) {
      throw new IllegalStateException("Incorrect Start date");
    }
    Date progressiveDate = c.getTime();

    while (progressiveDate.compareTo(today) < 0) {
      try {
        c.setTime(progressiveDate);
      } catch (Exception e) {
        throw new IllegalStateException("Unable to fetch date");
      }
      c.add(Calendar.DATE, interval);
      progressiveDate = c.getTime();
      while (isHoliday(formatter.format(progressiveDate))) {
        try {
          c.setTime(progressiveDate);
        } catch (Exception e) {
          throw new IllegalStateException("Unable to fetch date");
        }
        c.add(Calendar.DATE, 1);
        progressiveDate = c.getTime();
      }
      investMoney(totalAmount, formatter.format(progressiveDate));
    }
    investmentPlan = new InvestmentPlans(totalAmount, date, null, interval, null);
    return "Investing " + totalAmount + "on regular basis with " + interval + "days interval" +
            "starting from " + date;
  }

  @Override
  public String regularInvestMoney(double totalAmount, String startDate,
                                   String endDate, int interval) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    try {
      c.setTime(formatter.parse(startDate));
    } catch (Exception e) {
      throw new IllegalStateException("Incorrect Start date");
    }
    Date progressiveDate = c.getTime();
    try {
      c.setTime(formatter.parse(endDate));
    } catch (Exception e) {
      throw new IllegalStateException("Incorrect Start date");
    }
    Date endingDate = c.getTime();

    while (progressiveDate.compareTo(endingDate) < 0) {
      try {
        c.setTime(progressiveDate);
      } catch (Exception e) {
        throw new IllegalStateException("Unable to fetch date");
      }
      c.add(Calendar.DATE, interval);
      progressiveDate = c.getTime();
      while (isHoliday(formatter.format(progressiveDate))) {
        try {
          c.setTime(progressiveDate);
        } catch (Exception e) {
          throw new IllegalStateException("Unable to fetch date");
        }
        c.add(Calendar.DATE, 1);
        progressiveDate = c.getTime();
      }
      investMoney(totalAmount, formatter.format(progressiveDate));
    }
    investmentPlan = new InvestmentPlans(totalAmount, startDate, endDate,
            interval, null);
    return "Investing " + totalAmount + " on regular basis with " + interval + " days interval" +
            "starting from " + startDate + " till " + endDate;
  }


  @Override
  public String regularInvestMoneyWithPercentage(double totalAmount, String date,
                                                 int interval, double[] percentageWeights) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date today = new Date();
    Calendar c = Calendar.getInstance();
    try {
      c.setTime(formatter.parse(date));
    } catch (Exception e) {
      throw new IllegalStateException("Incorrect Start date" + e);
    }
    Date progressiveDate = c.getTime();

    while (progressiveDate.compareTo(today) < 0) {
      try {
        c.setTime(progressiveDate);
      } catch (Exception e) {
        throw new IllegalStateException("Unable to fetch date");
      }
      c.add(Calendar.DATE, interval);
      progressiveDate = c.getTime();
      while (isHoliday(formatter.format(progressiveDate))) {
        try {
          c.setTime(progressiveDate);
        } catch (Exception e) {
          throw new IllegalStateException("Unable to fetch date");
        }
        c.add(Calendar.DATE, 1);
        progressiveDate = c.getTime();
      }
      try {
        investMoney(totalAmount, formatter.format(progressiveDate), percentageWeights);
      } catch (IllegalStateException s) {
        throw new IllegalStateException("Sum of percentages is not 100" + s);
      } catch (Exception e) {
        throw new IllegalStateException("Unable to fetch date");
      }
    }
    investmentPlan = new InvestmentPlans(totalAmount, date, null, interval, percentageWeights);
    return "Investing " + totalAmount + "on regular basis with " + interval + "days interval" +
            "starting from " + date;
  }

  @Override
  public String regularInvestMoneyWithPercentage(double totalAmount, String startDate,
                                                 String endDate, int interval,
                                                 double[] percentageWeights) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    try {
      c.setTime(formatter.parse(startDate));
    } catch (Exception e) {
      throw new IllegalStateException("Incorrect Start date" + e);
    }
    Date progressiveDate = c.getTime();
    try {
      c.setTime(formatter.parse(endDate));
    } catch (Exception e) {
      throw new IllegalStateException("Incorrect Start date" + e);
    }
    Date endingDate = c.getTime();

    while (progressiveDate.compareTo(endingDate) < 0) {
      try {
        c.setTime(progressiveDate);
      } catch (Exception e) {
        throw new IllegalStateException("Unable to fetch date");
      }
      c.add(Calendar.DATE, interval);
      progressiveDate = c.getTime();
      while (isHoliday(formatter.format(progressiveDate))) {
        try {
          c.setTime(progressiveDate);
        } catch (Exception e) {
          throw new IllegalStateException("Unable to fetch date");
        }
        c.add(Calendar.DATE, 1);
        progressiveDate = c.getTime();
      }
      try {
        investMoney(totalAmount, formatter.format(progressiveDate), percentageWeights);
      } catch (IllegalStateException s) {
        throw new IllegalStateException("Sum of percentages is not 100" + s);
      } catch (Exception e) {
        throw new IllegalStateException("Unable to fetch date");
      }
    }
    investmentPlan = new InvestmentPlans(totalAmount, startDate, endDate,
            interval, percentageWeights);
    return "Investing " + totalAmount + " on regular basis with " + interval + " days interval" +
            "starting from " + startDate + " till " + endDate;
  }


  /**
   * IsHoliday determines if that day is a holiday or not.
   *
   * @param stringDate the date in the (YYYY-MM-DD) format
   * @return true if it's a saturday, sunday or a national holiday
   * @throws IllegalArgumentException if the date is passed in the incorrect format
   */
  private boolean isHoliday(String stringDate) throws IllegalArgumentException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    try {
      Date date = sdf.parse(stringDate);
      cal.setTime(date);
    } catch (Exception e) {
      throw new IllegalArgumentException("Unable to Parse, kindly enter the date in the " +
              "correct format");
    }

    // Sunday and Saturday is a holiday
    if (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7) {
      return true;
    }

    //New Year(1st Jan)
    if (cal.get(Calendar.DAY_OF_MONTH) == 01 && cal.get(Calendar.MONTH) == Calendar.JANUARY) {
      return true;
    }
    //Martin Luther King Day (3rd monday of Jan)
    if (cal.get(Calendar.WEEK_OF_MONTH) == 3 && cal.get(Calendar.DAY_OF_WEEK) == 2
            && cal.get(Calendar.MONTH) == 1) {
      return true;
    }

    //President's day(3rd Monday of Feb)
    if (cal.get(Calendar.WEEK_OF_MONTH) == 3 && cal.get(Calendar.DAY_OF_WEEK) == 2
            && cal.get(Calendar.MONTH) == 2) {
      return true;
    }

    //Memorial day(last Monday of May)
    if (cal.get(Calendar.DAY_OF_MONTH) == 31 - 7 && cal.get(Calendar.DAY_OF_WEEK) == 2
            && cal.get(Calendar.MONTH) == 5) {
      return true;
    }

    //Independence Day(4th July)
    if (cal.get(Calendar.DAY_OF_MONTH) == 4 && cal.get(Calendar.MONTH) == 7) {
      return true;
    }

    //Labor Day(1st Monday of September)
    if (cal.get(Calendar.WEEK_OF_MONTH) == 1 && cal.get(Calendar.DAY_OF_WEEK) == 2
            && cal.get(Calendar.MONTH) == 9) {
      return true;
    }

    //Columbus Day(2nd Monday of Oct)
    if (cal.get(Calendar.WEEK_OF_MONTH) == 2 && cal.get(Calendar.DAY_OF_WEEK) == 2
            && cal.get(Calendar.MONTH) == 10) {
      return true;
    }

    //Veterans
    if (cal.get(Calendar.DAY_OF_MONTH) == 11 && cal.get(Calendar.MONTH) == 11) {
      return true;
    }

    //ThanksGiving(4th Thursday of Nov
    if (cal.get(Calendar.WEEK_OF_MONTH) == 4 && cal.get(Calendar.DAY_OF_WEEK) == 5
            && cal.get(Calendar.MONTH) == 11) {
      return true;
    }

    //Christmas(25th December)
    return cal.get(Calendar.DAY_OF_MONTH) == 25 && cal.get(Calendar.MONTH) == 12;

  }

  @Override
  public void savePortfolioInFile(String fileName) {
    try {
      currentPortfolio.savePortfolioInFile(fileName);
    } catch (Exception e) {
      throw new IllegalStateException("Something went wrong");
    }
  }

  @Override
  public void retrivePortfolioFromFile(String fileName) {
    try {
      currentPortfolio.retrivePortfolioFromFile(fileName);
    } catch (Exception e) {
      throw new IllegalStateException("Something went wrong " + e);
    }
  }

  @Override
  public void saveInvestmentInFile(String fileName) {
    try {
      currentPortfolio.saveInvestmentInFile(fileName, investmentPlan.toString());
    } catch (Exception e) {
      throw new IllegalStateException("Something went wrong");
    }
  }

  @Override
  public void retriveInvestmentFromFile(String fileName) {
    try {
      InvestmentPlans retrievedPlan = currentPortfolio.retriveInvestmentFromFile(fileName);
      if (retrievedPlan.getEndDate().equals("")) {
        regularInvestMoney(retrievedPlan.getAmount(),
                retrievedPlan.getStartDate(), retrievedPlan.getInterval());
      } else {
        regularInvestMoney(retrievedPlan.getAmount(), retrievedPlan.getStartDate(),
                retrievedPlan.getEndDate(), retrievedPlan.getInterval());
      }
    } catch (Exception e) {
      throw new IllegalStateException("Something went wrong");
    }
  }
}
