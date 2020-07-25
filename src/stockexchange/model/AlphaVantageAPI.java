package stockexchange.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * AlphaVantageAPI class allows us to fetch the share data using web based AlphaVantageAPI. It
 * provides a method to fetch share data.
 */
public class AlphaVantageAPI implements WebBasedAPI {

  private String nameOfShare = "";
  private String apiKey = "14ZH23H0O9DU7EL5";
  private URL url = null;

  /**
   * Constructs an Object of AlphaVantageAPI and initializes the object with share name.
   *
   * @param nameOfShare name of the share
   */
  public AlphaVantageAPI(String nameOfShare) {
    this.nameOfShare = nameOfShare;
  }

  @Override
  public String getData() {
    URL url;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + nameOfShare + "&apikey=" + apiKey + "&datatype=csv");

    } catch (MalformedURLException e) {
      throw new IllegalStateException("Incorrect name of the share");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + nameOfShare);
    }
    return output.toString();
  }

}
