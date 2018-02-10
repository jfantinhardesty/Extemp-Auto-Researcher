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
   * Creates the background task for the JTextArea.
   */
  public BackgroundTask(final JTextArea textArea) {
    super();
    this.textArea = textArea;
  }

  /**
   * Starts indexing articles in the background.
   */
  @Override
  public String doInBackground() {
    final MainEngine indexPrevious = new MainEngine();
    indexPrevious.startDownload(textArea);
    return "done";
  }
}