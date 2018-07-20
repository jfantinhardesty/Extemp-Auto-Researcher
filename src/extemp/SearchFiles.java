package extemp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/** Simple command-line based search demo. */
public class SearchFiles {

  /**
   * 
   */
  private SearchFiles() {
  }

  /**
   * Simple command-line based search demo.
   * 
   * @param args
   * @throws Exception
   */
  public static void search(String queryString) throws Exception {
    String index = "index";
    String field = "contents";
    String queries = "articles";
    int repeat = 5;
    boolean raw = false;
    int hitsPerPage = 10000;

    IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
    IndexSearcher searcher = new IndexSearcher(reader);
    Analyzer analyzer = new StandardAnalyzer();

    BufferedReader in = null;
    in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    QueryParser parser = new QueryParser(field, analyzer);
    while (true) {
      String line = queryString != null ? queryString : in.readLine();

      if (line == null || line.length() == -1) {
        break;
      }

      line = line.trim();
      if (line.length() == 0) {
        break;
      }

      Query query = parser.parse(line);

      if (repeat > 0) { // repeat & time as benchmark
        Date start = new Date();
        for (int i = 0; i < repeat; i++) {
          searcher.search(query, 100);
        }
        Date end = new Date();
      }

      doPagingSearch(in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);

      if (queryString != null) {
        break;
      }
    }
    reader.close();
  }

  /**
   * This demonstrates a typical paging search scenario, where the search engine
   * presents pages of size n to the user. The user can then go to the next page
   * if interested in the next hits.
   * 
   * <p>
   * When the query is executed for the first time, then only enough results are
   * collected to fill 5 result pages. If the user wants to page beyond this
   * limit, then the query is executed another time and all hits are collected.
   * 
   * @param in
   * @param searcher
   * @param query
   * @param hitsPerPage
   * @param raw
   * @param interactive
   * @throws IOException
   * 
   */
  public static void doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query,
      int hitsPerPage, boolean raw, boolean interactive) throws IOException {

    // Collect enough docs to show 5 pages
    TopDocs results = searcher.search(query, 5 * hitsPerPage);
    ScoreDoc[] hits = results.scoreDocs;

    int numTotalHits = Math.toIntExact(results.totalHits);
    // System.out.println(numTotalHits + " total matching documents");

    int start = 0;
    int end = Math.min(numTotalHits, 10000);

    List<List<Object>> tableData = new ArrayList<List<Object>>();

    for (int i = start; i < end; i++) {
      List<Object> rowResult = new ArrayList<Object>();
      if (raw) { // output raw format
        // System.out.println("doc=" + hits[i].doc + " score=" + hits[i].score);
        continue;
      }

      Document doc = searcher.doc(hits[i].doc);
      String path = doc.get("path");
      if (path != null) {
        // System.out.println((i + 1) + ". " + path);
        String title = doc.get("title");
        if (title != null) {
          // System.out.println(" Title: " + doc.get("title"));
          title = "1";
        }

        rowResult.add(i);
        rowResult.add(path);
        rowResult.add(title);
        rowResult.add(hits[i].score);

        tableData.add(rowResult);

      } else {
        // System.out.println((i + 1) + ". " + "No path for this document");
      }
    }

    FileSelector.updateData(tableData);
  }
}