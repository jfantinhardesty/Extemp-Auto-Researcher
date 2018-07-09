package extemp;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/**
 * Application window.
 */
public class DownloadFrame extends JFrame {
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Text area that will display information about indexing and downloading of
   * documents.
   */
  private final JTextArea textDownload = new JTextArea();

  /**
   * The scroll pane.
   */
  private final JScrollPane scrollPane = new JScrollPane(textDownload);

  /**
   * Launch the application.
   */
  public static void start() {
    EventQueue.invokeLater(() -> {
      final DownloadFrame window = new DownloadFrame();
      window.setVisible(true);
    });
  }

  /**
   * Create the application.
   */
  public DownloadFrame() {
    super("Downloading Articles");
    initialize();
  }
  
  /**
   * Returns the text area to display information about the current download.
   * @return text area to display download info
   */
  public JTextArea getJTextArea() {
    return textDownload;
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    setBounds(100, 100, 779, 465);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setVisible(true);
    setLayout(new GridBagLayout());

    textDownload.setWrapStyleWord(true);
    textDownload.setLineWrap(true);
    textDownload.setEditable(false);

    final GridBagConstraints gbc = new GridBagConstraints();

    scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2, true));
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.CENTER;
    add(scrollPane, gbc);
  }
}