package stockexchange.view;

/**
 * StockExchangeView is an View of StockExchange MVC architecture. It extends IStockExchange View
 * and implements all its methods which are responsible for interacting with the user.
 */
public class StockExchangeView implements IStockExchangeView {

  @Override
  public void displayOptions() {

    System.out.println("\n_______________________________________________");
    System.out.print("\nSelect one of the below options");
    System.out.print("\n1. Create portfolio" +
            "\n2. Select portfolio" +
            "\n3. Set Commission" +
            "\n4. Display shares" +
            "\n5. Buy displayed share" +
            "\n6. Invest Money" +
            "\n7. Invest Money with weighted distribution" +
            "\n8. Regularly Invest Money in Shares" +
            "\n9. Regularly Invest Money in Shares with weighted distribution" +
            "\n10. Sell all shares - Give Share Name and Date" +
            "\n11. Get current portfolio details" +
            "\n12. Get portfolio details on certain date" +
            "\n13. Get Current Cost Basis" +
            "\n14. Get Cost Basis on date" +
            "\n15. Save Portfolio" +
            "\n16. Save Investment Strategy" +
            "\n17. Load Portfolio" +
            "\n18. Load Investment Strategy" +
            "\n19. Remove portfolio" +
            "\n20. Quit");
    System.out.println("\n_______________________________________________");
    System.out.println("\nEnter your option here:");
  }

  @Override
  public void errorMessage(String errMsg) {
    System.out.println("\n" + errMsg);
  }

  @Override
  public void instructionMessage(String instMsg) {
    System.out.println(instMsg);
  }

  @Override
  public void operationSuccessful(String succMsg) {
    System.out.println("\n" + succMsg);
  }
}

