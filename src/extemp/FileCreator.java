package extemp;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A Utility class FileCreator.
 */
public final class FileCreator {
  /**
   * Name of the folder which will store every article.
   */
  private static String baseFolder = "articles";
  /**
   * Name of the file that stores a list of urls that have been indexed by the
   * program.
   */
  private static String sourceName = "index";
  /**
   * Array of all news sources that articles are pulled from.
   */
  private static String[] sourceNames = { "Al Jazeera", "All Africa",
      "American Enterprise Insitute", "American Progress", "Asia Times", "Associated Press", "BBC",
      "Belfer Center", "Bipartisan Policy Center", "Bloomberg", "Business Insider",
      "Carnegie Endowment for International Peace", "Cato Institute",
      "Center for Immigration Studies", "Center on Budget and Policy Priorities", "Chatham House",
      "Christian Science Monitor", "CNN", "Council on Foreign Relations", "Defense One",
      "Der Spiegel International", "Economic Policy Institute",
      "Electronic Privacy Information Center", "European Council on Foreign Relations",
      "Financial Times", "Freedom House", "Foreign Policy", "Foreign Policy in Focus", "Fox News",
      "Gallup", "Hoover Institution", "Huffington Post", "Human Rights Watch",
      "Independent Institute", "International Monetary Fund", "IRIN", "Kaiser Family Foundation",
      "LA Times", "Lowy Institute", "Manhatten Institute", "Merco Press", "Mises Institute",
      "Mother Jones", "National Public Radio", "New America", "New Republic",
      "Open Society Foundations", "Pambazuka News",
      "Peterson Institute for International Economics", "Pew Research Center", "PolitiFact",
      "Rand Corporation", "Real Clear Politics", "Reuters", "Russian Times", "Scientific American",
      "Slate", "South China Morning Post", "The Aspen Institute", "The Atlantic",
      "The Atlantic Council", "The Brookings Institute", "The Carnegie Endowment",
      "The Conversation", "The Diplomat", "The Economist", "The Global Observatory", "The Guardian",
      "The Hill", "The International Crisis Group", "The Japan Times", "The Jerusalem Post",
      "The Long War Journal", "The Moscow Times", "The National Interest", "The New York Times",
      "The Sydney Morning Herald", "Think Progress", "Time Magazine", "Toronto Star",
      "United Nations University", "Urban Institute", "Wall Street Journal", "Washington Examiner",
      "Washington Post", "Washington Times", "Wilson Center" };
  /**
   * HasMap of all sources information and the corresponding news outlet. Not
   * complete for the following Copyright 2017, ecfr, epic, Council on Foreign
   * Relations, Hoover Institution, Manhattan Institute
   */
  private static final Map<String, String> SOURCEMAP = createSourceMap();

  /**
   * Creates the map associating the saved title in the database with a simple
   * source title.
   * 
   * @return map for the database title and a simple title
   */
  private static Map<String, String> createSourceMap() {
    final Map<String, String> sourceMap = new ConcurrentHashMap<String, String>();
    sourceMap.put("TheHill - The Hill News", "The Hill");
    sourceMap.put("ThinkProgress - Medium", "Think Progress");
    sourceMap.put("Americas", "The Economist");
    sourceMap.put("Asia", "The Economist");
    sourceMap.put("Britain", "The Economist");
    sourceMap.put("United States", "The Economist");
    sourceMap.put("Business and finance", "The Economist");
    sourceMap.put("China", "The Economist");
    sourceMap.put("Culture", "The Economist");
    sourceMap.put("Europe", "The Economist");
    sourceMap.put("Economics", "The Economist");
    sourceMap.put("International", "The Economist");
    sourceMap.put("Leaders", "The Economist");
    sourceMap.put("Science and technology", "The Economist");
    sourceMap.put("Middle East and Africa", "The Economist");
    sourceMap.put("World", "Washington Post");
    sourceMap.put("World – TIME", "Time Magazine");
    sourceMap.put("TIME", "Time Magazine");
    sourceMap.put("Tech – TIME", "Time Magazine");
    sourceMap.put("Politics – TIME", "Time Magazine");
    sourceMap.put("Newsfeed – TIME", "Time Magazine");
    sourceMap.put("Business – TIME", "Time Magazine");
    sourceMap.put("www.washingtontimes.com stories: News", "");
    sourceMap.put("www.washingtontimes.com stories: Culture", "");
    sourceMap.put("Politics", "Washington Times");
    sourceMap.put("SPIEGEL ONLINE - International", "Der Spiegel International");
    sourceMap.put("Sydney Morning Herald RSS News Headlines", "The Sydney Morning Herald");
    sourceMap.put("The Aspen Institute", "The Aspen Institute");
    sourceMap.put("The Diplomat", "The Diplomat");
    sourceMap.put("Slate Articles", "Slate");
    sourceMap.put("Open Society Foundations", "Open Society Foundations");
    sourceMap.put("Pambazuka News - Pan-Africanism", "Pambazuka News");
    sourceMap.put("Pambazuka News - Land &amp; Environment", "Pambazuka News");
    sourceMap.put("Pambazuka News - ICT, Media &amp; Security", "Pambazuka News");
    sourceMap.put("Pambazuka News - Human Security", "Pambazuka News");
    sourceMap.put("Pambazuka News - Governance", "Pambazuka News");
    sourceMap.put("Pambazuka News - Global South", "Pambazuka News");
    sourceMap.put("Pambazuka News - Gender &amp; Minorities", "Pambazuka News");
    sourceMap.put("Pambazuka News - Advocacy &amp; Campaigns", "Pambazuka News");
    sourceMap.put("Peterson Institute Update", "Peterson Institute for International Economics");
    sourceMap.put("Pew Research Center", "Pew Research Center");
    sourceMap.put("Politifact.com Truth-O-Meter rulings from National", "PolitiFact");
    sourceMap.put("Politifact.com stories from National", "PolitiFact");
    sourceMap.put("Recent posts", "Center for Immigration Studies");
    sourceMap.put("RSS", "International Monetary Fund");
    sourceMap.put("RT", "Russian Times");
    sourceMap.put("MoJo Articles | Mother Jones", "Mother Jones");
    sourceMap.put("New America", "New America");
    sourceMap.put("New Republic", "New Republic");
    sourceMap.put("Independent Institute Articles", "Independent Institute");
    sourceMap.put("Brookings: Up Front", "The Brookings Institute");
    sourceMap.put("Latest From Brookings", "The Brookings Institute");
    sourceMap.put("Latest From the Wilson Center", "Wilson Center");
    sourceMap.put("All", "IRIN");
    sourceMap.put("All Research", "Belfer Center");
    sourceMap.put("Bipartisan Policy Center", "Bipartisan Policy Center");
    sourceMap.put("Cato Recent Op-eds", "Cato Institute");
    sourceMap.put("Center on Budget: Comprehensive News Feed",
        "Center on Budget and Policy Priorities");
    sourceMap.put("Copyright 2008", "Real Clear Politics");
    sourceMap.put("Defense One - All Content", "Defense One");
    sourceMap.put("Economic Policy Institute", "Economic Policy Institute");
    sourceMap.put("Foreign Policy", "Foreign Policy");
    sourceMap.put("Foreign Policy In FocusForeign Policy In Focus", "Foreign Policy in Focus");
    sourceMap.put("Lowy Institute", "Freedom House");
    sourceMap.put("Gallup.Com", "Gallup");
    sourceMap.put("© 1995 - 2017 The Jerusalem Post. All rights reserved.", "The Jerusalem Post");
    sourceMap.put(
        "© Copyright The Financial Times Ltd 2017. &quot;FT&quot; and &quot;Financial Times&quot; "
            + "are trademarks of the Financial Times. "
            + "See http://www.ft.com/servicestools/help/terms#legal1 for the terms and conditions of reuse.",
        "Financial Times");
    sourceMap.put("©2017 Los Angeles Times", "LA Times");
    sourceMap.put("Â© 2015 Aljazeera Network", "Al Jazeera");
    sourceMap.put(
        "All rights reserved. Users may download and print extracts of content from this website "
            + "for their own personal and non-commercial use only. Republication or redistribution "
            + "of Reuters content, including by framing or similar means, is expressly prohibited "
            + "without "
            + "the prior written consent of Reuters. Reuters and the Reuters sphere logo are "
            + "registered trademarks or trademarks of the Reuters group of companies around "
            + "the world. © Reuters 2017",
        "Reuters");
    sourceMap.put("AllAfrica News: Actualités", "All Africa");
    sourceMap.put("Christian Science Monitor | All Stories", "Christian Science Monitor");
    sourceMap.put("copyright  © 2017 Dow Jones &amp; Company, Inc.", "Wall Street Journal");
    sourceMap.put("Copyright (c) 2017 Turner Broadcasting System, Inc. All Rights Reserved.",
        "CNN");
    sourceMap.put("Copyright 2017  The New York Times Company", "The New York Times");
    sourceMap.put("Copyright 2017 BLOOMBERG L.P. ALL RIGHTS RESERVED", "Bloomberg");
    sourceMap.put("Copyright 2017 by The Atlantic Monthly Group. All Rights Reserved.",
        "The Atlantic");
    sourceMap.put("Copyright 2017 NPR - For Personal Use Only", "National Public Radio");
    sourceMap.put("Copyright 2017, MercoPress.", "Merco Press");
    sourceMap.put("Copyright Toronto Star 1996-2013 , http://www.thestar.com/terms",
        "Toronto Star");
    sourceMap.put(
        "Copyright: (C) British Broadcasting Corporation, see "
            + "http://news.bbc.co.uk/2/hi/help/rss/4498287.stm for terms and conditions of reuse.",
        "BBC");
    sourceMap.put(
        "Copyright2017 The Associated Press. All rights reserved. This "
            + "material may not be published, broadcast, rewritten or redistributed",
        "Associated Press");
    sourceMap.put("FOX News", "Fox News");
    sourceMap.put("Copyright 2017 FOX News Channel", "Fox News");
    sourceMap.put(
        "Guardian News and Media Limited or its affiliated companies. All rights reserved. 2017",
        "The Guardian");
    sourceMap.put("Scientific American, a Division of Nature America, Inc.", "Scientific American");
    sourceMap.put("South China Morning Post - News feed", "South China Morning Post");
    sourceMap.put("South China Morning Post - Hong Kong feed", "South China Morning Post");
    sourceMap.put("South China Morning Post - China feed", "South China Morning Post");
    sourceMap.put("South China Morning Post - World feed", "South China Morning Post");
    sourceMap.put("South China Morning Post - Asia feed", "South China Morning Post");
    sourceMap.put("The Huffington Post | Full News Feed", "Huffington Post");
    sourceMap.put("Carnegie Endowment for International Peace -\r\n" + "Carnegie Publications",
        "The Carnegie Endowment");
    sourceMap.put("Center for American ProgressCenter for American Progress", "American Progress");
    sourceMap.put("Copyright (c) 2017, The RAND Corporation", "Rand Corporation");
    sourceMap.put("Copyright 2017 by the Council on Foreign Relations. All Rights Reserved.",
        "Council on Foreign Relations");
    sourceMap.put("Mises Institute", "Mises Institute");
    sourceMap.put("News – United Nations University", "United Nations University");
    sourceMap.put("The Henry J. Kaiser Family Foundation", "Kaiser Family Foundation");
    sourceMap.put("Polls and Surveys about Health Policy from the Kaiser Family Foundation",
        "Kaiser Family Foundation");
    sourceMap.put("Urban Center", "Urban Center");
    return sourceMap;
  }

  /**
   * Private constructor because it is a utility class.
   */
  private FileCreator() {
    // Does nothing
  }

  /**
   * Creates a directory of all the files, an index for the articles, and a
   * current timestamp.
   */
  public static void createFiles() {
    createDirectory();
    createIndex();
    createTimeStamp();
  }

  /**
   * Creates a folder which will store folders for each news source that will
   * store their corresponding articles.
   */
  private static void createDirectory() {
    for (int i = 0; i < sourceNames.length; i++) {
      new File("articles/" + sourceNames[i]).mkdirs();
    }
  }

  /**
   * Creates a text file to store a list of urls.
   */
  private static void createIndex() {
    final Path path = Paths.get(baseFolder + "/" + sourceName + ".txt");
    if (!Files.exists(path)) {
      try {
        Files.createFile(path);
      } catch (IOException e) {
        // TODO
      }
    }
  }

  /**
   * Creates a text file to store a timestamp.
   */
  private static void createTimeStamp() {
    final String timestamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    final byte[] strToBytes = timestamp.getBytes();
    // new File("timestamp.txt");
    final Path file = Paths.get("timestamp.txt");
    if (Files.exists(file)) {
      try {
        Files.write(file, strToBytes);
      } catch (IOException e) {
        // TODO Auto-generated catch block'
      }
    } else {
      try {
        Files.createFile(file);
      } catch (IOException e) {
        // TODO Auto-generated catch block'
      }
    }
  }

  /**
   * Create a text file of the article.
   * 
   * @param content
   *          article contents that will be added to the file.
   * @param url
   *          contains the article title, url, and source.
   * @return true if the file was created, false otherwise
   */
  public static boolean createFile(final String content, final UrlInfo url) {
    boolean failure = false;
    if (content != null) {
      final ArrayList<String> lines = new ArrayList<String>();
      final ArrayList<String> headerLines = new ArrayList<String>();
      final ArrayList<String> footerLines = new ArrayList<String>();
      String sourceName;
      final String urlLink = url.getUrl();
      final String urlTitle = url.getTitle();

      sourceName = SOURCEMAP.get(url.getSource());

      if (sourceName == null) {
        sourceName = "";
        try (FileWriter fileWrite = new FileWriter("articles/noIfElse.txt", true);
            BufferedWriter buffRead = new BufferedWriter(fileWrite);
            PrintWriter output = new PrintWriter(buffRead)) {
          output.println(urlLink);
        } catch (IOException e) {
          // TODO
        }
      }
      headerLines.add(urlTitle);
      headerLines.add(sourceName);
      headerLines.add("");
      footerLines.add("");
      footerLines.add(urlLink);
      try {
        lines.add(content);
        final Path file = Paths.get(baseFolder + "/" + sourceName + "/"
            + urlTitle.replaceAll("[^a-zA-Z0-9_\\-\\.]", "") + ".txt");
        Files.createFile(file);
        Files.write(file, headerLines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        Files.write(file, footerLines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
      } catch (FileAlreadyExistsException e) {
        failure = true;
      } catch (IOException e) {
        failure = true;
      }
    }
    return failure;
  }
}
