package extemp;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/**
 * Application window.
 */
public class ExtempWindow {

  /**
   * JFrame.
   */
  private JFrame frame;

  /**
   * Launch the application.
   */
  public static void start() {
    EventQueue.invokeLater(new Runnable() {
      /**
       * Run the main program.
       */
      public void run() {
        try {
          final ExtempWindow window = new ExtempWindow();
          window.frame.setVisible(true);
        } catch (Exception e) {
          // e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public ExtempWindow() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 779, 465);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setLayout(new GridBagLayout());

    final JTextArea textArea = new JTextArea();
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    textArea.setEditable(false);

    final GridBagConstraints gbc = new GridBagConstraints();

    final JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2, true));
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.CENTER;
    frame.add(scrollPane, gbc);

    final FlowLayout flowLay = new FlowLayout(FlowLayout.LEFT);

    final JMenuBar menuBar = new JMenuBar();
    menuBar.setLayout(flowLay);
    scrollPane.setColumnHeaderView(menuBar);

    final JMenu mnIndexArticles = new JMenu("Index Articles");
    menuBar.add(mnIndexArticles);

    final JMenuItem btnPastWeek = new JMenuItem("Past Week");
    mnIndexArticles.add(btnPastWeek);

    final JMenuItem btnPastMonth = new JMenuItem("Past Month");
    mnIndexArticles.add(btnPastMonth);

    final JMenuItem btnPast3Months = new JMenuItem("Past 3 Months");
    mnIndexArticles.add(btnPast3Months);

    final JMenuItem btnPast6Months = new JMenuItem("Past 6 Months");
    mnIndexArticles.add(btnPast6Months);

    final JMenuItem btnSinceIndex = new JMenuItem("Since Previous Index");
    mnIndexArticles.add(btnSinceIndex);

    final JButton btnSearchArticles = new JButton("Search Articles");
    menuBar.add(btnSearchArticles);

    final JButton btnSettings = new JButton("Settings");
    menuBar.add(btnSettings);

    final JButton btnHelp = new JButton("Help");
    menuBar.add(btnHelp);

    btnSinceIndex.addActionListener(new ActionListener() {
      /**
       * Start indexing articles.
       */
      public void actionPerformed(final ActionEvent e) {
        (new BackgroundTask(textArea)).execute();
      }
    });
  }
}