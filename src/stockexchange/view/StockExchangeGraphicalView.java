package stockexchange.view;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;


/**
 * StockExchangeGraphicalView is the class for Graphical User Interface. It implements the
 * IStockExchangeGUIView Interface and all its methods.
 */
public class StockExchangeGraphicalView extends JFrame implements IStockExchangeGUIView {

  /**
   * The various variables of Frames, Panels, TextArea fields, Buttons, TextFields, CardLayout used
   * for the Graphical User Interface.
   */
  JFrame frame;
  JPanel controls;
  JPanel buttons;
  JPanel options;
  JPanel createPanel;
  JPanel displays;
  JTextArea portfolioArea;
  JTextArea outputArea;
  JTextArea errorArea;
  CardLayout cardLayout;
  JButton title1;
  JTextField createInput;
  JButton createButton;
  JTextField selectInput;
  JButton selectButton;
  JTextField createInputSetCommission;
  JButton createButtonCommission;
  JTextField createInputDisplayShare;
  JButton createButtonDisplayShare;
  JTextField createInputBuyShare;
  JButton createButtonBuyShare;
  JTextField createInputInvestMoney;
  JButton createButtonInvestMoney;
  JTextField createInputCostBasisAvg;
  JButton createButtonCostBasisAvg;
  JButton createButtonCostBasisAvgPer;
  JButton createButtonCostBasisAvgPerEnd;
  JTextField createInputSellShare;
  JButton createButtonSellShare;
  JButton createButtonSellShareMulti;
  JTextField createInputGetShareDetail;
  JButton createButtonGetShareDetail;
  JTextField createInputGetCostBasis;
  JButton createButtonGetCostBasis;
  JTextField createInputSavePortfolio;
  JButton createButtonSavePortfolio;
  JTextField createInputSaveStrategy;
  JButton createButtonSaveStrategy;
  JTextField createInputLoadPortfolio;
  JButton createButtonLoadPortfolio;
  JTextField createInputLoadStrategy;
  JButton createButtonLoadStrategy;
  JTextField createInputRemovePortfolio;
  JButton createButtonRemovePortfolio;
  private String currentSelection = "create";


  /**
   * Constructor of the StockExchangeGraphicalView that initialises the various components, of the
   * Graphical User Interface.
   */
  public StockExchangeGraphicalView() {
    frame = new JFrame("Stock Exchange");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 1000);
    frame.setResizable(false);
    controls = new JPanel();
    controls.setSize(600, 1000);
    controls.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
    controls.setAlignmentY(Component.LEFT_ALIGNMENT);
    controls.setBackground(Color.WHITE);
    controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
    controls.setVisible(true);
    /**
     * all controls here
     */
    buttons = new JPanel(new GridLayout(6, 3, 20, 25));
    buttons.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
    buttons.setVisible(true);
    buttons.setOpaque(true);

    JButton dummy1 = new JButton("");
    dummy1.setFont(new Font("Serif", Font.BOLD, 20));
    dummy1.setForeground(Color.WHITE);
    dummy1.setEnabled(false);
    buttons.add(dummy1);

    //Creates the buttons for each different type of operation/function using this method.
    createButtons();

    cardLayout = new CardLayout();
    options = new JPanel(cardLayout);
    options.setVisible(true);
    options.setOpaque(true);
    options.setBackground(Color.lightGray);

    //The various Panels for each different type of operation are created using these methods.
    createPortfolioPanel();
    createSelectPanel();
    createCommissionPanel();
    createDisplaySharePanel();
    createBuySharePanel();
    createInvestMoneyPanel();
    createCostBasisAvgPanel();
    createSellSharePanel();
    createShareDetailPanel();
    createGetCostBasisPanel();
    createSavePortfolioPanel();
    createSaveStrategyPanel();
    createLoadPortfolioPanel();
    createLoadStrategyPanel();
    createRemovePortfolioPanel();

    controls.add(options);

    displays = new JPanel();
    displays.setBackground(Color.lightGray);

    //The various display output textArea Fields are created using these methods.
    displayPotfolioDetails();
    displayOutputOfOperations();
    displayErrorInstructions();

    displays.setVisible(true);

    JSplitPane splitPane = new JSplitPane();
    splitPane.setSize(600, 1000);
    splitPane.setDividerSize(2);
    splitPane.setDividerLocation(600);
    splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setLeftComponent(controls);
    splitPane.setRightComponent(displays);
    frame.add(splitPane);
    frame.setVisible(true);
  }

  /**
   * Creates the set of all buttons used for the various operations available for the user to create
   * and Edit the portfolio.
   */
  public void createButtons() {

    title1 = new JButton("PORTFOLIO");
    title1.setFont(new Font("Serif", Font.BOLD, 20));
    title1.setForeground(Color.DARK_GRAY);
    title1.setBackground(Color.decode("#EEEEEE"));
    title1.setEnabled(true);
    buttons.add(title1);

    JButton dummy2 = new JButton("");
    dummy2.setFont(new Font("Serif", Font.BOLD, 20));
    dummy2.setForeground(Color.WHITE);
    dummy2.setEnabled(false);
    buttons.add(dummy2);

    JButton createPort = new JButton("CREATE PORTFOLIO");
    createPort.setFont(new Font("Serif", Font.BOLD, 14));
    createPort.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "create";
        cardLayout.show(options, "create");
      }
    });
    buttons.add(createPort);

    JButton selectPort = new JButton("SELECT PORTFOLIO");
    selectPort.setFont(new Font("Serif", Font.BOLD, 14));
    selectPort.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "select";
        cardLayout.show(options, "select");
      }
    });
    buttons.add(selectPort);

    JButton setCommission = new JButton("SET COMMISSION");
    setCommission.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "set";
        cardLayout.show(options, "set");
      }
    });
    setCommission.setFont(new Font("Serif", Font.BOLD, 14));

    buttons.add(setCommission);

    JButton displayShare = new JButton("DISPLAY SHARE");
    displayShare.setFont(new Font("Serif", Font.BOLD, 14));
    displayShare.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "display";
        cardLayout.show(options, "display");
      }
    });
    buttons.add(displayShare);

    JButton buyShare = new JButton("BUY SHARE");
    buyShare.setFont(new Font("Serif", Font.BOLD, 14));
    buyShare.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "buy";
        cardLayout.show(options, "buy");
      }
    });
    buttons.add(buyShare);

    JButton investMoney = new JButton("INVEST MONEY");
    investMoney.setFont(new Font("Serif", Font.BOLD, 14));
    investMoney.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "invest";
        cardLayout.show(options, "invest");
      }
    });
    buttons.add(investMoney);

    JButton regularInvest = new JButton("REGULAR INVEST");
    regularInvest.setFont(new Font("Serif", Font.BOLD, 14));
    regularInvest.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "costBasisAvg";
        cardLayout.show(options, "costBasisAvg");
      }
    });
    buttons.add(regularInvest);

    JButton sellShare = new JButton("SELL SHARE");
    sellShare.setFont(new Font("Serif", Font.BOLD, 14));
    sellShare.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "sell";
        cardLayout.show(options, "sell");
      }
    });
    buttons.add(sellShare);

    JButton getDetails = new JButton("GET SHARE DETAIL");
    getDetails.setFont(new Font("Serif", Font.BOLD, 14));
    getDetails.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "getShareDetail";
        cardLayout.show(options, "getShareDetail");
      }
    });
    buttons.add(getDetails);

    JButton getCostBase = new JButton("GET COST BASIS");
    getCostBase.setFont(new Font("Serif", Font.BOLD, 14));
    getCostBase.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "getCostBasis";
        cardLayout.show(options, "getCostBasis");
      }
    });
    buttons.add(getCostBase);

    JButton saveProfile = new JButton("SAVE PORTFOLIO");
    saveProfile.setFont(new Font("Serif", Font.BOLD, 14));
    saveProfile.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "savePortfolio";
        cardLayout.show(options, "save");
      }
    });
    buttons.add(saveProfile);

    JButton saveStrategy = new JButton("SAVE STRATEGY");
    saveStrategy.setFont(new Font("Serif", Font.BOLD, 14));
    saveStrategy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "saveStrategy";
        cardLayout.show(options, "saveStrategy");
      }
    });
    buttons.add(saveStrategy);

    JButton openPortfolio = new JButton("LOAD PORTFOLIO");
    openPortfolio.setFont(new Font("Serif", Font.BOLD, 14));
    openPortfolio.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "loadPortfolio";
        cardLayout.show(options, "loadPortfolio");
      }
    });
    buttons.add(openPortfolio);

    JButton openInvest = new JButton("LOAD STRATEGY");
    openInvest.setFont(new Font("Serif", Font.BOLD, 14));
    openInvest.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "loadStrategy";
        cardLayout.show(options, "loadStrategy");
      }
    });
    buttons.add(openInvest);

    JButton help = new JButton("REMOVE PORTFOLIO");
    help.setFont(new Font("Serif", Font.BOLD, 14));
    buttons.add(help);
    help.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentSelection = "remove";
        cardLayout.show(options, "removePortfolio");
      }
    });
    controls.add(buttons);
  }

  /**
   * Creates a portfolio Panel, available for the user in the GUI to enter the required details to
   * create a portfolio and perform action on it.
   */
  public void createPortfolioPanel() {
    createPanel = new JPanel();
    createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.Y_AXIS));
    createPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    createPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    createPanel.setVisible(true);

    JLabel createTitle = new JLabel("CREATE PORTFOLIO");
    createTitle.setFont(new Font("Serif", Font.BOLD, 25));
    createTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    createPanel.add(createTitle);

    JLabel createText1 = new JLabel("Enter the Name of the portfolio that you " +
            "would like to create:");
    createText1.setFont(new Font("Serif", Font.BOLD, 20));
    createText1.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    createPanel.add(createText1);

    JLabel createText2 = new JLabel("FORMAT: portfolioName");
    createText2.setFont(new Font("Serif", Font.BOLD, 20));
    createText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    createPanel.add(createText2);

    createInput = new JTextField();
    createInput.setFont(new Font("Serif", Font.BOLD, 20));
    createInput.setMaximumSize(new Dimension(500, 50));
    createInput.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    createPanel.add(createInput);
    createPanel.add(new JLabel("  "));

    createButton = new JButton("CREATE");
    createButton.setFont(new Font("Serif", Font.BOLD, 14));
    createButton.setActionCommand("createButton");
    createPanel.add(createButton);
    options.add(createPanel, "create");
  }

  /**
   * Creates a Select portfolio Panel, available for the user in the GUI to enter the required
   * details to select the portfolio and perform action on it.
   */
  public void createSelectPanel() {
    JPanel selectPanel = new JPanel();
    selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));
    selectPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    selectPanel.setAlignmentY(Component.LEFT_ALIGNMENT);

    JLabel selectTitle = new JLabel("SELECT PORTFOLIO");
    selectTitle.setFont(new Font("Serif", Font.BOLD, 25));
    selectTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    selectPanel.add(selectTitle);


    JLabel selectText1 = new JLabel("Select the Name of the portfolio " +
            "that you would like to select:");
    selectText1.setFont(new Font("Serif", Font.BOLD, 20));
    selectText1.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    selectPanel.add(selectText1);

    JLabel selectText2 = new JLabel("FORMAT: portfolioName");
    selectText2.setFont(new Font("Serif", Font.BOLD, 20));
    selectText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    selectPanel.add(selectText2);

    selectInput = new JTextField();
    selectInput.setFont(new Font("Serif", Font.BOLD, 20));
    selectInput.setMaximumSize(new Dimension(500, 50));
    selectInput.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    selectPanel.add(selectInput);
    selectPanel.add(new JLabel("  "));

    selectButton = new JButton("SELECT");
    selectButton.setFont(new Font("Serif", Font.BOLD, 14));
    selectButton.setActionCommand("selectButton");
    selectPanel.add(selectButton);

    options.add(selectPanel, "select");

  }


  /**
   * Creates a Commission portfolio Panel, available for the user in the GUI to enter the required
   * details to set the commission and perform relevant action on it.
   */
  public void createCommissionPanel() {
    JPanel setCommissionPanel = new JPanel();
    setCommissionPanel.setLayout(new BoxLayout(setCommissionPanel, BoxLayout.Y_AXIS));
    setCommissionPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    setCommissionPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    setCommissionPanel.setVisible(true);

    JLabel selectCommissionText1 = new JLabel("SET COMMISSION");
    selectCommissionText1.setFont(new Font("Serif", Font.BOLD, 25));
    selectCommissionText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    setCommissionPanel.add(selectCommissionText1);

    JLabel selectCommissionText2 = new JLabel("Set the commission for the transaction");
    selectCommissionText2.setFont(new Font("Serif", Font.BOLD, 20));
    selectCommissionText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    setCommissionPanel.add(selectCommissionText2);

    JLabel selectCommissionText3 = new JLabel("FORMAT: commission");
    selectCommissionText3.setFont(new Font("Serif", Font.BOLD, 20));
    selectCommissionText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    setCommissionPanel.add(selectCommissionText3);

    createInputSetCommission = new JTextField();
    createInputSetCommission.setFont(new Font("Serif", Font.BOLD, 20));
    createInputSetCommission.setMaximumSize(new Dimension(500, 50));
    createInputSetCommission.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    setCommissionPanel.add(createInputSetCommission);
    setCommissionPanel.add(new JLabel("  "));

    createButtonCommission = new JButton("SET");
    createButtonCommission.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonCommission.setActionCommand("setButton");
    setCommissionPanel.add(createButtonCommission);
    options.add(setCommissionPanel, "set");
  }

  /**
   * Creates a Display Share Panel, available for the user in the GUI to enter the required details
   * to display the share and perform relevant action on it.
   */
  public void createDisplaySharePanel() {
    JPanel displaySharePanel = new JPanel();
    displaySharePanel.setLayout(new BoxLayout(displaySharePanel, BoxLayout.Y_AXIS));
    displaySharePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    displaySharePanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    displaySharePanel.setVisible(true);

    JLabel displayShareText1 = new JLabel("DISPLAY SHARE");
    displayShareText1.setFont(new Font("Serif", Font.BOLD, 25));
    displayShareText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    displaySharePanel.add(displayShareText1);

    JLabel displayShareText2 = new JLabel("Enter the Shares you want to check");
    displayShareText2.setFont(new Font("Serif", Font.BOLD, 20));
    displayShareText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    displaySharePanel.add(displayShareText2);

    JLabel displayShareText3 = new JLabel("FORMAT: ShareName Date(yyyy-MM-dd)");
    displayShareText3.setFont(new Font("Serif", Font.BOLD, 20));
    displayShareText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    displaySharePanel.add(displayShareText3);

    createInputDisplayShare = new JTextField();
    createInputDisplayShare.setFont(new Font("Serif", Font.BOLD, 20));
    createInputDisplayShare.setMaximumSize(new Dimension(500, 50));
    createInputDisplayShare.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    displaySharePanel.add(createInputDisplayShare);
    displaySharePanel.add(new JLabel("  "));

    createButtonDisplayShare = new JButton("DISPLAY");
    createButtonDisplayShare.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonDisplayShare.setActionCommand("displayButton");
    displaySharePanel.add(createButtonDisplayShare);

    options.add(displaySharePanel, "display");
  }


  /**
   * Creates a Buy Share Panel, available for the user in the GUI to enter the required details to
   * buy the share and perform relevant action on it.
   */
  public void createBuySharePanel() {
    JPanel buySharePanel = new JPanel();
    buySharePanel.setLayout(new BoxLayout(buySharePanel, BoxLayout.Y_AXIS));
    buySharePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    buySharePanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    buySharePanel.setVisible(true);

    JLabel buyShareText1 = new JLabel("BUY SHARE");
    buyShareText1.setFont(new Font("Serif", Font.BOLD, 25));
    buyShareText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    buySharePanel.add(buyShareText1);

    JLabel buyShareText2 = new JLabel("Enter the Name of the Share and Quantity " +
            "that you");
    buyShareText2.setFont(new Font("Serif", Font.BOLD, 20));
    buyShareText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    buySharePanel.add(buyShareText2);

    JLabel buyShareText2a = new JLabel("would like to buy:");
    buyShareText2a.setFont(new Font("Serif", Font.BOLD, 20));
    buyShareText2a.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    buySharePanel.add(buyShareText2a);

    JLabel buyShareText3 = new JLabel("FORMAT: TickerSymbol QtyOfShares");
    buyShareText3.setFont(new Font("Serif", Font.BOLD, 20));
    buyShareText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    buySharePanel.add(buyShareText3);

    createInputBuyShare = new JTextField();
    createInputBuyShare.setFont(new Font("Serif", Font.BOLD, 20));
    createInputBuyShare.setMaximumSize(new Dimension(500, 50));
    createInputBuyShare.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    buySharePanel.add(createInputBuyShare);
    buySharePanel.add(new JLabel("  "));

    createButtonBuyShare = new JButton("BUY");
    createButtonBuyShare.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonBuyShare.setActionCommand("buyButton");
    buySharePanel.add(createButtonBuyShare);
    options.add(buySharePanel, "buy");
  }

  /**
   * Creates a Invest Money Panel, available for the user in the GUI to enter the required details
   * to invest money and perform relevant action on it.
   */
  public void createInvestMoneyPanel() {
    JPanel investMoneyPanel = new JPanel();
    investMoneyPanel.setLayout(new

            BoxLayout(investMoneyPanel, BoxLayout.Y_AXIS));
    investMoneyPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    investMoneyPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    investMoneyPanel.setVisible(true);

    JLabel investMoneyText1 = new JLabel("INVEST MONEY");
    investMoneyText1.setFont(new

            Font("Serif", Font.BOLD, 25));
    investMoneyText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    investMoneyPanel.add(investMoneyText1);

    JLabel investMoneyText2 = new JLabel("Enter the Amount to be Invested and date:");
    investMoneyText2.setFont(new

            Font("Serif", Font.BOLD, 20));
    investMoneyText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    investMoneyPanel.add(investMoneyText2);

    JLabel investMoneyText3 = new JLabel("FORMAT: Amount Date(yyyy-MM-dd)");
    investMoneyText3.setFont(new

            Font("Serif", Font.BOLD, 20));
    investMoneyText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    investMoneyPanel.add(investMoneyText3);

    JLabel investMoneyText4 = new JLabel("IF You Wish to divide Money Unequally, Then ...");
    investMoneyText4.setFont(new

            Font("Serif", Font.BOLD, 20));
    investMoneyText4.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    investMoneyPanel.add(investMoneyText4);

    JLabel investMoneyText5 = new JLabel("Enter the Amount to be Invested, date " +
            "and Percentage weights:");
    investMoneyText5.setFont(new

            Font("Serif", Font.BOLD, 20));
    investMoneyText5.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    investMoneyPanel.add(investMoneyText5);

    JLabel investMoneyText6 = new JLabel("FORMAT: Amount Date(yyyy-MM-dd)");
    investMoneyText6.setFont(new

            Font("Serif", Font.BOLD, 20));
    investMoneyText6.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    investMoneyPanel.add(investMoneyText6);

    createInputInvestMoney = new JTextField();
    createInputInvestMoney.setFont(new Font("Serif", Font.BOLD, 20));
    createInputInvestMoney.setMaximumSize(new Dimension(500, 50));
    createInputInvestMoney.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    investMoneyPanel.add(createInputInvestMoney);
    investMoneyPanel.add(new JLabel("  "));

    createButtonInvestMoney = new JButton("INVEST");
    createButtonInvestMoney.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonInvestMoney.setActionCommand("investButton");
    investMoneyPanel.add(createButtonInvestMoney);
    options.add(investMoneyPanel, "invest");
  }

  /**
   * Creates a Average cost basis Panel, available for the user in the GUI to enter the required
   * details to regularly invest and perform relevant action on it.
   */
  public void createCostBasisAvgPanel() {
    JPanel costBasisAvgPanel = new JPanel();
    costBasisAvgPanel.setLayout(new BoxLayout(costBasisAvgPanel, BoxLayout.Y_AXIS));
    costBasisAvgPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    costBasisAvgPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    costBasisAvgPanel.setVisible(true);

    JLabel costBasisAvgText1 = new JLabel("REGULAR INVESTMENT STRATEGY");
    costBasisAvgText1.setFont(new Font("Serif", Font.BOLD, 25));
    costBasisAvgText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    costBasisAvgPanel.add(costBasisAvgText1);

    JLabel costBasisAvgText2 = new JLabel("Enter the amount to be invested, " +
            "Start date, End date");
    costBasisAvgText2.setFont(new Font("Serif", Font.BOLD, 20));
    costBasisAvgText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    costBasisAvgPanel.add(costBasisAvgText2);

    JLabel costBasisAvgText2a = new JLabel("and Interval Size in Days :");
    costBasisAvgText2a.setFont(new Font("Serif", Font.BOLD, 20));
    costBasisAvgText2a.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    costBasisAvgPanel.add(costBasisAvgText2a);

    JLabel costBasisAvgText3 = new JLabel("FORMAT: Amount Start_Date (" +
            "Optional - End_Date) Interval");
    costBasisAvgText3.setFont(new Font("Serif", Font.BOLD, 20));
    costBasisAvgText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    costBasisAvgPanel.add(costBasisAvgText3);

    JLabel costBasisAvgText3a = new JLabel("If You wish to divide Amount Unequally, Then..");
    costBasisAvgText3a.setFont(new Font("Serif", Font.BOLD, 20));
    costBasisAvgText3a.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    costBasisAvgPanel.add(costBasisAvgText3a);

    JLabel costBasisAvgText3b = new JLabel("Enter the Amount to be Invested, Start date," +
            " Interval (In days) and Percentage Weights :");
    costBasisAvgText3b.setFont(new Font("Serif", Font.BOLD, 20));
    costBasisAvgText3b.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    costBasisAvgPanel.add(costBasisAvgText3b);

    JLabel costBasisAvgText3c = new JLabel("FORMAT: Amount Start_Date (" +
            "Optional - End_Date) Inverval PercentageWeights");
    costBasisAvgText3c.setFont(new Font("Serif", Font.BOLD, 20));
    costBasisAvgText3c.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    costBasisAvgPanel.add(costBasisAvgText3c);

    createInputCostBasisAvg = new JTextField();
    createInputCostBasisAvg.setFont(new Font("Serif", Font.BOLD, 20));
    createInputCostBasisAvg.setMaximumSize(new Dimension(500, 50));
    createInputCostBasisAvg.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    costBasisAvgPanel.add(createInputCostBasisAvg);
    costBasisAvgPanel.add(new JLabel("  "));

    createButtonCostBasisAvg = new JButton("AVERAGE COST BASIS");
    createButtonCostBasisAvg.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonCostBasisAvg.setActionCommand("costBaseAvgButton");
    costBasisAvgPanel.add(createButtonCostBasisAvg);
    costBasisAvgPanel.add(new JLabel("  "));

    createButtonCostBasisAvgPer = new JButton("AVERAGE COST BASIS WITH PERCENTAGE");
    createButtonCostBasisAvgPer.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonCostBasisAvgPer.setActionCommand("costBaseAvgButtonPer");
    costBasisAvgPanel.add(createButtonCostBasisAvgPer);
    costBasisAvgPanel.add(new JLabel("  "));

    createButtonCostBasisAvgPerEnd = new JButton("AVERAGE COST BASIS WITH PERCENTAGE AND END");
    createButtonCostBasisAvgPerEnd.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonCostBasisAvgPerEnd.setActionCommand("costBaseAvgButtonPerEnd");
    costBasisAvgPanel.add(createButtonCostBasisAvgPerEnd);
    costBasisAvgPanel.add(new JLabel("  "));


    options.add(costBasisAvgPanel, "costBasisAvg");
  }

  /**
   * Creates a sell share Panel, available for the user in the GUI to enter the required details to
   * sell the shares and perform relevant action on it.
   */
  public void createSellSharePanel() {
    JPanel sellSharePanel = new JPanel();
    sellSharePanel.setLayout(new BoxLayout(sellSharePanel, BoxLayout.Y_AXIS));
    sellSharePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    sellSharePanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    sellSharePanel.setVisible(true);

    JLabel sellShareText1 = new JLabel("SELL SHARE");
    sellShareText1.setFont(new Font("Serif", Font.BOLD, 25));
    sellShareText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    sellSharePanel.add(sellShareText1);

    JLabel sellShareText2 = new JLabel("Enter the Share name, date of the");
    sellShareText2.setFont(new Font("Serif", Font.BOLD, 20));
    sellShareText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    sellSharePanel.add(sellShareText2);

    JLabel sellShareText2a = new JLabel("(Optional - Quantity) Share you want to sell:");
    sellShareText2a.setFont(new Font("Serif", Font.BOLD, 20));
    sellShareText2a.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    sellSharePanel.add(sellShareText2a);

    JLabel sellShareText3 = new JLabel("FORMAT: ShareName (Optional - Quantity) " +
            "Date(yyyy-MM-dd)");
    sellShareText3.setFont(new Font("Serif", Font.BOLD, 20));
    sellShareText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    sellSharePanel.add(sellShareText3);

    JLabel sellShareText4 = new JLabel("IF there are multiple shares " +
            "of Same Company of different dates");
    sellShareText4.setFont(new Font("Serif", Font.BOLD, 20));
    sellShareText4.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    sellSharePanel.add(sellShareText4);

    JLabel sellShareText4b = new JLabel("Then..");
    sellShareText4b.setFont(new Font("Serif", Font.BOLD, 20));
    sellShareText4b.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    sellSharePanel.add(sellShareText4b);

    JLabel sellShareText4a = new JLabel("Enter the Name(Ticker Symbol) of the Shares, " +
            " Quantity ");
    sellShareText4a.setFont(new Font("Serif", Font.BOLD, 20));
    sellShareText4a.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    sellSharePanel.add(sellShareText4a);

    JLabel sellShareText4c = new JLabel("the Cost of shares and " +
            " date on which you want to Sell :");
    sellShareText4c.setFont(new Font("Serif", Font.BOLD, 20));
    sellShareText4c.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    sellSharePanel.add(sellShareText4c);

    JLabel sellShareText5 = new JLabel("FORMAT: TickerSymbol (Optional - QtyOfShares)");
    sellShareText5.setFont(new Font("Serif", Font.BOLD, 20));
    sellShareText5.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    sellSharePanel.add(sellShareText5);

    JLabel sellShareText5a = new JLabel("CostOfShares Date");
    sellShareText5a.setFont(new Font("Serif", Font.BOLD, 20));
    sellShareText5a.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    sellSharePanel.add(sellShareText5a);

    createInputSellShare = new JTextField();
    createInputSellShare.setFont(new Font("Serif", Font.BOLD, 20));
    createInputSellShare.setMaximumSize(new Dimension(500, 50));
    createInputSellShare.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    sellSharePanel.add(createInputSellShare);
    sellSharePanel.add(new JLabel("  "));

    createButtonSellShare = new JButton("SELL SHARE");
    createButtonSellShare.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonSellShare.setActionCommand("sell");
    sellSharePanel.add(createButtonSellShare);
    sellSharePanel.add(new JLabel("  "));

    createButtonSellShareMulti = new JButton("SELL SHARE FROM MULTIPLE");
    createButtonSellShareMulti.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonSellShareMulti.setActionCommand("sellMulti");
    sellSharePanel.add(createButtonSellShareMulti);

    options.add(sellSharePanel, "sell");
  }

  /**
   * Creates share detail Panel, available for the user in the GUI to enter the required details to
   * fetch the shares details and further perform relevant action on it.
   */
  public void createShareDetailPanel() {
    JPanel getShareDetailPanel = new JPanel();
    getShareDetailPanel.setLayout(new BoxLayout(getShareDetailPanel, BoxLayout.Y_AXIS));
    getShareDetailPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    getShareDetailPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    getShareDetailPanel.setVisible(true);

    JLabel getShareDetailText1 = new JLabel("GET SHARE DETAIL");
    getShareDetailText1.setFont(new Font("Serif", Font.BOLD, 25));
    getShareDetailText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    getShareDetailPanel.add(getShareDetailText1);

    JLabel getShareDetailText2 = new JLabel("Enter the date till which share " +
            "details are required :");
    getShareDetailText2.setFont(new Font("Serif", Font.BOLD, 20));
    getShareDetailText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    getShareDetailPanel.add(getShareDetailText2);

    JLabel getShareDetailText3 = new JLabel("FORMAT: date(yyyy-MM-dd)");
    getShareDetailText3.setFont(new Font("Serif", Font.BOLD, 20));
    getShareDetailText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    getShareDetailPanel.add(getShareDetailText3);

    createInputGetShareDetail = new JTextField();
    createInputGetShareDetail.setFont(new Font("Serif", Font.BOLD, 20));
    createInputGetShareDetail.setMaximumSize(new Dimension(500, 50));
    createInputGetShareDetail.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    getShareDetailPanel.add(createInputGetShareDetail);
    getShareDetailPanel.add(new JLabel("  "));

    createButtonGetShareDetail = new JButton("GET SHARE DETAIL");
    createButtonGetShareDetail.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonGetShareDetail.setActionCommand("detailsButton");
    getShareDetailPanel.add(createButtonGetShareDetail);
    options.add(getShareDetailPanel, "getShareDetail");
  }

  /**
   * Creates get cost basis Panel, available for the user in the GUI to enter the required details
   * to fetch the cost basis details and further perform relevant action on it.
   */
  public void createGetCostBasisPanel() {
    JPanel getCostBasisPanel = new JPanel();
    getCostBasisPanel.setLayout(new BoxLayout(getCostBasisPanel, BoxLayout.Y_AXIS));
    getCostBasisPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    getCostBasisPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    getCostBasisPanel.setVisible(true);

    JLabel getCostBasisText1 = new JLabel("GET COST BASIS");
    getCostBasisText1.setFont(new Font("Serif", Font.BOLD, 25));
    getCostBasisText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    getCostBasisPanel.add(getCostBasisText1);

    JLabel getCostBasisText2 = new JLabel("Enter the date for which you would like to " +
            "know the");
    getCostBasisText2.setFont(new Font("Serif", Font.BOLD, 20));
    getCostBasisText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    getCostBasisPanel.add(getCostBasisText2);

    JLabel getCostBasisText2a = new JLabel("Cost Basis:");
    getCostBasisText2a.setFont(new Font("Serif", Font.BOLD, 20));
    getCostBasisText2a.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    getCostBasisPanel.add(getCostBasisText2a);

    JLabel getCostBasisText3 = new JLabel("FORMAT: Date(yyyy-MM-dd)");
    getCostBasisText3.setFont(new Font("Serif", Font.BOLD, 20));
    getCostBasisText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    getCostBasisPanel.add(getCostBasisText3);

    createInputGetCostBasis = new JTextField();
    createInputGetCostBasis.setFont(new Font("Serif", Font.BOLD, 20));
    createInputGetCostBasis.setMaximumSize(new Dimension(500, 50));
    createInputGetCostBasis.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    getCostBasisPanel.add(createInputGetCostBasis);
    getCostBasisPanel.add(new JLabel("  "));

    createButtonGetCostBasis = new JButton("GET COST BASIS");
    createButtonGetCostBasis.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonGetCostBasis.setActionCommand("getCostButton");
    getCostBasisPanel.add(createButtonGetCostBasis);

    options.add(getCostBasisPanel, "getCostBasis");
  }

  /**
   * Creates a save portfolio Panel, available for the user in the GUI to enter the required details
   * to save a portfolio and further perform relevant action on it.
   */
  public void createSavePortfolioPanel() {
    JPanel savePortfolioPanel = new JPanel();
    savePortfolioPanel.setLayout(new BoxLayout(savePortfolioPanel, BoxLayout.Y_AXIS));
    savePortfolioPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    savePortfolioPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    savePortfolioPanel.setVisible(true);

    JLabel savePortfolioText1 = new JLabel("SAVE PORTFOLIO");
    savePortfolioText1.setFont(new Font("Serif", Font.BOLD, 25));
    savePortfolioText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    savePortfolioPanel.add(savePortfolioText1);

    JLabel savePortfolioText2 = new JLabel("Enter the file name in which portfolio " +
            "is to be saved");
    savePortfolioText2.setFont(new Font("Serif", Font.BOLD, 20));
    savePortfolioText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    savePortfolioPanel.add(savePortfolioText2);

    JLabel savePortfolioText2a = new JLabel("FORMAT: fileName");
    savePortfolioText2a.setFont(new Font("Serif", Font.BOLD, 20));
    savePortfolioText2a.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    savePortfolioPanel.add(savePortfolioText2a);

    createInputSavePortfolio = new JTextField();
    createInputSavePortfolio.setFont(new Font("Serif", Font.BOLD, 20));
    createInputSavePortfolio.setMaximumSize(new Dimension(500, 50));
    createInputSavePortfolio.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    savePortfolioPanel.add(createInputSavePortfolio);
    savePortfolioPanel.add(new JLabel("  "));

    createButtonSavePortfolio = new JButton("SAVE PORTFOLIO");
    createButtonSavePortfolio.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonSavePortfolio.setActionCommand("savePortfolioButton");
    savePortfolioPanel.add(createButtonSavePortfolio);

    options.add(savePortfolioPanel, "save");

  }

  /**
   * Creates a save strategy Panel, available for the user in the GUI to enter the required details
   * to save strategy applied to a portfolio and further perform relevant action on it.
   */
  public void createSaveStrategyPanel() {
    JPanel saveStrategyPanel = new JPanel();
    saveStrategyPanel.setLayout(new BoxLayout(saveStrategyPanel, BoxLayout.Y_AXIS));
    saveStrategyPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    saveStrategyPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    saveStrategyPanel.setVisible(true);

    JLabel saveStrategyText1 = new JLabel("SAVE STRATEGY");
    saveStrategyText1.setFont(new Font("Serif", Font.BOLD, 25));
    saveStrategyText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    saveStrategyPanel.add(saveStrategyText1);

    JLabel saveStrategyText2 = new JLabel("Enter the file name in which " +
            "currently used strategy is to be saved");
    saveStrategyText2.setFont(new Font("Serif", Font.BOLD, 20));
    saveStrategyText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    saveStrategyPanel.add(saveStrategyText2);

    JLabel saveStrategyText3 = new JLabel("FORMAT: fileName");
    saveStrategyText3.setFont(new Font("Serif", Font.BOLD, 20));
    saveStrategyText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    saveStrategyPanel.add(saveStrategyText3);

    createInputSaveStrategy = new JTextField();
    createInputSaveStrategy.setFont(new Font("Serif", Font.BOLD, 20));
    createInputSaveStrategy.setMaximumSize(new Dimension(500, 50));
    createInputSaveStrategy.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    saveStrategyPanel.add(createInputSaveStrategy);
    saveStrategyPanel.add(new JLabel("  "));

    createButtonSaveStrategy = new JButton("SAVE STRATEGY");
    createButtonSaveStrategy.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonSaveStrategy.setActionCommand("saveStrategyButton");
    saveStrategyPanel.add(createButtonSaveStrategy);
    options.add(saveStrategyPanel, "saveStrategy");
  }

  /**
   * Creates a Load portfolio Panel, available for the user in the GUI to enter the required details
   * to to load the portfolio and further perform relevant action on it.
   */
  public void createLoadPortfolioPanel() {
    JPanel loadPortfolioPanel = new JPanel();
    loadPortfolioPanel.setLayout(new BoxLayout(loadPortfolioPanel, BoxLayout.Y_AXIS));
    loadPortfolioPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    loadPortfolioPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    loadPortfolioPanel.setVisible(true);

    JLabel loadPortfolioText1 = new JLabel("LOAD PORTFOLIO");
    loadPortfolioText1.setFont(new Font("Serif", Font.BOLD, 25));
    loadPortfolioText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    loadPortfolioPanel.add(loadPortfolioText1);

    JLabel loadPortfolioText2 = new JLabel("Enter the file name from " +
            "which portfolio is to be retrieved :");
    loadPortfolioText2.setFont(new Font("Serif", Font.BOLD, 20));
    loadPortfolioText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    loadPortfolioPanel.add(loadPortfolioText2);

    JLabel loadPortfolioText3 = new JLabel("FORMAT: fileName");
    loadPortfolioText3.setFont(new Font("Serif", Font.BOLD, 20));
    loadPortfolioText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    loadPortfolioPanel.add(loadPortfolioText3);

    createInputLoadPortfolio = new JTextField();
    createInputLoadPortfolio.setFont(new Font("Serif", Font.BOLD, 20));
    createInputLoadPortfolio.setMaximumSize(new Dimension(500, 50));
    createInputLoadPortfolio.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    loadPortfolioPanel.add(createInputLoadPortfolio);
    loadPortfolioPanel.add(new JLabel("  "));

    createButtonLoadPortfolio = new JButton("LOAD PORTFOLIO");
    createButtonLoadPortfolio.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonLoadPortfolio.setActionCommand("loadPortfolioButton");
    loadPortfolioPanel.add(createButtonLoadPortfolio);

    options.add(loadPortfolioPanel, "loadPortfolio");
  }


  /**
   * Creates a Load Strategy Panel, available for the user in the GUI to enter the required details
   * to like the filename and further perform relevant action on it.
   */
  public void createLoadStrategyPanel() {
    JPanel loadStrategyPanel = new JPanel();
    loadStrategyPanel.setLayout(new BoxLayout(loadStrategyPanel, BoxLayout.Y_AXIS));
    loadStrategyPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    loadStrategyPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    loadStrategyPanel.setVisible(true);

    JLabel loadStrategyText1 = new JLabel("LOAD STRATEGY");
    loadStrategyText1.setFont(new Font("Serif", Font.BOLD, 25));
    loadStrategyText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    loadStrategyPanel.add(loadStrategyText1);

    JLabel loadStrategyText2 = new JLabel("Enter the file name from " +
            "which portfolio is to be retrieved :");
    loadStrategyText2.setFont(new Font("Serif", Font.BOLD, 20));
    loadStrategyText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    loadStrategyPanel.add(loadStrategyText2);

    JLabel loadStrategyText3 = new JLabel("FORMAT: fileName");
    loadStrategyText3.setFont(new Font("Serif", Font.BOLD, 20));
    loadStrategyText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    loadStrategyPanel.add(loadStrategyText3);

    createInputLoadStrategy = new JTextField();
    createInputLoadStrategy.setFont(new Font("Serif", Font.BOLD, 20));
    createInputLoadStrategy.setMaximumSize(new Dimension(500, 50));
    createInputLoadStrategy.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    loadStrategyPanel.add(createInputLoadStrategy);
    loadStrategyPanel.add(new JLabel("  "));

    createButtonLoadStrategy = new JButton("LOAD STRATEGY");
    createButtonLoadStrategy.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonLoadStrategy.setActionCommand("loadStrategyButton");
    loadStrategyPanel.add(createButtonLoadStrategy);
    options.add(loadStrategyPanel, "loadStrategy");
  }


  /**
   * Creates a remove portfolio Panel, available for the user in the GUI to enter the required
   * details to remove a portfolio and further perform relevant action on it.
   */
  public void createRemovePortfolioPanel() {
    JPanel removePortfolioPanel = new JPanel();
    removePortfolioPanel.setLayout(new BoxLayout(removePortfolioPanel, BoxLayout.Y_AXIS));
    removePortfolioPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 5));
    removePortfolioPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    removePortfolioPanel.setVisible(true);

    JLabel removePortfolioText1 = new JLabel("REMOVE STRATEGY");
    removePortfolioText1.setFont(new Font("Serif", Font.BOLD, 25));
    removePortfolioText1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
    removePortfolioPanel.add(removePortfolioText1);

    JLabel removePortfolioText2 = new JLabel("Enter the name of the  " +
            "portfolio to be removed :");
    removePortfolioText2.setFont(new Font("Serif", Font.BOLD, 20));
    removePortfolioText2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    removePortfolioPanel.add(removePortfolioText2);

    JLabel removePortfolioText3 = new JLabel("FORMAT: portfolioName");
    removePortfolioText3.setFont(new Font("Serif", Font.BOLD, 20));
    removePortfolioText3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
    removePortfolioPanel.add(removePortfolioText3);

    createInputRemovePortfolio = new JTextField();
    createInputRemovePortfolio.setFont(new Font("Serif", Font.BOLD, 20));
    createInputRemovePortfolio.setMaximumSize(new Dimension(500, 50));
    createInputRemovePortfolio.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 5));
    removePortfolioPanel.add(createInputRemovePortfolio);
    removePortfolioPanel.add(new JLabel("  "));

    createButtonRemovePortfolio = new JButton("REMOVE PORTFOLIO");
    createButtonRemovePortfolio.setFont(new Font("Serif", Font.BOLD, 14));
    createButtonRemovePortfolio.setActionCommand("removePortfolioButton");
    removePortfolioPanel.add(createButtonRemovePortfolio);
    options.add(removePortfolioPanel, "removePortfolio");
  }


  /**
   * Creates a display Panel, available for the user in the GUI, where the details of the portfolio
   * are displayed.
   */
  public void displayPotfolioDetails() {
    JLabel portfolioTitle = new JLabel("PORTFOLIO DETAILS");
    portfolioTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    portfolioTitle.setFont(new Font("Serif", Font.BOLD, 25));
    portfolioTitle.setForeground(Color.DARK_GRAY);
    displays.add(portfolioTitle);

    portfolioArea = new JTextArea("");
    portfolioArea.setBounds(50, 50, 150, 150);
    portfolioArea.setColumns(30);
    portfolioArea.setRows(11);
    portfolioArea.setLineWrap(true);
    portfolioArea.setFont(new Font("Serif", Font.BOLD, 15));
    portfolioArea.setEditable(false);
    JScrollPane scroll = new JScrollPane(portfolioArea);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    displays.add(scroll);
  }

  /**
   * Creates a display Panel, available for the user in the GUI, where the output of operations is
   * displayed.
   */
  public void displayOutputOfOperations() {
    JLabel outputTitle = new JLabel("OUTPUT OF OPERATION");
    outputTitle.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
    outputTitle.setFont(new Font("Serif", Font.BOLD, 25));
    outputTitle.setForeground(Color.DARK_GRAY);
    displays.add(outputTitle);

    outputArea = new JTextArea("");
    outputArea.setBounds(50, 50, 150, 150);
    outputArea.setColumns(30);
    outputArea.setRows(11);
    outputArea.setLineWrap(true);
    outputArea.setFont(new Font("Serif", Font.BOLD, 15));
    outputArea.setEditable(false);
    JScrollPane scrol2 = new JScrollPane(outputArea);
    scrol2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    displays.add(scrol2);
  }


  /**
   * Creates a display Panel, available for the user in the GUI, where the error messages and
   * instructions(if any) are displayed.
   */
  public void displayErrorInstructions() {
    JLabel error = new JLabel("ERRORS & INSTRUCTIONS");
    error.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
    error.setFont(new Font("Serif", Font.BOLD, 25));
    error.setForeground(Color.DARK_GRAY);
    displays.add(error);
    errorArea = new JTextArea("");
    errorArea.setBounds(50, 50, 150, 150);
    errorArea.setColumns(30);
    errorArea.setRows(11);
    errorArea.setLineWrap(true);
    errorArea.setForeground(Color.red);
    errorArea.setFont(new Font("Serif", Font.BOLD, 15));
    errorArea.setEditable(false);
    JScrollPane scrol3 = new JScrollPane(errorArea);
    scrol3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    displays.add(scrol3);

  }

  @Override
  public void displayOptions() {
    errorArea.setText(" ");
  }

  @Override
  public void errorMessage(String errMsg) {
    errorArea.setText(errMsg);
  }

  @Override
  public void instructionMessage(String instMsg) {
    errorArea.setText(instMsg);
  }

  @Override
  public void operationSuccessful(String succMsg) {
    outputArea.setText(succMsg);
  }

  @Override
  public void setPortfolioOverview(String overview) {
    portfolioArea.setText(overview);
  }

  @Override
  public String getInputData() {
    switch (currentSelection) {
      case "create": {
        return createInput.getText();
      }
      case "select": {
        return selectInput.getText();
      }
      case "set": {
        return createInputSetCommission.getText();
      }
      case "display": {
        return createInputDisplayShare.getText();
      }
      case "buy": {
        return createInputBuyShare.getText();
      }
      case "invest": {
        return createInputInvestMoney.getText();
      }
      case "costBasisAvg": {
        return createInputCostBasisAvg.getText();
      }
      case "sell": {
        return createInputSellShare.getText();
      }
      case "getShareDetail": {
        return createInputGetShareDetail.getText();
      }
      case "getCostBasis": {
        return createInputGetCostBasis.getText();
      }
      case "savePortfolio": {
        return createInputSavePortfolio.getText();
      }
      case "saveStrategy": {
        return createInputSaveStrategy.getText();
      }
      case "loadPortfolio": {
        return createInputLoadPortfolio.getText();
      }
      case "loadStrategy": {
        return createInputLoadStrategy.getText();
      }
      case "remove": {
        return createInputRemovePortfolio.getText();
      }
      default: {
        return "";
      }
    }
  }

  @Override
  public void setPortfolioName(String name) {
    title1.setText(name);
  }

  @Override
  public void setListener(ActionListener listener) {
    createButtonSellShare.addActionListener(listener);
    createButtonSellShareMulti.addActionListener(listener);
    createButtonCostBasisAvg.addActionListener(listener);
    createButtonCostBasisAvgPer.addActionListener(listener);
    createButton.addActionListener(listener);
    selectButton.addActionListener(listener);
    createButtonCommission.addActionListener(listener);
    createButtonDisplayShare.addActionListener(listener);
    createButtonBuyShare.addActionListener(listener);
    createButtonInvestMoney.addActionListener(listener);
    createButtonCostBasisAvgPerEnd.addActionListener(listener);
    createButtonGetShareDetail.addActionListener(listener);
    createButtonGetCostBasis.addActionListener(listener);
    createButtonSavePortfolio.addActionListener(listener);
    createButtonSaveStrategy.addActionListener(listener);
    createButtonLoadPortfolio.addActionListener(listener);
    createButtonLoadStrategy.addActionListener(listener);
    createButtonRemovePortfolio.addActionListener(listener);
  }

}