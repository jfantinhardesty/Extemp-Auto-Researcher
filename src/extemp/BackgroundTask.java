package extemp;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * Background tasks used with threads.
 */
class BackgroundTask extends SwingWorker<String, Object> {

  /**
   * JTextArea to display information.
   */
  private final transient JTextArea textArea;
  
  /**
   * Date string that keeps track of which button was pushed.
   */
  private final transient String date;
  
  /**
   * Creates the background task for the JTextArea.
   */
  public BackgroundTask(final JTextArea textArea, final String date) {
    super();
    this.textArea = textArea;
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