package stockexchange.controller;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringBufferInputStream;

import stockexchange.model.StockExchangeModel;
import stockexchange.view.StockExchangeView;

import static org.junit.Assert.assertTrue;

public class StockExchangeControllerTest {

  @Test
  public void testCreatePortfolioSelectPortfolio() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 hello 2 hello 3 GOOG 2018-11-14 16");
    //InputStream input = new StringBufferInputStream("1 hello 15");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Created Successfully"));
    System.setOut(save_out);
  }


  @Test
  public void testSelectPortfolioThatDoesnotExists() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 port1 2 port2 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Portfolio profile does not exist."));
    System.setOut(save_out);
  }


  @Test
  public void testRemovePortfolioWithoutSelection() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 port1 15 port1 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Select Portfolio first"));
    System.setOut(save_out);
  }


  @Test
  public void testRemovePortfolio() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 port1 2 port1 15 port1 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("removed successfully"));
    System.setOut(save_out);
  }


  @Test
  public void testPortfolioWithExistingName() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 port1 1 port1 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("The name of the portfolio already exists"));
    System.setOut(save_out);
  }


  @Test
  public void testDisplaySharesCorrect() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 EducationPortfolio 2 EducationPortfolio"
            + " 4 AAPL 2018-11-08 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Here are the stock details requested."));
    System.setOut(save_out);
  }


  @Test
  public void testDisplaySharesHolidaySat() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 EducationPortfolio 2 EducationPortfolio"
            + " 4 GOOG 2018-11-03 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Illegal date format"));
    System.setOut(save_out);
  }


  @Test
  public void testDisplaySharesHoliday() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 EducationPortfolio 2 EducationPortfolio"
            + "4 GOOG 2018-11-04 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Illegal date format"));
    System.setOut(save_out);
  }


  @Test
  public void testDisplaySharesHolidayNational() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 EducationPortfolio 2 EducationPortfolio"
            + " 4 GOOG 2018-12-25 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Data for date not available"));
    System.setOut(save_out);
  }


  @Test
  public void testBuySharesCorrect() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 EducationPortfolio 2 EducationPortfolio"
            + " 4 GOOG 2018-11-14 5 GOOG 2 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("bought successfully."));
    System.setOut(save_out);
  }


  @Test
  public void testDisplayPortfolioCorrect() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 EducationPortfolio 2 EducationPortfolio"
            + " 4 GOOG 2018-11-13 5 GOOG 6 11 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Current portfolio Details."));
    System.setOut(save_out);
  }


  @Test
  public void testbuySharesCorrect() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 EducationPortfolio 2 EducationPortfolio"
            + " 4 GOOG 2018-11-13 5 GOOG 6 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("bought successfully."));
    System.setOut(save_out);
  }


  @Test
  public void testsellSharesCorrect() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 p1 2 p1 4 GOOG 2018-11-13 5 GOOG 7 " +
            "10 GOOG 2018-11-14 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("sold successfully"));
    System.setOut(save_out);
  }


  @Test
  public void testsellSharesNotAvailableCorrect() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 p1 2 p1 4 GOOG 2018-11-13 5 GOOG 7 " +
            "10 AAPL 2018-11-14 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Does not have share"));
    System.setOut(save_out);
  }


  @Test
  public void testsellSharesWithQtyCorrect() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 p1 2 p1 4 GOOG 2018-11-13 5 GOOG 7 " +
            "10 GOOG 3 2018-11-14 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("sold successfully!"));
    System.setOut(save_out);
  }


  @Test
  public void testsellSharesNameQtyInCorrect() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 p1 2 p1 4 GOOG 2018-11-13 5 GOOG 7 " +
            "10 GOOG 8 2018-11-14 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Need to specify the cost value to delete."));
    System.setOut(save_out);
  }


  @Test
  public void testsellSharesNameWhenMultipleAvailableOftheSameCompany() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 p1 2 p1 4 GOOG 2018-11-13 5 GOOG 7 " +
            "4 GOOG 2018-11-14 5 GOOG 3 10 GOOG 2018-11-14 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Need to specify the cost value to delete."));
    System.setOut(save_out);
  }


  @Test
  public void testsellSharesNameQtyWhenMultipleAvailableOftheSameCompany() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 p1 2 p1 4 GOOG 2018-11-13 5 GOOG 7 " +
            "4 GOOG 2018-11-14 5 GOOG 3 10 GOOG 2018-11-14 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Need to specify the cost value to delete."));
    System.setOut(save_out);
  }


  @Test
  public void testsellSharesNameCost() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 p1 2 p1 4 GOOG 2018-11-13 5 GOOG 7 " +
            "4 GOOG 2018-11-14 5 GOOG 3 15 10 GOOG 1031.0000 9 2018-11-14 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("sold successfully!"));
    System.setOut(save_out);
  }


  @Test
  public void testsellSharesNameQtyCostIncorrect() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("1 p1 2 p1 4 GOOG 2018-11-13 5 GOOG 7 " +
            "4 GOOG 2018-11-14 5 GOOG 7 10 GOOG 2 1031.0000 2018-11-14 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("sold successfully!"));
    System.setOut(save_out);

  }


  @Test
  public void testInvalidOptionInputGarbageChar() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("r Hello 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Incorrect option,"));
    System.setOut(save_out);
  }


  @Test
  public void testInvalidOptionInputNoCaseNumber() throws Exception {
    PrintStream save_out = System.out;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new StringBufferInputStream("17 Hello 16");
    StockExchangeController sec = new StockExchangeController(input);
    try {
      sec.run(new StockExchangeModel(), new StockExchangeView());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to run");
    }
    assertTrue(out.toString().contains("Incorrect option,"));
    System.setOut(save_out);
  }
}