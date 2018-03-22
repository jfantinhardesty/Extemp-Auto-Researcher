package extemp;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.xml.sax.SAXException;

/**
 * Runs the threads.
 */
public class ThreadWorker implements Runnable {
  /**
   * Is false if a thread is not currently running or failed.
   */
  private boolean running = false;

  /**
   * Contains the title, url and source of the url.
   */
  private final UrlInfo urlInfo;

  /**
   * Creates the thread.
   * 
   * @param url a urlInfo object containing the title, source and link to the url.
   */
  public ThreadWorker(final UrlInfo url) {
    this.urlInfo = url;

    final Thread thread = new Thread(this);
    thread.setDaemon(true);
    thread.start();
  }

  /**
   * Returns if the thread is running.
   */
  public boolean isRunning() {
    return running;
  }
  
  /**
   * Returns the url link.
   */
  public String getUrl() {
    return urlInfo.getUrl();
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
      if (isRunning()) {
        running = FileCreator.createFile(content, urlInfo);
      }
    } catch (SocketTimeoutException e) {
      running = false;
    } catch (MalformedURLException e) {
      running = false;
    } catch (IOException e) {
      running = false;
    } catch (BoilerpipeProcessingException e) {
      running = false;
    } catch (SAXException e) {
      running = false;
    }

    running = false;
  }
}