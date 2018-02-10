package extemp;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
      final Document doc = Jsoup.parse(url, 3500);
      failure = FileCreator.createFile(doc, sourceListName, titleListName, urlListName,
          isFailure());
    } catch (IOException e) {
      // e.printStackTrace();
      // System.out.println(e);
      failure = true;
    } catch (NullPointerException e) {
      // e.printStackTrace();
      failure = true;
    } catch (IllegalArgumentException e) {
      // e.printStackTrace();
      failure = true;
    }

    this.running = false;
  }
}