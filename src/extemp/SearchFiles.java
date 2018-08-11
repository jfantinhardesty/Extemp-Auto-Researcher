package extemp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
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

/**
 * Responsible for searching the index and getting the search results to add to
 * the java table.
 */
public class SearchFiles {

  /**
   * Constructor does nothing.
   */
  public SearchFiles() {
    
  }

  /**
   * Searches the index based on the string that the user types in.
   * 
   * @param queryString
   *          String that will be searched for
   * @throws Exception
   *           Generic Exception
   */
  public static void search(String queryString) throws Exception {
    String index = "index";
    String field = "contents";
    // String queries = "articles";
    int repeat = 5;
    // boolean raw = false;
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

      if (repeat > 0) {
        for (int i = 0; i < repeat; i++) {
          searcher.search(query, 100);
        }
      }

      doPagingSearch(in, searcher, query, hitsPerPage);

      if (queryString != null) {
        break;
      }
    }
    reader.close();
  }

  /**
   * Searches the index and finds up to 10,000 hits.
   * 
   * @param in
   *          Buffered Reader
   * @param searcher
   *          Index Searcher
   * @param query
   *          Query
   * @param maxHits
   *          Maximum number of hits to show
   * @throws IOException
   *           IOException
   * 
   */
  public static void doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query,
      int maxHits) throws IOException {

    TopDocs results = searcher.search(query, maxHits);
    ScoreDoc[] hits = results.scoreDocs;

    int numTotalHits = Math.toIntExact(results.totalHits);

    int start = 0;
    int end = Math.min(numTotalHits, maxHits);

    List<List<Object>> tableData = new ArrayList<List<Object>>();

    for (int i = start; i < end; i++) {
      List<Object> rowResult = new ArrayList<Object>();

      Document doc = searcher.doc(hits[i].doc);
      String path = doc.get("path");
      if (path != null) {
        String title = doc.get("title");
        if (title == null) {
          title = "1";
        }

        rowResult.add(i);
        rowResult.add(path);
        rowResult.add(title);
        rowResult.add(hits[i].score);

        tableData.add(rowResult);

      }
    }

    // Display the results in the table for the user.
    FileSelector.updateData(tableData);
  }
}