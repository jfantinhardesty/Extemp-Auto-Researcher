package extemp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Runs the main program by connecting to a database and retrieving urls and
 * storing them into text files.
 */
public class MainEngine {
  /**
   * Total amount of threads to be used in the program default set to 100.
   */
  private static int threadAmount = 100;

  /**
   * Connects to the database.
   */
  private final DatabaseConnector connector;

  /**
   * Creates the various files and folders.
   */
  private final FileCreator fileCreator;

  /**
   * Constructor that creates the FileCreator and DatabaseConnector.
   */
  public MainEngine() {
    connector = new DatabaseConnector();
    fileCreator = new FileCreator();
  }

  /**
   * Starts downloading articles.
   *
   * @param textArea
   *          a text area to display messages
   * @param date
   *          date when articles will be indexed from.
   */
  public void startDownload(final JTextArea textArea, final String date) {
    // Create a list of UrlInfo from the mySQL database
    Map<String, String> login;
    login = login();

    final List<UrlInfo> urlDownloads = connector.connectDatabase(textArea, login, date);

    // Create files
    fileCreator.createSetup();

    final List<String> storedUrlList = new ArrayList<String>();
    Scanner inFile;
    String token;
    try {
      inFile = new Scanner(new File("articles/index.txt"));
      while (inFile.hasNext()) {
        // find next line
        token = inFile.next();
        storedUrlList.add(token);
      }
      inFile.close();
    } catch (FileNotFoundException e) {
      // TODO
    }

    // Remove files that have already been downloaded.
    urlDownloads.removeIf(u -> storedUrlList.contains(u.getUrl()));

    final long totalStartTime = System.currentTimeMillis();

    textArea.append(urlDownloads.size() + " articles to cache\n");
    textArea.update(textArea.getGraphics());

    threads(urlDownloads, textArea);

    final long currentTime = System.currentTimeMillis();
    textArea.append("Completed in " + (currentTime - totalStartTime) / 60000 + "min\n");
    textArea.update(textArea.getGraphics());

    textArea.append("Now starting the indexing process. Please do not close this window.");
    textArea.update(textArea.getGraphics());

    IndexFiles index = new IndexFiles();
    index.startIndex();
    textArea.append("Completed the indexing process. You can now search your files.");
    textArea.update(textArea.getGraphics());

  }

  /**
   * Creates a popup windows for the user to login to the MySQL database.
   * 
   * @return A map containing the associated strings from the database.
   */
  public static Map<String, String> login() {
    final JPanel panel = new JPanel(new BorderLayout(5, 5));

    final JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
    label.add(new JLabel("E-Mail", SwingConstants.RIGHT));
    label.add(new JLabel("Password", SwingConstants.RIGHT));
    panel.add(label, BorderLayout.WEST);

    final JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
    final JTextField username = new JTextField();
    controls.add(username);
    final JPasswordField password = new JPasswordField();
    controls.add(password);
    panel.add(controls, BorderLayout.CENTER);

    JOptionPane.showMessageDialog(null, panel, "login", JOptionPane.QUESTION_MESSAGE);

    final Map<String, String> logininformation = new ConcurrentHashMap<>();
    logininformation.put("user", username.getText());
    logininformation.put("password", new String(password.getPassword()));
    return logininformation;
  }

  /**
   * This method will run multiple threads which will each connect to a Url and
   * grab the text from the url and store that into a text file.
   *
   * @param urlClass
   *          a list of UrlInfor containing all article information
   * @param textArea
   *          a JTextArea to display information to the user.
   */
  private static void threads(final List<UrlInfo> urlClass, final JTextArea textArea) {
    long startTime;
    long currentTime;
    final int articleAmount = urlClass.size();
    int count = 0;
    int failureCount = 0;
    final FileCreator fileCreator = new FileCreator();

    for (int j = 0; j < articleAmount - threadAmount; j += threadAmount) {
      final List<ThreadWorker> workers = new ArrayList<>();
      startTime = System.currentTimeMillis();
      for (int i = 0; i < threadAmount; i++) {
        workers.add(new ThreadWorker(urlClass.get(j + i)));
      }

      // We must force the main thread to wait for all the workers
      // to finish their work before we check to see how long it
      // took to complete
      for (final ThreadWorker worker : workers) {
        while (worker.isRunning()) {
          try {
            Thread.sleep(100);
          } catch (final InterruptedException e) {
            // e.printStackTrace();
            Thread.currentThread().interrupt();
          }
        }

        if (worker.isRunning()) {
          // If the worker is running, then the worker timed out so we consider it to be a
          // failure
          failureCount++;
          fileCreator.addAsFailure(worker.getUrl());
        } else {
          // If it ended, then it must have been a success
          fileCreator.addAsSuccess(worker.getUrl());
        }
      }

      count += threadAmount;
      currentTime = System.currentTimeMillis();
      textArea
          .append(count - failureCount + "/" + articleAmount + " completed: " + "Response time of "
              + (currentTime - startTime) + "ms" + "Total articles checked: " + count + "\n");
      textArea.update(textArea.getGraphics());
    }
  }
}