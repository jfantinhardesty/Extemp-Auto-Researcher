package extemp;

/**
 * Stores the url, source, and title.
  */
public class UrlInfo {
  /**
   * A string that stores the url.
    */
  public String url;
  /**
   * A string that stores the name of the news source.
    */
  public String source;
  /**
   * A string that store the title of the article.
    */
  public String title;
  
  /**
   * Create a UrlInfo object which holds the url, title, and source of an article.\
   * 
   * @param url String
   * @param title String
   * @param source String
    */
  UrlInfo(final String url, final String title, final String source) {
    this.url = url;
    this.title = title;
    this.source = source;
  }
}
