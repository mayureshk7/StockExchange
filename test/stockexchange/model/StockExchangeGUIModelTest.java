package stockexchange.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StockExchangeGUIModelTest {

  @Test
  public void testRetrievePortfolio() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(2.0);
    se.displayShareDetails("AAPL", "2017-10-11");
    se.buyShares("AAPL", 4);
    se.displayShareDetails("GE", "2017-10-12");
    se.buyShares("GE", 8);
    se.displayShareDetails("HE", "2017-10-13");
    se.buyShares("HE", 18);
    se.savePortfolioInFile("hello1");
    StockExchangeModel sc = new StockExchangeModel();
    sc.retrivePortfolioFromFile("hello1");
    assertEquals(se.getCurrentPortfolioDetails(),
            sc.getCurrentPortfolioDetails());
  }

  @Test(expected = IllegalStateException.class)
  public void testRetrievePortfolioAlreadyExists() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(2.0);
    se.displayShareDetails("AAPL", "2017-10-11");
    se.buyShares("AAPL", 4);
    se.displayShareDetails("GE", "2017-10-12");
    se.buyShares("GE", 8);
    se.displayShareDetails("HE", "2017-10-13");
    se.buyShares("HE", 18);
    se.savePortfolioInFile("hello1");
    StockExchangeModel sc = new StockExchangeModel();
    sc.retrivePortfolioFromFile("hello1");
    assertEquals(se.getCurrentPortfolioDetails(),
            sc.getCurrentPortfolioDetails());
  }

  @Test(expected = IllegalStateException.class)
  public void testRetrievePortfolioFileDoesNotExist() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(2.0);
    se.displayShareDetails("AAPL", "2017-10-11");
    se.buyShares("AAPL", 4);
    se.displayShareDetails("GE", "2017-10-12");
    se.buyShares("GE", 8);
    se.displayShareDetails("HE", "2017-10-13");
    se.buyShares("HE", 18);
    se.savePortfolioInFile("hello2");
    StockExchangeModel sc = new StockExchangeModel();
    sc.retrivePortfolioFromFile("hello3");
    assertEquals(se.getCurrentPortfolioDetails(),
            sc.getCurrentPortfolioDetails());
  }

  @Test
  public void testRetrieveStrategy() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(2.0);
    se.displayShareDetails("AAPL", "2017-10-11");
    se.buyShares("AAPL", 4);
    se.displayShareDetails("GE", "2017-10-12");
    se.buyShares("GE", 8);
    se.displayShareDetails("HE", "2017-10-13");
    se.buyShares("HE", 18);
    se.saveInvestmentInFile("strat1");
    StockExchangeModel sc = new StockExchangeModel();
    sc.retriveInvestmentFromFile("strat1");
    assertEquals(se.getCurrentPortfolioDetails(),
            sc.getCurrentPortfolioDetails());
  }

  @Test(expected = IllegalStateException.class)
  public void testRetrieveStrategyAlreadyExists() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(2.0);
    se.displayShareDetails("AAPL", "2017-10-11");
    se.buyShares("AAPL", 4);
    se.displayShareDetails("GE", "2017-10-12");
    se.buyShares("GE", 8);
    se.displayShareDetails("HE", "2017-10-13");
    se.buyShares("HE", 18);
    se.saveInvestmentInFile("strat1");
    StockExchangeModel sc = new StockExchangeModel();
    sc.retriveInvestmentFromFile("strat1");
    assertEquals(se.getCurrentPortfolioDetails(),
            sc.getCurrentPortfolioDetails());
  }

  @Test(expected = IllegalStateException.class)
  public void testRetrieveStrategyFileDoesNotExist() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(2.0);
    se.displayShareDetails("AAPL", "2017-10-11");
    se.buyShares("AAPL", 4);
    se.displayShareDetails("GE", "2017-10-12");
    se.buyShares("GE", 8);
    se.displayShareDetails("HE", "2017-10-13");
    se.buyShares("HE", 18);
    se.saveInvestmentInFile("strat2");
    StockExchangeModel sc = new StockExchangeModel();
    sc.retriveInvestmentFromFile("hello3");
    assertEquals(se.getCurrentPortfolioDetails(),
            sc.getCurrentPortfolioDetails());
  }
}