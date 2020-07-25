package stockexchange.model;

/**
 * The class is used to store the details of the current investment plan.
 */
public class InvestmentPlans {
  private double amount;
  private String startDate;
  private String endDate;
  private int interval;
  private double[] percentages;

  /**
   * Constructor of the InvestmentPlans that initialises the investment plan with the following
   * parameters.
   *
   * @param amount      is the total amount of investment.
   * @param startDate   is the start date of the investment plan
   * @param endDate     is the end date of the investment plan
   * @param interval    is the interval at which the investment needs to be done
   * @param percentages the unequal amount of investment for different stocks/shares
   */

  public InvestmentPlans(double amount, String startDate, String endDate,
                         int interval, double[] percentages) {
    this.amount = amount;
    this.startDate = startDate;
    this.endDate = endDate;
    this.interval = interval;
    this.percentages = percentages;
  }

  /**
   * Gets the amount of the investment plan.
   *
   * @return the amount of the investment plan
   */
  public double getAmount() {
    return this.amount;
  }

  /**
   * Gets the start date of the investment plan.
   *
   * @return the start date of the investment plan
   */
  public String getStartDate() {
    return this.startDate;
  }

  /**
   * Gets the end date of the investment plan.
   *
   * @return the end date of the investment plan
   */
  public String getEndDate() {
    return this.endDate;
  }

  /**
   * Gets the set interval for the investment plan.
   *
   * @return the interval of the investment plan
   */
  public int getInterval() {
    return interval;
  }

  /**
   * Gets the percentage weights for different portfolios set for the investment plan.
   *
   * @return the percentage weights
   */
  public double[] getPercentages() {
    return percentages;
  }

  @Override
  public String toString() {
    return Double.toString(amount) + " " + startDate + " " + endDate
            + " " + Integer.toString(interval) + " " + percentages;
  }
}