package extemp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

/**
 * Responsible for connecting to the mySQL database to download and index
 * articles.
 */
public class DatabaseConnector {

  /**
   * Constructor responsible for connecting to the database.
   */
  public DatabaseConnector() {

  }

  /**
   * Connects to the mySQL database and returns a list of UrlInfo elements.
   * 
   * @param textArea
   *          a JTextArea to display information to the user.
   * @param login
   *          Map containing the username and password for the database
   * @param date
   *          date when articles will be indexed from.
   * @return a list of UrlInfo containing the url, source, and title of each
   *         article
   */
  public List<UrlInfo> connectDatabase(final JTextArea textArea, final Map<String, String> login,
      final String date) {
    final List<UrlInfo> urlClass = new ArrayList<>();

    final LocalDate currentDate = LocalDate.now();
    String timestamp = null;

    // Determine which button was pressed so we download the correct amount of
    // articles
    if ("Past Week".equals(date)) {
      timestamp = currentDate.minusDays(7).toString();
    } else if ("Past Month".equals(date)) {
      timestamp = currentDate.minusMonths(1).toString();
    } else if ("Past 3 Months".equals(date)) {
      timestamp = currentDate.minusMonths(3).toString();
    } else if ("Past 6 Months".equals(date)) {
      timestamp = currentDate.minusMonths(6).toString();
    } else {
      final Path file = Paths.get("timestamp.txt");

      // Check that the timestamp path is valid to prevent crash
      if (Files.exists(file)) {
        BufferedWriter fileWrite;
        try {
          fileWrite = Files.newBufferedWriter(Paths.get("timestamp.txt"));
          final BufferedWriter buffRead = new BufferedWriter(fileWrite);
          final PrintWriter output = new PrintWriter(buffRead);
          output.println(timestamp);
          output.close();
        } catch (final IOException e) {
          timestamp = currentDate.minusMonths(6).toString();
        }
      }
    }

    // Connect to the database
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (final ClassNotFoundException e1) {
      // TODO
    }

    // Try to login to the database
    try (
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://" + "sql122.main-hosting.eu" + "/" + "u445594861_art", login.get("user"),
            login.get("password"));
        Statement statement1 = conn.createStatement()) {
      textArea.append("Database is Connected.");
      textArea.update(textArea.getGraphics());

      // Return the url, title and source from the database for each article
      try (ResultSet result1 = statement1
          .executeQuery("SELECT url, title, " + "source FROM `my_posts` " + "WHERE date > " + "'"
              + timestamp + "'" + "ORDER BY `my_posts`.`date` DESC")) {
        while (result1.next()) {
          urlClass.add(new UrlInfo(result1.getString("url"), result1.getString("title"),
              result1.getString("source")));
        }
        textArea.append("\nConnection is closed.");
        textArea.update(textArea.getGraphics());
      }
    } catch (final SQLException e) {
      textArea.append("Could not connect to database.");
      textArea.update(textArea.getGraphics());
    }
    return urlClass;
  }
}
