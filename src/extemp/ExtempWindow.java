package extemp;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
    setBounds(100, 100, 779, 465);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();

    MenuBar menuBar = new MenuBar();
    menuBar.setVisible(true);

    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.NORTH;
    add(menuBar, gbc);

    JPanel searchBox = new JPanel();
    searchBox.setLayout(new FlowLayout());
    searchBox.setMinimumSize(new Dimension(400, 40));

    TextField searchText = new TextField();
    searchText.setPreferredSize(new Dimension(300, 20));
    searchBox.add(searchText);

    JButton searchBtn = new JButton("Search");
    searchBtn.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        try {
          SearchFiles.search(searchText.getText());
        } catch (Exception event) {
          // TODO Auto-generated catch block
          event.printStackTrace();
        }
      } 
    } );
    searchBox.add(searchBtn);

    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.NORTH;

    searchBox.setBorder(new TitledBorder(new EtchedBorder(), "Search"));
    add(searchBox, gbc);

    gbc.weightx = 2;
    gbc.weighty = 2;
    gbc.gridy = 3;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.NORTH;

    final List<List<Object>> tableData = new ArrayList<List<Object>>();
    tableData.add(new ArrayList<Object>(
        Arrays.asList("1", "test1.txt", "C://test1.txt", "C:/Test/test1.txt")));
    tableData.add(new ArrayList<Object>(
        Arrays.asList("2", "test2.txt", "C://test2.txt", "C:/Test/test2.txt")));
    tableData.add(new ArrayList<Object>(
        Arrays.asList("3", "test2.txt", "C://test3.txt", "C:/Test/test3.txt")));

    FileSelector fileSelector = new FileSelector(tableData);
    add(fileSelector.getTable(), gbc);
    pack();
  }
}