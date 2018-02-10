package extemp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
   * Name of the folder which will store every article.
   */
  private static String baseFolder = "articles";
  /**
   * Name of the file that stores a list of urls that have been indexed by the
   * program.
   */
  private static String sourceName = "index";

  /**
   * Returns void. JTextArea is the area on the screen where updates will be given
   * about the process of the indexing of articles
   *
   * @param textArea
   *          a text area to display messages
   * @see ArrayList
   * @see Connection
   */
  public void startDownload(final JTextArea textArea) {
    // Create a list of UrlInfo from the mySQL database
    Hashtable<String, String> login;
    login = login();
    final List<UrlInfo> urlClass = DatabaseConnector.connectDatabase(textArea, login);

    // Create files
    FileCreator.createFiles();
    /**
     * final List<String> storedUrlList = new ArrayList<String>(); Scanner inFile;
     * String token;
     * 
     * try { inFile = new Scanner(new File("articles/index.txt"));
     * 
     * while (inFile.hasNext()) { // find next line token = inFile.next();
     * storedUrlList.add(token); } inFile.close();
     * 
     * } catch (FileNotFoundException e) { // TODO }
     * textArea.append(storedUrlList.size() + "\n");
     * textArea.update(textArea.getGraphics());
     */

    /**
     * String urlString; for (final UrlInfo url : urlClass) { urlString = url.url;
     * for (int j = 0; j < storedUrlList.size(); j++) { if
     * (urlString.equals(storedUrlList.get(j))) { urlClass.remove(url); } } }
     */

    final long totalStartTime = System.currentTimeMillis();
    final int articleAmount = urlClass.size();

    textArea.append(articleAmount + " articles to cache\n");
    textArea.update(textArea.getGraphics());

    threads(urlClass, textArea);

    final long currentTime = System.currentTimeMillis();
    textArea.append("Completed in " + (currentTime - totalStartTime) / (60000) + "min\n");
    textArea.update(textArea.getGraphics());
  }
  
  public static Hashtable<String, String> login() {
    JPanel panel = new JPanel(new BorderLayout(5, 5));

    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
    label.add(new JLabel("E-Mail", SwingConstants.RIGHT));
    label.add(new JLabel("Password", SwingConstants.RIGHT));
    panel.add(label, BorderLayout.WEST);

    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
    JTextField username = new JTextField();
    controls.add(username);
    JPasswordField password = new JPasswordField();
    controls.add(password);
    panel.add(controls, BorderLayout.CENTER);

    JOptionPane.showMessageDialog(null, panel, "login", JOptionPane.QUESTION_MESSAGE);

    Hashtable<String, String> logininformation = new Hashtable<String, String>();
    logininformation.put("user", username.getText());
    logininformation.put("password", new String(password.getPassword()));
    return logininformation;
  }

  /**
   * Returns void. UrlList is the ArrayList of the Urls, titleList is the
   * ArrayList of titles, and sourceList is the ArrayList of sources.
   * 
   * <p>This method will run multiple threads which will each connect to a Url and
   * grab the text from the url and store that into a text file.
   *
   * @param urlList
   *          an ArrayList of urls
   * @param titleList
   *          an ArrayList of titles
   * @param sourceList
   *          an ArrayList of sources
   * @param jTextArea
   *          a text area to display information
   * @return void
   */
  private static void threads(final List<UrlInfo> urlClass, final JTextArea textArea) {
    long startTime;
    long currentTime;
    final int articleAmount = urlClass.size();
    int count = 0;
    int failureCount = 0;
    FileWriter fileWrite;
    BufferedWriter bufferedWrite;
    PrintWriter out;

    for (int j = 0; j < articleAmount - threadAmount; j += threadAmount) {
      final List<ThreadWorker> workers = new ArrayList<ThreadWorker>();
      startTime = System.currentTimeMillis();
      for (int i = 0; i < threadAmount; i++) {
        workers.add(new ThreadWorker(urlClass.get(j + i).url, urlClass.get(j + i).title,
            urlClass.get(j + i).source));
      }

      // We must force the main thread to wait for all the workers
      // to finish their work before we check to see how long it
      // took to complete
      for (final ThreadWorker worker : workers) {
        while (worker.isRunning()) {
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            // e.printStackTrace();
            Thread.currentThread().interrupt();
          }
        }
        if (worker.isFailure()) {
          failureCount++;
          try {
            fileWrite = new FileWriter("articles/failure.txt", true);
            bufferedWrite = new BufferedWriter(fileWrite);
            out = new PrintWriter(bufferedWrite);
            out.println(worker.getUrl());
            out.close();
          } catch (IOException e) {
            // TODO
          }
        } else {
          try {
            fileWrite = new FileWriter(baseFolder + "/" + sourceName + ".txt", true);
            bufferedWrite = new BufferedWriter(fileWrite);
            out = new PrintWriter(bufferedWrite);
            out.println(worker.getUrl());
            out.close();
          } catch (IOException e) {
            // TODO
          }
        }
      }
      count += threadAmount;
      currentTime = System.currentTimeMillis();
      textArea.append(
          (count - failureCount) + "/" + articleAmount + " completed: " + "Response time of "
              + (currentTime - startTime) + "ms" + "Total articles checked: " + (count) + "\n");
      textArea.update(textArea.getGraphics());
    }
  }
}