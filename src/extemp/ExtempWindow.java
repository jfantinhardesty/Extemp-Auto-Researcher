package extemp;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Application window that is responsible for the creation of all objects in the frame.
 */
public class ExtempWindow extends JFrame {
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Launch the application.
   */
  public static void start() {
    EventQueue.invokeLater(() -> {
      final ExtempWindow window = new ExtempWindow();
      window.setVisible(true);
    });
  }

  /**
   * Create the application.
   */
  public ExtempWindow() {
    super("Extemp Auto Researcher");
    initialize();
  }

  /**
   * Initialize the contents of the frame by creating the menu bar, search bar,
   * table, and text area.
   */
  private void initialize() {
    // Initialize the bounds for the window
    setBounds(100, 100, 800, 800);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    // Set the layout to grid bag layout
    GridBagLayout gbl = new GridBagLayout();
    gbl.columnWidths = new int[] { 0, 0, 0, 0 };
    gbl.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    gbl.columnWeights = new double[] { 0.0, 0.0, 0, Double.MIN_VALUE };
    gbl.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
        Double.MIN_VALUE };
    setLayout(gbl);

    GridBagConstraints gbc = new GridBagConstraints();

    // Create the MenuBar
    gbc.weightx = .5;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.NORTH;
    MenuBar menuBar = new MenuBar();
    add(menuBar, gbc);

    // Creates the search box that will hold the text field
    JPanel searchBox = new JPanel();
    searchBox.setPreferredSize(new Dimension(400, 32));
    searchBox.setMinimumSize(new Dimension(400, 32));

    // Creates the text field where the user will type in their search
    TextField searchText = new TextField();
    searchText.setPreferredSize(new Dimension(300, 20));
    searchText.setMinimumSize(new Dimension(300, 20));
    searchBox.add(searchText);

    // Adds the search button which will execute the search when pressed
    JButton searchBtn = new JButton("Search");
    searchBtn.setPreferredSize(new Dimension(80, 20));
    searchBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          SearchFiles.search(searchText.getText());
        } catch (Exception event) {
          // TODO Auto-generated catch block
          event.printStackTrace();
        }
      }
    });
    searchBox.add(searchBtn);

    // Adds the search box to the window
    gbc.weightx = .5;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.NORTH;
    searchBox.setBorder(new TitledBorder(new EtchedBorder()));
    add(searchBox, gbc);

    // Adds the table to the window that will store the search results
    gbc.weightx = .5;
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.NORTH;
    TextDisplayer textDisplay = new TextDisplayer();
    FileSelector fileSelector = new FileSelector(textDisplay);
    add(fileSelector.getTable(), gbc);

    // Adds the text area which will display the article text
    gbc.weightx = .5;
    gbc.weighty = 2;
    gbc.gridy = 3;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.NORTH;
    add(textDisplay.getTextArea(), gbc);

    pack();
  }
}