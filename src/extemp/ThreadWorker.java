package extemp;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;

import java.io.IOException;
import java.net.URL;

import org.xml.sax.SAXException;

/**
 * Runs the threads.
 */
public class ThreadWorker implements Runnable {
  /**
   * Is false if a thread is not currently running.
   */
  private boolean running;

  /**
   * Is false if a thread has not failed.
   */
  private boolean failure;

  /**
   * Contains the title, url and source of the url.
   */
  private final UrlInfo urlInfo;

  /**
   * Creates the thread.
   * 
   * @param url
   *          a urlInfo object containing the title, source and link to the url.
   */
  public ThreadWorker(final UrlInfo url) {
    urlInfo = url;

    final Thread thread = new Thread(this);
    thread.setDaemon(true);
    thread.start();
  }

  /**
   * Returns the url link.
   * 
   * @return the url link
   */
  public String getUrl() {
    return urlInfo.getUrl();
  }

  /**
   * Returns if the thread failed.
   * 
   * @return true if the thread failed, false otherwise
   */
  public boolean isFailure() {
    return failure;
  }

  /**
   * Returns if the thread is running.
   * 
   * @return true if the thread is running, false otherwise
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Runs the thread.
   */
  @Override
  public void run() {
    running = true;

    try {
      final URL url = new URL(getUrl());
      final HTMLDocument htmlDoc = HTMLFetcher.fetch(url);
      final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
      final String content = CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);
      if (!isFailure()) {
        final FileCreator fileCreator = new FileCreator();
        failure = fileCreator.createTextArticle(content, urlInfo);
      }
    } catch (IOException | BoilerpipeProcessingException | SAXException e) {
      failure = true;
    }

    running = false;
  }
}