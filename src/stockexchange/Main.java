package stockexchange;

import java.util.Scanner;

import stockexchange.controller.StockExchangeController;
import stockexchange.controller.StockExchangeGUIController;
import stockexchange.model.StockExchangeModel;
import stockexchange.view.StockExchangeGraphicalView;
import stockexchange.view.StockExchangeView;

/**
 * Main method of the Stock Exchange. Crates a Console based or GUI based view based on the choice.
 */
public class Main {
  /**
   * Main method of the Main class.
   *
   * @param args arguments
   * @throws Exception exceptions
   */
  public static void main(String[] args) throws Exception {
    System.out.println("Select Interface Type : ");
    System.out.println("1. Console Based Interface");
    System.out.println("2. Graphical User Interface");
    System.out.println("Choice : ");
    Scanner sc = new Scanner(System.in);
    int choice = sc.nextInt();
    if (choice == 1) {
      /**
       * Creates Console based View.
       */
      StockExchangeController se = new StockExchangeController(System.in);
      se.run(new StockExchangeModel(), new StockExchangeView());
    } else if (choice == 2) {
      /**
       * Creates GUI Based View.
       */
      StockExchangeGUIController se = new StockExchangeGUIController();
      se.run(new StockExchangeModel(), new StockExchangeGraphicalView());
    } else {
      System.out.print("Invalid Choice");
    }
  }
}

