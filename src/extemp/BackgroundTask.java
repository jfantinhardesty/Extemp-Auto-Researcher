package extemp;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * Background tasks used with threads.
 */
public class BackgroundTask extends SwingWorker<String, Object> {
  /**
   * JTextArea to display information.
   */
  private JTextArea textArea;

  /**
   * Date string that keeps track of which button was pushed.
   */
  private final String date;

  /**
   * Creates the background task for the JTextArea.
   * 
   * @param date
   *          date when articles will be indexed from.
   */
  public BackgroundTask(final String date) {
    super();
    DownloadFrame frame = new DownloadFrame();
    textArea = frame.getJTextArea();
    this.date = date;
  }

  /**
   * Starts indexing articles in the background.
   */
  @Override
  public String doInBackground() {
    final MainEngine indexPrevious = new MainEngine();
    indexPrevious.startDownload(textArea, date);
    return "done";
  }
}