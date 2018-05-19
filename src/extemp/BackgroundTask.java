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
  private final JTextArea textArea;

  /**
   * Date string that keeps track of which button was pushed.
   */
  private final String date;

  /**
   * Creates the background task for the JTextArea.
   * 
   * @param textArea
   *          a JTextArea to display information to the user.
   * @param date
   *          date when articles will be indexed from.
   */
  public BackgroundTask(final JTextArea textArea, final String date) {
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