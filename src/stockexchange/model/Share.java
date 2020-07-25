package stockexchange.model;

/**
 * This class represents the share data.
 */
public class Share {
  private String shareName;
  private double cost;
  private int numberOfShares;
  private String dateOfBuy;
  private double commission;

  /**
   * Costructor that initialises the share with the name of the company, cost of shares, total
   * number of shares.
   *
   * @param companyName    is the ticker symbol of the company
   * @param cost           is the cost of shares
   * @param numberOfShares is the quantity of shares
   * @param date           is the day on which share is bought
   */
  public Share(String companyName, double cost, int numberOfShares,
               String date, double commission) {
    this.shareName = companyName;
    this.cost = cost;
    this.numberOfShares = numberOfShares;
    this.dateOfBuy = date;
    this.commission = commission;
  }

  /**
   * Fetches the cost of the shares.
   *
   * @return the cost
   */
  public String getName() {
    return this.shareName;
  }

  /**
   * Fetches the cost of the shares.
   *
   * @return the cost
   */
  public double getCost() {
    return this.cost;
  }

  /**
   * Fetches the number of shares.
   *
   * @return the number os shares
   */
  public int getNumberOfShares() {
    return this.numberOfShares;
  }

  /**
   * Fetches the date of shares.
   *
   * @return the date of share
   */
  public String getDate() {
    return this.dateOfBuy;
  }

  /**
   * Fetches the commission of shares.
   *
   * @return the commission of share
   */
  public double getCommission() {
    return this.commission;
  }

  /**
   * Calculates the number of shares left after selling some of it.
   *
   * @param soldShares number of shares that need to be sold
   */
  public void sellSomeShares(int soldShares) {
    numberOfShares = numberOfShares - soldShares;
  }

  @Override
  public String toString() {
    return "Cost Basis : " + this.cost + "\n" +
            "Number of Shares: " + numberOfShares + "\n" +
            "Total Cost Basis : " + this.cost * numberOfShares + "\n" +
            "Purchase Date : " + this.dateOfBuy + "\n\n";
  }

  public String transactionData() {
    return this.shareName + " " + this.cost + " " + numberOfShares + " "
            + this.dateOfBuy + " " + this.commission;
  }
}
