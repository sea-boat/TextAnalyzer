package com.seaboat.text.analyzer.util;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * 
 * @author seaboat
 * @date 2017-05-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a basic index util.</p>
 */
public class IndexUtil {

  private static IndexReader indexReader;

  private static IndexWriter indexWriter;

  private static IndexSearcher indexSearcher;

  private static Directory directory;

  private static String path = "_indexdir";

  protected static Logger logger = Logger.getLogger(IndexUtil.class);

  public static IndexReader getIndexReader() throws IOException {
    if (null != indexReader) {
      return indexReader;
    } else {
      Directory directory = FSDirectory.open(Paths.get(path));
      return DirectoryReader.open(directory);
    }
  }

  public static synchronized IndexWriter getIndexWriter() throws IOException {
    if (null != indexWriter) {
      return indexWriter;
    } else {
      directory = FSDirectory.open(Paths.get(path));
      Analyzer analyzer = new IKAnalyzer(true);
      IndexWriterConfig config = new IndexWriterConfig(analyzer);
      config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
      indexWriter = new IndexWriter(directory, config);
      return indexWriter;
    }
  }

  public static IndexSearcher getIndexSearcher() throws IOException {

    if (null != indexSearcher) {
      return indexSearcher;
    } else {
      synchronized (IndexUtil.class) {
        indexSearcher = new IndexSearcher(getIndexReader());
      }
      return indexSearcher;
    }
  }

}
