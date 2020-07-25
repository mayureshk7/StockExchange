package stockexchange.view;

import java.awt.event.ActionListener;

/**
 * This interface is the extended interface of the IStockExchangeView. It offers all methods and
 * operations that can be performed additional to the existing functionalities provided by
 * IStockExchangeView.
 */
public interface IStockExchangeGUIView extends IStockExchangeView {

  /**
   * Sets the current portfolio details.
   *
   * @param msg is the message that is received from the model and displayed/set on view
   */
  void setPortfolioOverview(String msg);

  /**
   * Gets input from the input Text Field.
   *
   * @return a String of data, from the input Text Field
   */
  String getInputData();

  /**
   * Sets the name of the portfolio.
   *
   * @param name is the name of the portfolio that you want to set
   */
  void setPortfolioName(String name);

  /**
   * Sets the action Listener for all the buttons required/used in the Graphical User Interface.
   *
   * @param listener is the listener set for required/used buttons of the GUI
   */
  void setListener(ActionListener listener);
}