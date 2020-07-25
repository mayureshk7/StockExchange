package stockexchange.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class StockExchangeModelTest {

  @Test
  public void checkCreation() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.getCurrentPortfolioDetails().contains("FirstProfile"));
  }

  @Test
  public void checkCreationMultiple() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.getCurrentPortfolioDetails().contains("FirstProfile"));
    se.createPortfolio("SecondProfile");
    se.selectPortfolio("SecondProfile");
    assertTrue(se.getCurrentPortfolioDetails().contains("SecondProfile"));
    se.createPortfolio("ThirdProfile");
    se.selectPortfolio("ThirdProfile");
    assertTrue(se.getCurrentPortfolioDetails().contains("ThirdProfile"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkCreationFail() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.createPortfolio("FirstProfile");
    assertTrue(se.getCurrentPortfolioDetails().contains("FirstProfile"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkSelectFail() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("SecondProfile");
    assertTrue(se.getCurrentPortfolioDetails().contains("SecondProfile"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkDisplayWrongDate() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "").contains("GOOG"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkDisplayWrongTicker() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("", "2013-12-12").contains("GOOG"));
  }

  @Test
  public void checkDisplay() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-14")
            .contains("GOOG") && se.displayShareDetails("GOOG", "2018-11-14")
            .contains("2018-11-14"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkDisplayWasWeekend() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-11")
            .contains("GOOG") && se.displayShareDetails("GOOG", "2018-11-11")
            .contains("2018-11-11"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkDisplayWasHoliday() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-11")
            .contains("GOOG") && se.displayShareDetails("GOOG", "2018-11-11")
            .contains("2018-11-11"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkDisplayIncorrectTicker() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("XAFASFASFADFAD", "2018-11-11")
            .contains("XAFASFASFADFAD"));
  }

  @Test
  public void checkBuyShare() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG") && se.displayShareDetails("GOOG", "2018-11-13")
            .contains("2018-11-13"));
    se.buyShares("GOOG", 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkBuyShareFail() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG") && se.displayShareDetails("GOOG", "2018-11-13")
            .contains("2018-11-13"));
    se.buyShares("GOOG", -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkBuyShareError() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG") && se.displayShareDetails("GOOG", "2018-11-13")
            .contains("2018-11-13"));
    se.buyShares("GOOG", 1000000);
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG"));
  }

  @Test
  public void checkBuyShareMultiple() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG") && se.displayShareDetails("GOOG", "2018-11-13")
            .contains("2018-11-13"));
    se.buyShares("GOOG", 10);
    assertTrue(se.displayShareDetails("HE", "2018-11-13")
            .contains("HE") && se.displayShareDetails("HE", "2018-11-13")
            .contains("2018-11-13"));
    se.buyShares("HE", 10);
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG") &&
            se.getCurrentPortfolioDetails().contains("HE"));
  }

  @Test
  public void checkBuyShareMultipleOfSameSameShares() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG"));
    se.buyShares("GOOG", 10);
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG"));
    se.buyShares("GOOG", 20);
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG") &&
            se.getCurrentPortfolioDetails().contains("Number of Shares: 30"));
  }

  @Test
  public void checkBuyShareMultipleOfSameDifferentShares() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG"));
    se.buyShares("GOOG", 10);
    assertTrue(se.displayShareDetails("GOOG", "2018-11-14")
            .contains("GOOG"));
    se.buyShares("GOOG", 20);
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG") &&
            se.getCurrentPortfolioDetails().contains("Number of Shares: 10") &&
            se.getCurrentPortfolioDetails().contains("Number of Shares: 20"));
  }


  @Test
  public void checksellShareSome() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG") && se.displayShareDetails("GOOG", "2018-11-13")
            .contains("2018-11-13"));
    se.buyShares("GOOG", 10);
    assertTrue(se.displayShareDetails("HE", "2018-11-13")
            .contains("HE") || se.displayShareDetails("HE", "2018-11-13")
            .contains("2018-11-13"));
    se.buyShares("HE", 10);
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG") &&
            se.getCurrentPortfolioDetails().contains("HE"));
    se.sellShares("HE", 3, "2018-11-14");
    assertTrue(se.getCurrentPortfolioDetails().contains("HE") &&
            se.getCurrentPortfolioDetails().contains("Number of Shares: 7"));
  }

  @Test
  public void checksellShareAll() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG") && se.displayShareDetails("GOOG", "2018-11-13")
            .contains("2018-11-13"));
    se.buyShares("GOOG", 10);
    assertTrue(se.displayShareDetails("HE", "2018-11-13")
            .contains("HE") && se.displayShareDetails("HE", "2018-11-13")
            .contains("2018-11-13"));
    se.buyShares("HE", 10);
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG") &&
            se.getCurrentPortfolioDetails().contains("HE"));
    se.sellShares("HE", "2018-11-14");
    assertFalse(se.getCurrentPortfolioDetails().contains("HE"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checksellShareMultipleSpecifyPriceFail() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG") && se.displayShareDetails("GOOG", "2018-11-13")
            .contains("2018-11-13"));
    se.buyShares("GOOG", 10);
    assertTrue(se.displayShareDetails("GOOG", "2018-11-14")
            .contains("GOOG"));
    se.buyShares("GOOG", 10);
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG"));
    se.sellShares("GOOG", 3, "2018-11-14");
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG") &&
            se.getCurrentPortfolioDetails().contains("Number of Shares: 7"));
  }


  @Test
  public void checksellShareMultipleSpecifyPrice() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG") || se.displayShareDetails("GOOG", "2018-11-13")
            .contains("2018-11-13"));

    se.buyShares("GOOG", 10);
    assertTrue(se.displayShareDetails("GOOG", "2018-11-14")
            .contains("GOOG"));
    se.buyShares("GOOG", 10);
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG"));
    System.out.print(se.getCurrentPortfolioDetails());
    se.sellShares("GOOG", 3, 1031.15, "2018-11-14");
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG") &&
            se.getCurrentPortfolioDetails().contains("Number of Shares: 7"));
  }

  @Test
  public void checksellShareMultipleSpecifyPriceAll() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG") || se.displayShareDetails("GOOG", "2018-11-13")
            .contains("2018-11-13"));

    se.buyShares("GOOG", 10);
    assertTrue(se.displayShareDetails("GOOG", "2018-11-14")
            .contains("GOOG"));
    se.buyShares("GOOG", 10);
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG"));
    System.out.print(se.getCurrentPortfolioDetails());
    se.sellShares("GOOG", 1031.15, "2018-11-14");
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG") &&
            se.getCurrentPortfolioDetails().contains("Number of Shares: 10"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void checksellShareMultipleNoPrice() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG") || se.displayShareDetails("GOOG", "2018-11-13")
            .contains("2018-11-13"));

    se.buyShares("GOOG", 10);
    assertTrue(se.displayShareDetails("GOOG", "2018-11-14")
            .contains("GOOG"));
    se.buyShares("GOOG", 10);
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG"));
    System.out.print(se.getCurrentPortfolioDetails());
    se.sellShares("GOOG", "2018-11-14");
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG") &&
            se.getCurrentPortfolioDetails().contains("Number of Shares: 10"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void checksellShareMultipleSpecifyPriceNoPrice() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    assertTrue(se.displayShareDetails("GOOG", "2018-11-13")
            .contains("GOOG") || se.displayShareDetails("GOOG", "2018-11-13")
            .contains("2018-11-13"));

    se.buyShares("GOOG", 10);
    assertTrue(se.displayShareDetails("GOOG", "2018-11-14")
            .contains("GOOG"));
    se.buyShares("GOOG", 10);
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG"));
    se.sellShares("GOOG", 131.15, "2018-11-14");
    assertTrue(se.getCurrentPortfolioDetails().contains("GOOG"));
  }

  @Test(expected = IllegalStateException.class)
  public void checkCommissionFail() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(-2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    assertTrue(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 2.0"));
  }

  @Test
  public void checkCommissionSuccess() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    assertEquals(se.getCurrentCostBasis(), "10434.6");
    assertTrue(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 2.0"));
  }

  @Test
  public void checkCommissionSuccessMultiple() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GOOG", "2018-11-15");
    se.buyShares("GOOG", 10);
    assertEquals(se.getCurrentCostBasis(), "21079.7");
    assertTrue(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 4.0"));
  }

  @Test
  public void checkCommissionSuccessMultipleDifferent() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GOOG", "2018-11-15");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-15");
    se.buyShares("GOOG", 10);
    assertEquals(se.getCurrentCostBasis(), "21240.600000000002");
    assertTrue(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 8.0"));
  }

  @Test
  public void checkInvestMoney() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    se.investMoney(10000, "2018-11-14");
    se.investMoney(10000, "2018-11-15");
    assertTrue(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 12.0"));
    assertFalse(se.getCurrentPortfolioDetails().contains("Number of Shares: 10"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkInvestMoneyFail() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    se.investMoney(10000, "2018-11-11");
    assertTrue(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 12.0"));
    assertFalse(se.getCurrentPortfolioDetails().contains("Number of Shares: 10"));
  }

  @Test
  public void checkInvestMoneyInPercentage() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    se.investMoney(10000, "2018-11-14", new double[]{60.0, 40.0});
    assertTrue(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 8.0"));
    assertFalse(se.getCurrentPortfolioDetails().contains("Number of Shares: 10"));
  }

  @Test(expected = IllegalStateException.class)
  public void checkInvestMoneyInPercentageFail() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    se.investMoney(10000, "2018-11-14", new double[]{60.0, 10.0});
    assertFalse(se.getCurrentPortfolioDetails().contains("Number of Shares: 10"));
  }

  @Test
  public void checkInvestRegularMoney() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 40);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    se.regularInvestMoney(10000, "2018-11-15", 1);
    assertFalse(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 4.0"));
    assertNotEquals(se.getCostBasisOnDate("2018-11-14"),
            se.getCostBasisOnDate("2018-11-17"));
  }

  @Test
  public void checkInvestRegularMoneyWithEnd() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    se.regularInvestMoney(10000, "2018-11-11",
            "2018-11-15", 1);
    assertFalse(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 4.0"));
    assertFalse(se.getCurrentPortfolioDetails().contains("Number of Shares: 10"));
  }

  @Test
  public void checkInvestRegularMoneyWithPercentage() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    se.regularInvestMoneyWithPercentage(10000, "2018-11-11",
            1, new double[]{60.0, 40.0});
    assertFalse(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 4.0"));
    assertFalse(se.getCurrentPortfolioDetails().contains("Number of Shares: 10"));
  }

  @Test
  public void checkInvestRegularMoneyWithEndWithPercentage() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    se.regularInvestMoneyWithPercentage(10000, "2018-11-11",
            "2018-11-15", 1, new double[]{60.0, 40.0});
    assertFalse(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 4.0"));
    assertFalse(se.getCurrentPortfolioDetails().contains("Number of Shares: 10"));
  }

  @Test
  public void checkPortfolioOnDate() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    assertTrue(se.getCurrentPortfolioDetails("2018-11-13")
            .contains("Total Investment : 0.0"));
  }

  @Test
  public void checkPortfolioOnDateMultiple() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    assertTrue(se.getCurrentPortfolioDetails("2018-11-13")
            .contains("Total Investment : 0.0"));
    assertFalse(se.getCurrentPortfolioDetails("2018-11-16")
            .contains("Total Investment : 0.0"));
    assertNotEquals(se.getCostBasisOnDate("2018-11-14"),
            se.getCostBasisOnDate("2018-11-17"));
  }

  @Test
  public void checkCostBasisRegularMoneyWithEnd() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    se.regularInvestMoney(10000, "2018-11-11",
            "2018-11-15", 1);
    assertTrue(se.getCurrentPortfolioDetails("2018-11-01")
            .contains("Total Investment : 0.0"));
    assertFalse(se.getCurrentPortfolioDetails("2018-11-16")
            .contains("Total Investment : 0.0"));
    assertNotEquals(se.getCostBasisOnDate("2018-11-14"),
            se.getCostBasisOnDate("2018-11-17"));
  }

  @Test
  public void checkCostBasisRegularMoneyWithEndWithPercentage() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("FirstProfile");
    se.selectPortfolio("FirstProfile");
    se.setCommission(2.0);
    se.displayShareDetails("GOOG", "2018-11-14");
    se.buyShares("GOOG", 10);
    se.displayShareDetails("GE", "2018-11-14");
    se.buyShares("GE", 20);
    se.regularInvestMoneyWithPercentage(10000, "2018-11-11",
            "2018-11-15", 1, new double[]{60.0, 40.0});
    assertTrue(se.getCurrentPortfolioDetails("2018-11-01")
            .contains("Total Investment : 0.0"));
    assertFalse(se.getCurrentPortfolioDetails("2018-11-16")
            .contains("Total Investment : 0.0"));
    assertNotEquals(se.getCostBasisOnDate("2018-11-14"),
            se.getCostBasisOnDate("2018-11-17"));
  }

  @Test(expected = IllegalStateException.class)
  public void testSetCommissionNegativeIncorrect() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(-7.0);
    se.displayShareDetails("HE", "2018-10-11");
    se.buyShares("HE", 4);
    assertTrue(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 7.0"));
  }

  @Test
  public void testSetCommissionCorrect() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(7.0);
    se.displayShareDetails("HE", "2018-10-11");
    se.buyShares("HE", 4);
    assertTrue(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 7.0"));
  }

  @Test
  public void testregularInvestMoneyCorrect() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(1.0);
    se.displayShareDetails("HE", "2017-02-02");
    se.buyShares("HE", 4);
    se.displayShareDetails("AAPL", "2017-02-02");
    se.buyShares("AAPL", 8);
    se.regularInvestMoney(75000, "2018-11-15", 1);
    assertNotEquals(se.getCostBasisOnDate("2017-02-02"),
            se.getCostBasisOnDate("2018-11-02"));
  }


  @Test
  public void testgetCurrentPOrtfolioDetailsWithDateCorrect() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(1.0);
    se.displayShareDetails("AAPL", "2018-10-11");
    se.buyShares("AAPL", 4);
    se.displayShareDetails("HE", "2018-10-11");
    se.buyShares("HE", 8);
    assertTrue(se.getCurrentPortfolioDetails("2018-10-10")
            .contains("Total Investment : 0.0"));
  }


  @Test
  public void testgetCostBasisWithDateCorrect() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(1.0);
    se.displayShareDetails("AAPL", "2018-10-11");
    se.buyShares("AAPL", 4);
    assertEquals(se.getCostBasisOnDate("2018-10-11"), "0.0");
  }


  @Test
  public void testgetCostBasisWithoutDateCorrect() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(1.0);
    se.displayShareDetails("HE", "2018-10-11");
    se.buyShares("HE", 8);
    assertEquals(se.getCurrentCostBasis(), "282.04");
  }

  @Test
  public void testregularInvestMoneyStartDateEndDate() {
    StockExchangeModel se = new StockExchangeModel();
    se.createPortfolio("P1");
    se.selectPortfolio("P1");
    se.setCommission(2.0);
    se.displayShareDetails("AAPL", "2017-10-11");
    se.buyShares("AAPL", 4);
    se.displayShareDetails("GE", "2017-10-11");
    se.buyShares("GE", 8);
    se.displayShareDetails("HE", "2017-10-11");
    se.buyShares("HE", 18);
    se.regularInvestMoney(700000, "2017-10-11",
            "2018-10-11", 30);
    assertFalse(se.getCurrentPortfolioDetails().contains("Total Spent as Commission : 78.0"));
    assertFalse(se.getCurrentPortfolioDetails().contains("Purchase Date : 2018-09-14"));
  }
}
