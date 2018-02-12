package extemp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

/**
 * Database Connector class.
 * 
 * @author pjlak
 */
public class DatabaseConnector {
  /**
   * Returns a list of UrlInfo elements.
   * 
   * @param textArea
   *          a JTextArea to display information to the user.
   * @return a list of UrlInfo.
   */
  public static List<UrlInfo> connectDatabase(final JTextArea textArea,
      final Map<String, String> login) {
    final List<UrlInfo> urlClass = new ArrayList<UrlInfo>();
    final String timestamp = "2018-02-5";

    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e1) {
      // TODO
    }
    try (
        final Connection conn = DriverManager.getConnection(
            "jdbc:mysql://" + "sql122.main-hosting.eu" + "/" + "u445594861_art", login.get("user"),
            login.get("password"));
        final Statement statement1 = conn.createStatement();) {
      textArea.append("Database is Connected.");
      textArea.update(textArea.getGraphics());

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
    } catch (SQLException e) {
      textArea.append("Could not connect to database.");
      textArea.update(textArea.getGraphics());
      // e.printStackTrace();
    }
    return urlClass;
  }
}
