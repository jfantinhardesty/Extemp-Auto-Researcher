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
import java.util.HashMap;

import org.jsoup.nodes.Document;

/**
 * FileCreator.
 */
public class FileCreator {
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
   * HasMap of all sources information and the corresponding news outlet.
   */
  // Not complete for the following
  //Copyright 2017
  // ecfr epic
  // Council on Foreign Relations
  // Hoover Institution
  // Manhattan Institute
  private static final HashMap<String, String> map = new HashMap<String, String>() {
    {
      put("TheHill - The Hill News", "The Hill");
      put("ThinkProgress - Medium", "Think Progress");
      put("Americas", "The Economist");
      put("Asia", "The Economist");
      put("Britain", "The Economist");
      put("United States", "The Economist");
      put("Business and finance", "The Economist");
      put("China", "The Economist");
      put("Culture", "The Economist");
      put("Europe", "The Economist");
      put("Economics", "The Economist");
      put("International", "The Economist");
      put("Leaders", "The Economist");
      put("Science and technology", "The Economist");
      put("Middle East and Africa", "The Economist");
      put("World", "Washington Post");
      put("World – TIME", "Time Magazine");
      put("TIME", "Time Magazine");
      put("Tech – TIME", "Time Magazine");
      put("Politics – TIME", "Time Magazine");
      put("Newsfeed – TIME", "Time Magazine");
      put("Business – TIME", "Time Magazine");
      put("www.washingtontimes.com stories: News", "");
      put("www.washingtontimes.com stories: Culture", "");
      put("Politics", "Washington Times");
      put("SPIEGEL ONLINE - International", "Der Spiegel International");
      put("Sydney Morning Herald RSS News Headlines", "The Sydney Morning Herald");
      put("The Aspen Institute", "The Aspen Institute");
      put("The Diplomat", "The Diplomat");
      put("Slate Articles", "Slate");
      put("Open Society Foundations", "Open Society Foundations");
      put("Pambazuka News - Pan-Africanism", "Pambazuka News");
      put("Pambazuka News - Land &amp; Environment", "Pambazuka News");
      put("Pambazuka News - ICT, Media &amp; Security", "Pambazuka News");
      put("Pambazuka News - Human Security", "Pambazuka News");
      put("Pambazuka News - Governance", "Pambazuka News");
      put("Pambazuka News - Global South", "Pambazuka News");
      put("Pambazuka News - Gender &amp; Minorities", "Pambazuka News");
      put("Pambazuka News - Advocacy &amp; Campaigns", "Pambazuka News");
      put("Peterson Institute Update", "Peterson Institute for International Economics");
      put("Pew Research Center", "Pew Research Center");
      put("Politifact.com Truth-O-Meter rulings from National", "PolitiFact");
      put("Politifact.com stories from National", "PolitiFact");
      put("Recent posts", "Center for Immigration Studies");
      put("RSS", "International Monetary Fund");
      put("RT", "Russian Times");
      put("MoJo Articles | Mother Jones", "Mother Jones");
      put("New America", "New America");
      put("New Republic", "New Republic");
      put("Independent Institute Articles", "Independent Institute");
      put("Brookings: Up Front", "The Brookings Institute");
      put("Latest From Brookings", "The Brookings Institute");
      put("Latest From the Wilson Center", "Wilson Center");
      put("All", "IRIN");
      put("All Research", "Belfer Center");
      put("Bipartisan Policy Center", "Bipartisan Policy Center");
      put("Cato Recent Op-eds", "Cato Institute");
      put("Center on Budget: Comprehensive News Feed", "Center on Budget and Policy Priorities");
      put("Copyright 2008", "Real Clear Politics");
      put("Defense One - All Content", "Defense One");
      put("Economic Policy Institute", "Economic Policy Institute");
      put("Foreign Policy", "Foreign Policy");
      put("Foreign Policy In FocusForeign Policy In Focus", "Foreign Policy in Focus");
      put("Lowy Institute", "Freedom House");
      put("Gallup.Com", "Gallup");
      put("© 1995 - 2017 The Jerusalem Post. All rights reserved.", "The Jerusalem Post");
      put("© Copyright The Financial Times Ltd 2017. &quot;FT&quot; and &quot;Financial Times&quot; are trademarks of the Financial Times. See http://www.ft.com/servicestools/help/terms#legal1 for the terms and conditions of reuse.",
          "Financial Times");
      put("©2017 Los Angeles Times", "LA Times");
      put("Â© 2015 Aljazeera Network", "Al Jazeera");
      put("All rights reserved. Users may download and print extracts of content from this website for their own personal and non-commercial use only. Republication or redistribution of Reuters content, including by framing or similar means, is expressly prohibited without the prior written consent of Reuters. Reuters and the Reuters sphere logo are registered trademarks or trademarks of the Reuters group of companies around the world. © Reuters 2017",
          "Reuters");
      put("AllAfrica News: Actualités", "All Africa");
      put("Christian Science Monitor | All Stories", "Christian Science Monitor");
      put("copyright  © 2017 Dow Jones &amp; Company, Inc.", "Wall Street Journal");
      put("Copyright (c) 2017 Turner Broadcasting System, Inc. All Rights Reserved.", "CNN");
      put("Copyright 2017  The New York Times Company", "The New York Times");
      put("Copyright 2017 BLOOMBERG L.P. ALL RIGHTS RESERVED", "Bloomberg");
      put("Copyright 2017 by The Atlantic Monthly Group. All Rights Reserved.", "The Atlantic");
      put("Copyright 2017 NPR - For Personal Use Only", "National Public Radio");
      put("Copyright 2017, MercoPress.", "Merco Press");
      put("Copyright Toronto Star 1996-2013 , http://www.thestar.com/terms", "Toronto Star");
      put("Copyright: (C) British Broadcasting Corporation, see http://news.bbc.co.uk/2/hi/help/rss/4498287.stm for terms and conditions of reuse.",
          "BBC");
      put("Copyright2017 The Associated Press. All rights reserved. This material may not be published, broadcast, rewritten or redistributed",
          "Associated Press");
      put("FOX News", "Fox News");
      put("Copyright 2017 FOX News Channel", "Fox News");
      put("Guardian News and Media Limited or its affiliated companies. All rights reserved. 2017",
          "The Guardian");
      put("Scientific American, a Division of Nature America, Inc.", "Scientific American");
      put("South China Morning Post - News feed", "South China Morning Post");
      put("South China Morning Post - Hong Kong feed", "South China Morning Post");
      put("South China Morning Post - China feed", "South China Morning Post");
      put("South China Morning Post - World feed", "South China Morning Post");
      put("South China Morning Post - Asia feed", "South China Morning Post");
      put("The Huffington Post | Full News Feed", "Huffington Post");
      put("Carnegie Endowment for International Peace -\r\n" + "Carnegie Publications",
          "The Carnegie Endowment");
      put("Center for American ProgressCenter for American Progress", "American Progress");
      put("Copyright (c) 2017, The RAND Corporation", "Rand Corporation");
      put("Copyright 2017 by the Council on Foreign Relations. All Rights Reserved.",
          "Council on Foreign Relations");
      put("Mises Institute", "Mises Institute");
      put("News – United Nations University", "United Nations University");
      put("The Henry J. Kaiser Family Foundation", "Kaiser Family Foundation");
      put("Polls and Surveys about Health Policy from the Kaiser Family Foundation",
          "Kaiser Family Foundation");
      put("Urban Center", "Urban Center");
    }
  };

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
   * Returns void. Creates a folder which will store folders for each news source
   * that will store their corresponding articles.
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
    String timestamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    byte[] strToBytes = timestamp.getBytes();
    // new File("timestamp.txt");
    final Path file = Paths.get("timestamp.txt");
    if (!Files.exists(file)) {
      try {
        Files.createFile(file);
      } catch (IOException e) {
        // TODO
      }
    } else {
      try {
        Files.write(file, strToBytes);
      } catch (IOException e) {
        // TODO Auto-generated catch block
      }
    }
  }

  /**
   * Create a text file of the article.
   */
  public static boolean createFile(final Document doc, final String sourceListName,
      final String titleListName, final String urlListName, boolean failure) {
    if (doc != null) {
      final ArrayList<String> lines = new ArrayList<String>();
      final ArrayList<String> headerLines = new ArrayList<String>();
      final ArrayList<String> footerLines = new ArrayList<String>();
      String sourceName;

      sourceName = map.get(sourceListName);

      if (sourceName == null) {
        sourceName = "";
        try (FileWriter fileWrite = new FileWriter("articles/noIfElse.txt", true);
            BufferedWriter buffRead = new BufferedWriter(fileWrite);
            PrintWriter output = new PrintWriter(buffRead)) {
          output.println(urlListName);
        } catch (IOException e) {
          // exception handling left as an exercise for the reader
        }
      }
      headerLines.add(titleListName);
      headerLines.add(sourceName);
      headerLines.add("");
      footerLines.add("");
      footerLines.add(urlListName);
      try {
        lines.add(doc.select("p").prepend("\n\n").text());
        final Path file = Paths.get(baseFolder + "/" + sourceName + "/"
            + titleListName.replaceAll("[^a-zA-Z0-9_\\-\\.]", "") + ".txt");
        Files.createFile(file);
        Files.write(file, headerLines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        Files.write(file, footerLines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
      } catch (FileAlreadyExistsException e) {
        failure = true;
      } catch (IOException e) {
        failure = true;
      } catch (NullPointerException e) {
        failure = true;
      }
    }
    return failure;
  }
}
