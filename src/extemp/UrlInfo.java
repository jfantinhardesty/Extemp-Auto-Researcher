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
   * Create a UrlInfo object which holds the url, title, and source of an article.\
   * 
   * @param url String
   * @param title String
   * @param source String
    */
  public UrlInfo(final String url, final String title, final String source) {
    this.url = url;
    this.title = title;
    this.source = source;
  }
  
  /**
   * Returns the url.
    */
  public String getUrl() {
    return url;
  }
  
  /**
   * Returns the source.
    */
  public String getSource() {
    return source;
  }
  
  /**
   * Returns the title.
    */
  public String getTitle() {
    return title;
  }
}
