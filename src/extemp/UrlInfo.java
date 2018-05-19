package extemp;

/**
 * Stores the url, source, and title.
 */
public class UrlInfo {
  /**
   * A string that stores the url.
   */
  private final String url;

  /**
   * A string that stores the name of the news source.
   */
  private final String source;

  /**
   * A string that store the title of the article.
   */
  private final String title;

  /**
   * Create a UrlInfo object which holds the url, title, and source of an article.
   * 
   * @param url
   *          string containing the actual url.
   * @param title
   *          string containing the title of the article.
   * @param source
   *          string containing the source name.
   */
  public UrlInfo(final String url, final String title, final String source) {
    this.url = url;
    this.title = title;
    this.source = source;
  }

  /**
   * Returns the url.
   * 
   * @return the url link
   */
  public String getUrl() {
    return url;
  }

  /**
   * Returns the source.
   * 
   * @return the source of the article
   */
  public String getSource() {
    return source;
  }

  /**
   * Returns the title.
   * 
   * @return the title of the article
   */
  public String getTitle() {
    return title;
  }
}
