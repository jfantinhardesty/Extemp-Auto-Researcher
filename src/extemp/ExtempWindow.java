package extemp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Application window.
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
   * Initialize the contents of the frame.
   */
  private void initialize() {
    setBounds(100, 100, 800, 800);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    GridBagLayout gbl = new GridBagLayout();
    gbl.columnWidths = new int[] { 0, 0, 0, 0 };
    gbl.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    gbl.columnWeights = new double[] { 0.0, 0.0, 0, Double.MIN_VALUE };
    gbl.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
        Double.MIN_VALUE };
    setLayout(gbl);

    MenuBar menuBar = new MenuBar();

    GridBagConstraints gbc = new GridBagConstraints();

    gbc.weightx = .5;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.NORTH;
    add(menuBar, gbc);

    JPanel searchBox = new JPanel();
    //searchBox.setLayout(new FlowLayout());
    searchBox.setPreferredSize(new Dimension(400, 32));
    searchBox.setMinimumSize(new Dimension(400, 32));

    TextField searchText = new TextField();
    searchText.setPreferredSize(new Dimension(300, 20));
    searchText.setMinimumSize(new Dimension(300, 20));
    searchBox.add(searchText);

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

    gbc.weightx = .5;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.NORTH;

    searchBox.setBorder(new TitledBorder(new EtchedBorder()));
    add(searchBox, gbc);

    gbc.weightx = .5;
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.NORTH;

    FileSelector fileSelector = new FileSelector();
    add(fileSelector.getTable(), gbc);
    pack();
    revalidate();
  }
}