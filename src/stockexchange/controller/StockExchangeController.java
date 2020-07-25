package stockexchange.controller;

import java.io.InputStream;
import java.util.Scanner;

import stockexchange.model.StockExchangeModel;
import stockexchange.view.IStockExchangeView;

/**
 * StockExchangeController class is the controller of the StockExchangeModel, it implements
 * IStockExchangeController and all its methods.
 */
public class StockExchangeController implements IStockExchangeController {

  boolean portfolioExists = false;
  boolean shareDisplayed = false;
  private InputStream input;
  private boolean outerCondition = true;

  /**
   * Constructs an object of StockExchangeModel and StockExchangeView and initialises the
   * inputstream.
   *
   * @param input is the input that will be taken from the user from view
   */
  public StockExchangeController(InputStream input) {
    this.input = input;
  }

  @Override
  public void run(StockExchangeModel model, IStockExchangeView view) throws Exception {
    Scanner sc = new Scanner(input);
    while (outerCondition) {
      view.displayOptions();
      int option = -1;

      try {
        if (sc.hasNext()) {
          option = Integer.parseInt(sc.next());
        }
      } catch (Exception e) {
        view.errorMessage("Incorrect option, Please select an option from 1 to 16");
        continue;
      }
      /**
       * To check the StockExchangeModel and each of its method
       */
      switch (option) {
        //for create portfolio
        case 1: {
          createPortfolio(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //for select portfolio
        case 2: {
          selectPortfolio(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //to set Commission
        case 3: {
          setCommission(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //to display shares
        case 4: {
          displayShare(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //buy Shares
        case 5: {
          buyShares(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //invest money
        case 6: {
          investMoney(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //invest money with percentage
        case 7: {
          investMoneyWithPercent(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //regular money investment Shares
        case 8: {
          regularInvestMoney(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //regular money investment Shares
        case 9: {
          view.instructionMessage("Select Format : \n1. Without specifying End date" +
                  "\n2. With End date : ");
          sc.nextLine();
          switch (Integer.parseInt(sc.nextLine())) {
            case 1: {
              regularInvestMoneyWithWeights(sc, model, view);
            }
            break;
            case 2: {
              regularInvestMoneyWithWeightsEndDate(sc, model, view);
            }
            break;
            default:
              view.errorMessage("Wrong Choice");
          }
          pressAnyKeyToContinue(view);
        }
        break;

        //Sell All Shares
        case 10: {
          sellShares(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //getCurrentPortfolioDetails
        case 11: {
          getCurrentPortfolioDetails(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //getCurrentPortfolioDetails on day
        case 12: {
          getCurrentPortfolioDetailsOnDate(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //get current cost basis
        case 13: {
          getCurrentCostBasis(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //get cost basis on day
        case 14: {
          getCostBasisOnDate(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //save portfolio
        case 15: {
          savePortfolio(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        case 16: {
          saveInvestmentStrategy(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        case 17: {
          loadPortfolio(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        case 18: {
          loadInvetmentStrategy(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        //remove portfolio
        case 19: {
          removePortfolio(sc, model, view);
          pressAnyKeyToContinue(view);
        }
        break;

        case 20: {
          return;
        }

        default: {
          view.errorMessage("Incorrect option, Please select and option from 1 to 16");
        }
      }
    }
  }

  private void createPortfolio(Scanner sc, StockExchangeModel model, IStockExchangeView view) {
    view.instructionMessage("Enter the Name of the portfolio that you would like to create:");
    view.instructionMessage("FORMAT: portfolioName");
    String portfolioName = "";

    try {
      portfolioName = sc.next();
    } catch (Exception e) {
      view.errorMessage("Kindly enter the name of the portfolio." +
              "The name of the portfolio cannot be empty.");

    }
    try {
      model.createPortfolio(portfolioName);
      view.operationSuccessful(portfolioName + " Created Successfully");

    } catch (Exception e) {
      view.errorMessage("The name of the portfolio already exists");
    }
  }

  private void selectPortfolio(Scanner sc, StockExchangeModel model, IStockExchangeView view) {
    view.instructionMessage("Select the Name of the portfolio " +
            "that you would like to view/update:");
    view.instructionMessage("FORMAT: portfolioName");
    String portfolioName = "";

    try {
      portfolioName = sc.next();//or create a method in view to get the portfolio name
    } catch (Exception e) {
      view.errorMessage("Kindly enter the name of the portfolio." +
              "The name of the portfolio to be selected cannot be empty.");

    }
    try {
      model.selectPortfolio(portfolioName);
      view.operationSuccessful(portfolioName + " Selected successfully. You can now " +
              "view/update this portfolio.");
      portfolioExists = true;

    } catch (Exception e) {
      view.errorMessage("Portfolio profile does not exist.");
    }
  }

  private void setCommission(Scanner sc, StockExchangeModel model, IStockExchangeView view) {
    view.instructionMessage("Enter the Commission Value");
    view.instructionMessage("FORMAT: commission");
    double commission = 0.0;
    try {
      commission = Double.parseDouble(sc.next());
    } catch (Exception e) {
      view.errorMessage("Unable to parse the input value");
    }
    try {
      model.setCommission(commission);
      view.instructionMessage("Value of Commission set to : " + commission);
    } catch (IllegalStateException s) {
      view.errorMessage("Value of Commission cannot be negative");
    } catch (IllegalArgumentException a) {
      view.errorMessage("Select Portfolio first");
    }

  }

  private void displayShare(Scanner sc, StockExchangeModel model, IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Name(Ticker Symbol) of the Shares " +
              "and the Date(yyyy-MM-dd) to know the details of the " +
              "shares on that particular day:");
      view.instructionMessage("FORMAT: ShareName Date(yyyy-MM-dd)");

      String shareName = "";
      String shareOnDate = "";

      try {
        sc.nextLine();
        String input = sc.nextLine();
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
        view.operationSuccessful("Here are the stock details requested.");
        view.operationSuccessful(model.displayShareDetails(shareName, shareOnDate));
        shareDisplayed = true;
      } catch (Exception e) {
        view.errorMessage("Data for date not available");
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void buyShares(Scanner sc, StockExchangeModel model, IStockExchangeView view) {
    if (portfolioExists) {
      if (shareDisplayed) {
        view.instructionMessage("Enter the Name(Ticker Symbol) of the Shares " +
                "you want to buy and the number of stocks:");
        view.instructionMessage("FORMAT: TickerSymbol QtyOfShares");

        String shareName = "";
        int sharesQuantity = 0;

        try {
          sc.nextLine();
          String input = sc.nextLine();
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

  private void investMoney(Scanner sc, StockExchangeModel model, IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Amount to be Invested and date:");
      view.instructionMessage("FORMAT: Amount date");

      double amount = 0.0;
      String date = "";
      try {
        sc.nextLine();
        String input = sc.nextLine();
        String[] individualInput = input.trim().split(" ");
        if (individualInput.length == 2) {
          amount = Double.parseDouble(individualInput[0]);
          date = individualInput[1];
        } else {
          view.errorMessage("Incorrect number of inputs");
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

  private void investMoneyWithPercent(Scanner sc, StockExchangeModel model,
                                      IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Amount to be Invested, date " +
              "and percentage splits of shares:");
      view.instructionMessage("FORMAT: Amount date(YYYY-MM-DD) percentage_splits");

      double amount = 0.0;
      String date = "";
      double[] percentages = null;
      try {
        sc.nextLine();
        String input = sc.nextLine();
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

  private void regularInvestMoney(Scanner sc, StockExchangeModel model, IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Amount to be Invested, Start date" +
              "(and End date - Optional) and Interval (In days):");
      view.instructionMessage("FORMAT: Amount Start_Date (Opetional - End_Date) Inverval");

      double amount = 0.0;
      String startDate = "";
      String endDate = "";
      int interval = 1;
      try {
        sc.nextLine();
        String input = sc.nextLine();
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

  private void regularInvestMoneyWithWeights(Scanner sc, StockExchangeModel model,
                                             IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Amount to be Invested, Start date" +
              ", Interval (In days) and Percentage Weights :");
      view.instructionMessage("FORMAT: Amount Start_Date Interval Percentage_Weights");

      double amount = 0.0;
      String startDate = "";
      int interval = 1;
      double[] percentages = null;
      try {
        String input = sc.nextLine();
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

  private void regularInvestMoneyWithWeightsEndDate(Scanner sc, StockExchangeModel model,
                                                    IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Amount to be Invested, Start Date" +
              ", End Date, Interval (In days) and Percentage Weights :");
      view.instructionMessage("FORMAT: Amount Start_Date End_Date " +
              "Interval Percentage_Weights");

      double amount = 0.0;
      String startDate = "";
      String endDate = "";
      int interval = 1;
      double[] percentages = null;
      try {
        String input = sc.nextLine();
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

  private void sellShares(Scanner sc, StockExchangeModel model, IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Name(Ticker Symbol) of the Share " +
              "and date on which you want to Sell. If you want to sell few of the  " +
              "share then mention quantity : ");
      view.instructionMessage("FORMAT: TickerSymbol date");
      view.instructionMessage("or\nFORMAT: TickerSymbol Quantity Date");
      String shareName = "";
      String date = "";
      int shareQuantity = 0;
      double cost = 0.0;
      try {
        sc.nextLine();
        String input = sc.nextLine();
        String[] individualInput = input.trim().split(" ");
        if (individualInput.length == 2) {
          shareName = individualInput[0];
          date = individualInput[1];
          model.sellShares(shareName, date);
        } else if (individualInput.length == 3) {
          shareName = individualInput[0];
          shareQuantity = Integer.parseInt(individualInput[1]);
          date = individualInput[2];
          model.sellShares(shareName, shareQuantity, date);
        } else {
          throw new IllegalArgumentException("Incorrect number of arguments");
        }
      } catch (IllegalStateException e) {
        view.errorMessage("Multiple shares of the given share available, Please" +
                "provide additional details.");
        view.instructionMessage("Enter the Name(Ticker Symbol) " + "of the Shares," +
                " the Cost of shares and date on which " +
                "you want to Sell :");
        view.instructionMessage("If you want to sell some of the shares mention " +
                "quantity of shares to be sold: ");
        view.instructionMessage("FORMAT: TickerSymbol " +
                "CostOfShares Date");
        view.instructionMessage("or\nFORMAT: TickerSymbol QtyOfShares " +
                "CostOfShares Date");
        try {
          String input = sc.nextLine();
          String[] individualInput = input.trim().split(" ");
          if (individualInput.length == 3) {
            shareName = individualInput[0];
            cost = Double.parseDouble(individualInput[1]);
            date = individualInput[2];
            model.sellShares(shareName, cost, date);
          } else if (individualInput.length == 4) {
            shareName = individualInput[0];
            shareQuantity = Integer.parseInt(individualInput[1]);
            cost = Double.parseDouble(individualInput[2]);
            date = individualInput[3];
            model.sellShares(shareName, shareQuantity, cost, date);
          } else {
            throw new IllegalArgumentException("Invalid number of arguments");
          }
        } catch (Exception z) {
          view.errorMessage("Unable to process the input");
        }
      } catch (Exception e) {
        view.errorMessage("Something went wrong");
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void getCurrentPortfolioDetails(Scanner sc, StockExchangeModel model,
                                          IStockExchangeView view) {
    if (portfolioExists) {
      try {
        model.getCurrentPortfolioDetails();
        view.operationSuccessful("Current portfolio Details.");
        view.operationSuccessful(model.getCurrentPortfolioDetails());

      } catch (Exception e) {
        view.errorMessage("Select portfolio first");
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void getCurrentPortfolioDetailsOnDate(Scanner sc, StockExchangeModel model,
                                                IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the date on which portfolio details are needed : ");
      try {
        sc.nextLine();
        String date = sc.next();
        view.operationSuccessful("Current portfolio Details.");
        view.operationSuccessful(model.getCurrentPortfolioDetails(date));

      } catch (Exception e) {
        view.errorMessage("Unable to process the input" + e);
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void getCurrentCostBasis(Scanner sc, StockExchangeModel model,
                                   IStockExchangeView view) {
    if (portfolioExists) {
      try {
        view.operationSuccessful("Current Cost Basis : ");
        view.operationSuccessful(model.getCurrentCostBasis());

      } catch (Exception e) {
        view.errorMessage("Unable to process the input" + e);
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void getCostBasisOnDate(Scanner sc, StockExchangeModel model,
                                  IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the date on which Cost basis is needed : ");
      try {
        sc.nextLine();
        String date = sc.next();
        view.operationSuccessful("Cost Basis on " + date + " : ");
        view.operationSuccessful(model.getCostBasisOnDate(date));

      } catch (Exception e) {
        view.errorMessage("Unable to process the input" + e);
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void removePortfolio(Scanner sc, StockExchangeModel model, IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Name of the portfolio that " +
              "you would like to remove:");
      view.instructionMessage("FORMAT: portfolioName");
      String portfolioName = "";
      boolean condition = true;
      try {
        portfolioName = sc.next();
      } catch (Exception e) {
        view.errorMessage("Kindly enter the name of the portfolio " +
                "that needs to be deleted." +
                "The name of the portfolio cannot be empty.");
        condition = false;
      }
      try {
        model.removePortfolio(portfolioName);
        view.operationSuccessful(portfolioName + " removed successfully");
        condition = false;
      } catch (Exception e) {
        view.errorMessage("Portfolio profile does not exist.");
      }
    } else {
      view.errorMessage("Select Portfolio first");
    }
  }

  private void pressAnyKeyToContinue(IStockExchangeView view) {
    view.instructionMessage("Press Enter key to continue...");
    try {
      System.in.read();
    } catch (Exception e) {
      view.errorMessage("Press Enter key to continue...");
    }
  }


  private void savePortfolio(Scanner sc, StockExchangeModel model, IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Name of the file in which " +
              "portfolio needs to be saved :");
      view.instructionMessage("FORMAT: fileName");
      String portfolioName = "";

      try {
        portfolioName = sc.next();
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

  private void saveInvestmentStrategy(Scanner sc, StockExchangeModel model,
                                      IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Name of the file in which " +
              "invest Strategy needs to be saved :");
      view.instructionMessage("FORMAT: fileName");
      String strategyName = "";

      try {
        strategyName = sc.next();
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

  private void loadPortfolio(Scanner sc, StockExchangeModel model, IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Name of the file from which " +
              "portfolio needs to be retrieved :");
      view.instructionMessage("FORMAT: fileName");
      String portfolio = "";

      try {
        portfolio = sc.next();
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

  private void loadInvetmentStrategy(Scanner sc, StockExchangeModel model,
                                     IStockExchangeView view) {
    if (portfolioExists) {
      view.instructionMessage("Enter the Name of the file from which " +
              "Investment Strategy needs to be retrieved :");
      view.instructionMessage("FORMAT: fileName");
      String strategy = "";

      try {
        strategy = sc.next();
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

}