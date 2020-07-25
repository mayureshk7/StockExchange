package stockexchange.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import stockexchange.model.IStockExchangeGUIExtendedModel;
import stockexchange.model.StockExchangeModel;
import stockexchange.view.IStockExchangeGUIView;
import stockexchange.view.IStockExchangeView;

public class StockExchangeGUIController implements IStockExchangeController, ActionListener {

  IStockExchangeGUIExtendedModel model;
  IStockExchangeGUIView view;
  private boolean portfolioExists = false;
  private boolean shareDisplayed = false;

  @Override
  public void run(StockExchangeModel model, IStockExchangeView view) throws Exception {
    this.model = (IStockExchangeGUIExtendedModel) model;
    this.view = (IStockExchangeGUIView) view;
    ((IStockExchangeGUIView) view).setListener(this);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    view.errorMessage("");
    switch (e.getActionCommand()) {
      case "createButton": {
        createPortfolio();
      }
      break;
      case "selectButton": {
        portfolioExists = true;
        selectPortfolio();
      }
      break;
      case "setButton": {
        setCommission();
      }
      break;
      case "displayButton": {
        displayShare();
      }
      break;
      case "buyButton": {
        buyShare();
      }
      break;
      case "investButton": {
        investMoney();
      }
      break;
      case "costBaseAvgButton": {
        regularlyInvestMoney();
      }
      break;
      case "costBaseAvgButtonPer": {
        System.out.print("HO" + view.getInputData());
        regularlyInvestMoneyPercent();
      }
      break;
      case "costBaseAvgButtonPerEnd": {
        System.out.print("HO" + view.getInputData());
        regularlyInvestMoneyPercentEnd();
      }
      break;
      case "sell": {
        sellShare();
      }
      break;
      case "sellMulti": {
        sellShareMulti();
      }
      break;
      case "detailsButton": {
        getDetails();
      }
      break;
      case "getCostButton": {
        getCostBasis();
      }
      break;
      case "savePortfolioButton": {
        savePortfolio();
      }
      break;
      case "saveStrategyButton": {
        saveStrategy();
      }
      break;
      case "loadPortfolioButton": {
        loadPortfolio();
      }
      break;
      case "loadStrategyButton": {
        loadStrategy();
      }
      break;
      case "removePortfolioButton": {
        removePortfolio();
      }
      break;
      default: {
        view.errorMessage("Something went wrong");
      }
    }
    try {
      view.setPortfolioOverview(model.getCurrentPortfolioDetails());
    } catch (Exception ex) {
      view.setPortfolioOverview(" ");
    }
  }

  private void createPortfolio() {
    String input = view.getInputData();
    try {
      model.createPortfolio(input);
      view.operationSuccessful(input + " Created Successfully");
    } catch (Exception ex) {
      view.errorMessage("The name of the portfolio already exists");
    }
  }

  private void selectPortfolio() {
    String input = view.getInputData();
    try {
      model.selectPortfolio(input);
      view.setPortfolioName(input);
      view.operationSuccessful(input + " selected Successfully");
    } catch (Exception ex) {
      view.errorMessage("The name of the portfolio does not exists");
    }
  }

  private void setCommission() {
    String input = view.getInputData();
    double commission = 0;
    try {
      commission = Double.parseDouble(input);
    } catch (Exception ex) {
      view.errorMessage("Unable to parse the input value");
    }
    try {
      model.setCommission(commission);
      view.operationSuccessful("Value of Commission set to : " + commission);
    } catch (IllegalStateException s) {
      view.errorMessage("Value of Commission cannot be negative");
    } catch (IllegalArgumentException a) {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void displayShare() {
    if (portfolioExists) {
      String shareName = "";
      String shareOnDate = "";
      try {
        String input = view.getInputData();
        String[] individualInput = input.trim().split(" ");
        if (individualInput.length == 2) {
          shareName = individualInput[0];
          shareOnDate = individualInput[1];
        } else {
          view.errorMessage("Incorrect number of Input");
        }
      } catch (Exception e) {
        view.errorMessage("Unable to process the input");
      }
      try {
        model.displayShareDetails(shareName, shareOnDate);
        view.operationSuccessful(model.displayShareDetails(shareName, shareOnDate));
        shareDisplayed = true;
      } catch (Exception e) {
        view.errorMessage("Data for date not available");
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void buyShare() {
    if (portfolioExists) {
      if (shareDisplayed) {
        String shareName = "";
        int sharesQuantity = 0;

        try {
          String input = view.getInputData();
          String[] individualInput = input.trim().split(" ");
          if (individualInput.length == 2) {
            shareName = individualInput[0];
            sharesQuantity = Integer.parseInt(individualInput[1]);
          } else {
            view.errorMessage("Incorrect number of inputs");
          }
        } catch (Exception e) {
          view.errorMessage("Unable to process the input");
        }
        try {
          model.buyShares(shareName, sharesQuantity);
          view.operationSuccessful("Shares of " + shareName + " of quantity "
                  + sharesQuantity + " bought successfully.");
          shareDisplayed = false;
        } catch (Exception e) {
          if (e.toString().contains("Select portfolio first")) {
            view.errorMessage("Select portfolio first");
          } else if (e.toString().contains("CEO of")) {
            view.errorMessage("CEO of " + shareName + " has to resign if you buy " +
                    "if you buy " + sharesQuantity + " shares of " + shareName);
          } else {
            view.errorMessage("Incorrect number of shares to be bought");
          }
        }
      } else {
        view.errorMessage("Fetch the details of the share first");
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void investMoney() {
    if (portfolioExists) {
      double amount = 0.0;
      String date = "";
      try {
        String input = view.getInputData();
        String[] individualInput = input.trim().split(" ");
        if (individualInput.length == 2) {
          amount = Double.parseDouble(individualInput[0]);
          date = individualInput[1];
        } else {
          this.investUsingPercentage();
          return;
        }
      } catch (Exception e) {
        view.errorMessage("Error processing inputs");
      }
      String status = "";
      try {
        status = model.investMoney(amount, date);
      } catch (IllegalStateException s) {
        view.errorMessage("Trending cannot be done on holidays");
      } catch (IllegalArgumentException a) {
        view.errorMessage("Illegal date format");
      }
      view.operationSuccessful(status);
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void investUsingPercentage() {
    if (portfolioExists) {
      double amount = 0.0;
      String date = "";
      double[] percentages = null;
      try {
        String input = view.getInputData();
        String[] individualInput = input.trim().split(" ");
        amount = Double.parseDouble(individualInput[0]);
        date = individualInput[1];
        percentages = new double[individualInput.length - 2];
        for (int i = 2; i < individualInput.length; i++) {
          percentages[i - 2] = Double.parseDouble(individualInput[i]);
        }
      } catch (Exception e) {
        view.errorMessage("Error processing inputs");
      }
      String status = "";
      try {
        status = model.investMoney(amount, date, percentages);
      } catch (IllegalStateException s) {
        view.errorMessage("Sum of percentages should be 100");
      } catch (IllegalArgumentException a) {
        view.errorMessage("Illegal date format");
      } catch (Exception e) {
        view.errorMessage("Trending cannot be done on holidays");
      }
      view.operationSuccessful(status);
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void regularlyInvestMoney() {
    if (portfolioExists) {
      double amount = 0.0;
      String startDate = "";
      String endDate = "";
      int interval = 1;
      try {
        String input = view.getInputData();
        String[] individualInput = input.trim().split(" ");
        if (individualInput.length == 3) {
          amount = Double.parseDouble(individualInput[0]);
          startDate = individualInput[1];
          interval = Integer.parseInt(individualInput[2]);
        } else if (individualInput.length == 4) {
          amount = Double.parseDouble(individualInput[0]);
          startDate = individualInput[1];
          endDate = individualInput[2];
          interval = Integer.parseInt(individualInput[3]);
        } else {
          throw new IllegalArgumentException("Incorrect number of inputs ");
        }
      } catch (Exception e) {
        view.errorMessage("Error processing inputs - Regularly Invest money ");
      }
      String status = "";
      try {
        if (endDate.equals("")) {
          status = model.regularInvestMoney(amount, startDate, interval);
        } else {
          status = model.regularInvestMoney(amount, startDate, endDate, interval);
        }
      } catch (IllegalStateException s) {
        view.errorMessage("Trending cannot be done on holidays");
      } catch (IllegalArgumentException a) {
        view.errorMessage("Illegal date format");
      }
      view.operationSuccessful(status);
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void regularlyInvestMoneyPercent() {
    if (portfolioExists) {
      double amount = 0.0;
      String startDate = "";
      int interval = 1;
      double[] percentages = null;
      try {
        String input = view.getInputData();
        String[] individualInput = input.trim().split(" ");
        amount = Double.parseDouble(individualInput[0]);
        startDate = individualInput[1];
        interval = Integer.parseInt(individualInput[2]);
        percentages = new double[individualInput.length - 3];
        for (int i = 3; i < individualInput.length; i++) {
          percentages[i - 3] = Double.parseDouble(individualInput[i]);
        }
      } catch (Exception e) {
        view.errorMessage("Error processing inputs");
      }
      String status = "";
      try {
        status = model.regularInvestMoneyWithPercentage(amount, startDate, interval, percentages);
      } catch (IllegalStateException s) {
        view.errorMessage("Sum of percentage weights should be 100");
      } catch (IllegalArgumentException a) {
        view.errorMessage("Illegal date format");
      }
      view.operationSuccessful(status);
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void regularlyInvestMoneyPercentEnd() {
    if (portfolioExists) {
      double amount = 0.0;
      String startDate = "";
      String endDate = "";
      int interval = 1;
      double[] percentages = null;
      try {
        String input = view.getInputData();
        String[] individualInput = input.trim().split(" ");
        amount = Double.parseDouble(individualInput[0]);
        startDate = individualInput[1];
        endDate = individualInput[2];
        interval = Integer.parseInt(individualInput[3]);
        percentages = new double[individualInput.length - 4];
        for (int i = 4; i < individualInput.length; i++) {
          percentages[i - 4] = Double.parseDouble(individualInput[i]);
        }
      } catch (Exception e) {
        view.errorMessage("Error processing inputs - Regularly Invest money ");
      }
      String status = "";
      try {
        status = model.regularInvestMoneyWithPercentage(amount, startDate, endDate,
                interval, percentages);
      } catch (IllegalStateException s) {
        view.errorMessage("Sum of percentage weights should be 100");
      } catch (IllegalArgumentException a) {
        view.errorMessage("Illegal date format");
      }
      view.operationSuccessful(status);
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void sellShare() {
    if (portfolioExists) {
      String shareName = "";
      String date = "";
      int shareQuantity = 0;
      try {
        String input = view.getInputData();
        String[] individualInput = input.trim().split(" ");
        if (individualInput.length == 2) {
          shareName = individualInput[0];
          date = individualInput[1];
          model.sellShares(shareName, date);
          view.operationSuccessful("Shares of " + shareName + "Sold Successfully");
        } else if (individualInput.length == 3) {
          shareName = individualInput[0];
          shareQuantity = Integer.parseInt(individualInput[1]);
          date = individualInput[2];
          model.sellShares(shareName, shareQuantity, date);
          view.operationSuccessful(shareQuantity + " Shares of " + shareName + "Sold Successfully");
        } else {
          throw new IllegalArgumentException("Incorrect number of arguments");
        }
      } catch (IllegalStateException e) {
        view.errorMessage("Multiple shares of the given share available, Please" +
                " provide additional details.");
      } catch (Exception e) {
        view.errorMessage("Something went wrong");
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void sellShareMulti() {
    if (portfolioExists) {
      String shareName = "";
      String date = "";
      int shareQuantity = 0;
      double cost = 0;
      try {
        String input = view.getInputData();
        String[] individualInput = input.trim().split(" ");
        if (individualInput.length == 3) {
          shareName = individualInput[0];
          cost = Double.parseDouble(individualInput[1]);
          date = individualInput[2];
          model.sellShares(shareName, cost, date);
          view.operationSuccessful("Shares of " + shareName + " Costing "
                  + cost + " Sold Successfully");
        } else if (individualInput.length == 4) {
          shareName = individualInput[0];
          shareQuantity = Integer.parseInt(individualInput[1]);
          cost = Double.parseDouble(individualInput[2]);
          date = individualInput[3];
          model.sellShares(shareName, shareQuantity, cost, date);
          view.operationSuccessful(shareQuantity + " Shares of " + shareName + " Costing "
                  + cost + " Sold Successfully");
        } else {
          throw new IllegalArgumentException("Invalid number of arguments");
        }
      } catch (Exception z) {
        view.errorMessage("Unable to process the input");
      }
    } else {
      view.errorMessage("Select portfolio first");
    }
  }

  private void getDetails() {
    if (portfolioExists) {
      try {
        String date = view.getInputData();
        if (date.equals("")) {
          String data = model.getCurrentPortfolioDetails();
          view.operationSuccessful("Current portfolio Details : "
                  + data);
        } else {
          String data = model.getCurrentPortfolioDetails(date);
          view.operationSuccessful("Current portfolio Details on " + date + " : "
                  + data);
        }

      } catch (Exception e) {
        view.errorMessage("Unable to process the input" + e);
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void getCostBasis() {
    if (portfolioExists) {
      try {
        String date = view.getInputData();
        String data = model.getCostBasisOnDate(date);
        view.operationSuccessful("Cost Basis on " + date + " : " + data);
      } catch (Exception e) {
        view.errorMessage("Unable to process the input" + e);
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void savePortfolio() {
    if (portfolioExists) {
      String portfolioName = "";
      try {
        portfolioName = view.getInputData();
      } catch (Exception e) {
        view.errorMessage("Unable to process input");
      }
      try {
        model.savePortfolioInFile(portfolioName);
        view.operationSuccessful("Portfolio Saved Successfully");
      } catch (Exception e) {
        view.errorMessage("File already Exist.");
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void saveStrategy() {
    if (portfolioExists) {
      String strategyName = "";
      try {
        strategyName = view.getInputData();
      } catch (Exception e) {
        view.errorMessage("Unable to process input");
      }
      try {
        model.saveInvestmentInFile(strategyName);
        view.operationSuccessful("Strategy Saved Successfully");
      } catch (Exception e) {
        view.errorMessage("File already Exist.");
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void loadPortfolio() {
    if (portfolioExists) {
      String portfolio = "";
      try {
        portfolio = view.getInputData();
      } catch (Exception e) {
        view.errorMessage("Unable to process input");
      }
      try {
        model.retrivePortfolioFromFile(portfolio);
        view.operationSuccessful("Portfolio retrieved Successfully");
      } catch (Exception e) {
        view.errorMessage("File does Not Exist.");
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void loadStrategy() {
    if (portfolioExists) {
      String strategy = "";
      try {
        strategy = view.getInputData();
      } catch (Exception e) {
        view.errorMessage("Unable to process input");
      }
      try {
        model.retriveInvestmentFromFile(strategy);
        view.operationSuccessful("Strategy retrieved Successfully");
      } catch (Exception e) {
        view.errorMessage("File does Not Exist.");
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void removePortfolio() {
    String portfolioName = "";
    try {
      portfolioName = view.getInputData();
      System.out.print(portfolioName);
    } catch (Exception e) {
      view.errorMessage("Kindly enter the name of the portfolio " +
              "that needs to be deleted. " +
              "The name of the portfolio cannot be empty.");
    }
    try {
      model.removePortfolio(portfolioName);
      view.operationSuccessful(portfolioName + " removed successfully");
    } catch (Exception e) {
      view.errorMessage("Portfolio profile does not exist.");
    }
  }

}
