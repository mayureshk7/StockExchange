package stockexchange.model;

/**
 * This interface allows to create multiple web based APIs to fetch share data.
 *
 */
public interface WebBasedAPI {
  /**
   * This method is used to fetch the historical data of the share.
   *
   * @return historical data of share
   */
  String getData();
}
