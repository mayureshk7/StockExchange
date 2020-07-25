package stockexchange.view;

/**
 * Interface for the StockExchangeModelView game view. An implementation will work with the
 * IStockExchangeModelView interface to provide operations of FreeExchangeModelView.
 */
public interface IStockExchangeView {

  /**
   * Displays the available options for users to select from to perform various operations on the
   * stockExchangeModel.
   */
  void displayOptions();

  /**
   * Displays the error message for users.
   *
   * @param errMsg the relevant and appropriate message
   */
  void errorMessage(String errMsg);

  /**
   * Dsiplays instruction message for the users.
   *
   * @param instMsg the relevant and appropriate message
   */
  void instructionMessage(String instMsg);

  /**
   * Displays message on successful completion of a particular operation.
   *
   * @param succMsg the relevant and appropriate message
   */
  void operationSuccessful(String succMsg);
}
