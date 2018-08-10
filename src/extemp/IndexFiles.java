package extemp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Index all text files under a directory. This will allow for fast searching
 * from the user.
 */
public class IndexFiles {
  /**
   * Folder that stores all of the index information.
   */
  private static String indexPath = "index";

  /**
   * Folder that stores all of the articles.
   */
  private static String docsPath = "articles";

  /**
   * Constructor does nothing.
   */
  public IndexFiles() {

  }

  /**
   * Index all text files under a directory.
   */
  public void startIndex() {
    final Path docDir = Paths.get(docsPath);

    try {
      Directory dir = FSDirectory.open(Paths.get(indexPath));
      Analyzer analyzer = new StandardAnalyzer();
      IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

      iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

      // Increase ram for better indexing performance
      iwc.setRAMBufferSizeMB(256.0);

      IndexWriter writer = new IndexWriter(dir, iwc);
      indexDocs(writer, docDir);

      // NOTE: if you want to maximize search performance,
      // you can optionally call forceMerge here. This can be
      // a terribly costly operation, so generally it's only
      // worth it when your index is relatively static (ie
      // you're done adding documents to it):
      //
      // writer.forceMerge(1);

      writer.close();

    } catch (IOException e) {
      // TODO
    }
  }

  /**
   * Indexes the given file using the given writer, or if a directory is given,
   * recurses over files and directories found under the given directory.
   * 
   * @param writer
   *          Writer to the index where the given file/dir info will be stored
   * @param path
   *          The file to index, or the directory to recurse into to find files to
   *          index
   * @throws IOException
   *           If there is a low-level I/O error
   */
  private void indexDocs(final IndexWriter writer, Path path) throws IOException {
    if (Files.isDirectory(path)) {
      Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          try {
            indexDoc(writer, file);
          } catch (IOException e) {
            // don't index files that can't be read.
          }
          return FileVisitResult.CONTINUE;
        }
      });
    } else {
      indexDoc(writer, path);
    }
  }

  /**
   * Indexes a single document.
   * 
   * @param writer
   *          The writer
   * @param file
   *          The file
   * @throws IOException
   *           IO error
   */
  private void indexDoc(IndexWriter writer, Path file) throws IOException {
    try (InputStream stream = Files.newInputStream(file)) {
      // Create a new document
      Document doc = new Document();

      // Add the file path to the document.
      Field pathField = new StringField("path", file.toString(), Field.Store.YES);
      doc.add(pathField);

      // Add the contents of the file to a field named "contents".
      doc.add(new TextField("contents",
          new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));

      if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
        // New index, so we just add the document since no other documents there
        writer.addDocument(doc);
      } else {
        // Existing index so we update the document
        writer.updateDocument(new Term("path", file.toString()), doc);
      }
    }
  }
}