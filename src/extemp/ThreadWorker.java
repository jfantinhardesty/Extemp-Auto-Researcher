package extemp;

import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;

import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Runs the threads.
 */
public class ThreadWorker implements Runnable {
  /**
   * Is false if a thread is not currently running.
   */
  private boolean running = false;
  /**
   * Is false if a thread has not failed.
   */
  private boolean failure = false;
  /**
   * Name of the source.
   */
  private final String sourceListName;
  /**
   * Name of the title.
   */
  private final String titleListName;
  /**
   * Name of the url.
   */
  private final String urlListName;

  /**
   * Creates the thread.
   * 
   * @param urlListName
   *          String containing the url of the article
   * @param titleListName
   *          String containing the title of the article
   * @param sourceListName
   *          String containing the source of the title
   */
  public ThreadWorker(final String urlListName, final String titleListName,
      final String sourceListName) {
    this.sourceListName = sourceListName;
    this.titleListName = titleListName;
    this.urlListName = urlListName;

    final Thread thread = new Thread(this);
    thread.setDaemon(true);
    thread.start();
  }

  /**
   * Returns if the thread failed.
   */
  public boolean isFailure() {
    return failure;
  }

  /**
   * Returns if the thread is running.
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Returns the url.
   */
  public String getUrl() {
    return urlListName;
  }

  /**
   * Runs the thread.
   */
  @Override
  public void run() {
    this.running = true;

    try {
      final URL url = new URL(urlListName);
      final HTMLDocument htmlDoc = HTMLFetcher.fetch(url);
      final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
      String content = CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);
      if (!isFailure()) {
        failure = FileCreator.createFile(content, sourceListName, titleListName, urlListName);
      }
    } catch (SocketTimeoutException e) {
      failure = true;
    } catch (Exception e) {
      // e.printStackTrace();
      // System.out.println(e);
      failure = true;
    }

    this.running = false;
  }
}