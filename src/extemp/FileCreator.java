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
      // The Hill
      if ("TheHill - The Hill News".equals(sourceListName)) {
        sourceName = "The Hill";
      }
      // Think Progress
      else if ("ThinkProgress - Medium".equals(sourceListName)) {
        sourceName = "Think Progress";
      }
      // The Economist
      else if ("Americas".equals(sourceListName) || "Asia".equals(sourceListName)
          || "Britain".equals(sourceListName) || "United States".equals(sourceListName)
          || "Business and finance".equals(sourceListName) || "China".equals(sourceListName)
          || "Culture".equals(sourceListName) || "Europe".equals(sourceListName)
          || "Economics".equals(sourceListName) || "International".equals(sourceListName)
          || "Leaders".equals(sourceListName) || "Science and technology".equals(sourceListName)
          || "Middle East and Africa".equals(sourceListName)) {
        sourceName = "The Economist";
      }
      // Washington Post
      else if ("World".equals(sourceListName)) {
        sourceName = "Washington Post";
        lines.add(doc.select("div.row.text.font-accent.size-1x-small.color-darker-gray").text());
        lines.add(doc.select("div.pb-author-bio").text());
      }
      // Time Magazine
      else if ("World – TIME".equals(sourceListName) || "TIME".equals(sourceListName)
          || "Tech – TIME".equals(sourceListName) || "Politics – TIME".equals(sourceListName)
          || "Newsfeed – TIME".equals(sourceListName) || "Business – TIME".equals(sourceListName)) {
        sourceName = "Time Magazine";
        lines.add(doc.select("div.row.text.font-accent.size-1x-small.color-darker-gray").text());
        lines.add(doc.select("a.text.font-accent.color-brand.size-1x-small._1HynphR0").text());
      }
      // Washington Times
      else if ("www.washingtontimes.com stories: News".equals(sourceListName)
          || "www.washingtontimes.com stories: Culture".equals(sourceListName)
          || "Politics".equals(sourceListName)) {
        sourceName = "Washington Times";
        // lines.add(doc.select("span.source").text());
        // lines.add("");
        // lines.add(doc.select("div.storyareawrapper").text());
      }
      // Der Spiegel International
      else if ("SPIEGEL ONLINE - International".equals(sourceListName)) {
        sourceName = "Der Spiegel International";
      }
      // The Sydney Morning Herald
      else if ("Sydney Morning Herald RSS News Headlines".equals(sourceListName)) {
        sourceName = "The Sydney Morning Herald";
      }
      // The Aspen Institute
      else if ("The Aspen Institute".equals(sourceListName)) {
        sourceName = "The Aspen Institute";
      }
      // The Diplomat
      else if ("The Diplomat".equals(sourceListName)) {
        sourceName = "The Diplomat";
      }
      // Slate
      else if ("Slate Articles".equals(sourceListName)) {
        sourceName = "Slate";
      }
      // Open Society Foundations
      else if ("Open Society Foundations".equals(sourceListName)) {
        sourceName = "Open Society Foundations";
      }
      // Pambazuka News
      else if ("Pambazuka News - Pan-Africanism".equals(sourceListName)
          || "Pambazuka News - Land &amp; Environment".equals(sourceListName)
          || "Pambazuka News - ICT, Media &amp; Security".equals(sourceListName)
          || "Pambazuka News - Human Security".equals(sourceListName)
          || "Pambazuka News - Governance".equals(sourceListName)
          || "Pambazuka News - Global South".equals(sourceListName)
          || "Pambazuka News - Gender &amp; Minorities".equals(sourceListName)
          || "Pambazuka News - Advocacy &amp; Campaigns".equals(sourceListName)) {
        sourceName = "Pambazuka News";
      }
      // Peterson Institute for International Economics
      else if ("Peterson Institute Update".equals(sourceListName)) {
        sourceName = "Peterson Institute for International Economics";
      }
      // Pew Research Center
      else if ("Pew Research Center".equals(sourceListName)) {
        sourceName = "Pew Research Center";
      }
      // PolitiFact
      else if ("Politifact.com Truth-O-Meter rulings from National".equals(sourceListName)
          || "Politifact.com stories from National".equals(sourceListName)) {
        sourceName = "PolitiFact";
      }
      // Center for Immigration Studies
      else if ("Recent posts".equals(sourceListName)) {
        sourceName = "Center for Immigration Studies";
      }
      // International Monetary Fund
      else if ("RSS".equals(sourceListName)) {
        sourceName = "International Monetary Fund";
      }
      // Russian Times
      else if ("RT".equals(sourceListName)) {
        sourceName = "Russian Times";
      }
      // Mother Jones
      else if ("MoJo Articles | Mother Jones".equals(sourceListName)) {
        sourceName = "Mother Jones";
      }
      // New America
      else if ("New America".equals(sourceListName)) {
        sourceName = "New America";
      }
      // New Republic
      else if ("New Republic".equals(sourceListName)) {
        sourceName = "New Republic";
      }
      // Independent Institute
      else if ("Independent Institute Articles".equals(sourceListName)) {
        sourceName = "Independent Institute";
      }
      // The Brookings Institute
      else if ("Brookings: Up Front".equals(sourceListName)
          || "Latest From Brookings".equals(sourceListName)) {
        sourceName = "The Brookings Institute";
      }
      // Wilson Center
      else if ("Latest From the Wilson Center".equals(sourceListName)) {
        sourceName = "Wilson Center";
      }
      // IRIN
      else if ("All".equals(sourceListName)) {
        sourceName = "IRIN";
      }
      // Belfer Center
      else if ("All Research".equals(sourceListName)) {
        sourceName = "Belfer Center";
      }
      // Bipartisan Policy Center
      else if ("Bipartisan Policy Center".equals(sourceListName)) {
        sourceName = "Bipartisan Policy Center";
      }
      // Cato Institute
      else if ("Cato Recent Op-eds".equals(sourceListName)) {
        sourceName = "Cato Institute";
      }
      // Center on Budget and Policy Priorities
      else if ("Center on Budget: Comprehensive News Feed".equals(sourceListName)) {
        sourceName = "Center on Budget and Policy Priorities";
      }
      // Real Clear Politics
      else if ("Copyright 2008".equals(sourceListName)) {
        sourceName = "Real Clear Politics";
      }
      // Defense One
      else if ("Defense One - All Content".equals(sourceListName)) {
        sourceName = "Defense One";
      }
      // Economic Policy Institute
      else if ("Economic Policy Institute".equals(sourceListName)) {
        sourceName = "Economic Policy Institute";
      }
      // Foreign Policy
      else if ("Foreign Policy".equals(sourceListName)) {
        sourceName = "Foreign Policy";
      }
      // Foreign Policy in Focus
      else if ("Foreign Policy In FocusForeign Policy In Focus".equals(sourceListName)) {
        sourceName = "Foreign Policy in Focus";
      }
      // Lowy Institute
      else if ("Lowy Institute".equals(sourceListName)) {
        sourceName = "Lowy Institute";
      }
      // Freedom House
      else if ("Freedom House".equals(sourceListName)) {
        sourceName = "Freedom House";
      }
      // Gallup
      else if ("Gallup.Com".equals(sourceListName)) {
        sourceName = "Gallup";
      }
      // The Jerusalem Post
      else if (sourceListName.contains("The Jerusalem Post. All rights ")) {
        sourceName = "The Jerusalem Post";
      }
      // Financial Times
      else if (sourceListName.contains("Copyright The Financial Times Ltd ")) {
        sourceName = "Financial Times";
      }
      // LA Times
      else if (sourceListName.contains("Los Angeles Times")) {
        sourceName = "LA Times";
      }
      // Al Jazeera
      else if (sourceListName.contains("Aljazeera Network")) {
        sourceName = "Al Jazeera";
      }
      // Reuters
      else if (sourceListName.contains("Reuters")) {
        sourceName = "Reuters";
      }
      // All Africa
      else if (sourceListName.contains("AllAfrica News:")) {
        sourceName = "All Africa";
      }
      // Carnegie Endowment for International Peace
      else if (sourceListName.contains("Carnegie Endowment for International Peace")) {
        sourceName = "The Carnegie Endowment";
      }
      // American Progress
      else if (sourceListName.contains("Center for American ProgressCenter ")) {
        sourceName = "American Progress";
      }
      // Christian Science Monitor
      else if (sourceListName.contains("Christian Science Monitor ")) {
        sourceName = "Christian Science Monitor";
      }
      // Wall Street Journal
      else if (sourceListName.contains("Dow Jones &amp; Company, Inc.")) {
        sourceName = "Wall Street Journal";
      }
      // CNN
      else if (sourceListName.contains("Turner Broadcasting System, Inc")
          || sourceListName.contains("Cable News Network LP,")) {
        sourceName = "CNN";
      }
      // Rand Corporation
      else if (sourceListName.contains("The RAND Corporation")) {
        sourceName = "Rand Corporation";
      }
      // Copyright 2017
      // ecfr epic

      // The New York Times
      else if (sourceListName.contains("The New York Times Company")) {
        sourceName = "The New York Times";
      }
      // Bloomberg
      else if (sourceListName.contains("BLOOMBERG L.P. ALL RIGHTS RESERVED")) {
        sourceName = "Bloomberg";
      }
      // The Atlantic
      else if (sourceListName.contains("by The Atlantic Monthly Group.")) {
        sourceName = "The Atlantic";
      }
      // Council on Foreign Relations
      else if (sourceListName.contains("by the Council on Foreign Relations")) {
        sourceName = "Council on Foreign Relations";
      }
      // National Public Radio
      else if (sourceListName.contains("NPR - For Personal Use Only")) {
        sourceName = "National Public Radio";
      }
      // Merco Press
      else if (sourceListName.contains("MercoPress.")) {
        sourceName = "Merco Press";
      }
      // Toronto Star
      else if (sourceListName.contains("Copyright Toronto Star")) {
        sourceName = "Toronto Star";
      }
      // BBC
      else if (sourceListName.contains("Copyright: (C) British Broadcasting Corporation, ")) {
        sourceName = "BBC";
      }
      // Associated Press
      else if (sourceListName.contains("The Associated Press.")) {
        sourceName = "Associated Press";
      }
      // Fox News
      else if (sourceListName.contains("FOX News")) {
        sourceName = "Fox News";
      }
      // The Guardian
      else if (sourceListName.contains("Guardian News and Media Limited or its affiliated")) {
        sourceName = "The Guardian";
      }
      // Hoover Institution

      // Mises Institute
      else if (sourceListName.contains("Mises Institute")) {
        sourceName = "Mises Institute";
      }
      // Manhattan Institute

      // United Nations University
      else if (sourceListName.contains("News – United Nations University")) {
        sourceName = "United Nations University";
      }
      // Scientific American
      else if (sourceListName.contains("Scientific American, a Division of Nature America,")) {
        sourceName = "Scientific American";
      }
      // South China Morning Post
      else if (sourceListName.contains("South China Morning Post")) {
        sourceName = "South China Morning Post";
      }
      // The Kaiser Family Foundation
      else if (sourceListName.contains("Kaiser Family Foundation")) {
        sourceName = "Kaiser Family Foundation";
      }
      // The Huffington Post
      else if (sourceListName.contains("The Huffington Post | Full News Feed")) {
        sourceName = "Huffington Post";
      }
      // Urban Center
      else if (sourceListName.contains("Urban Center")) {
        sourceName = "Urban Center";
      } else {
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
