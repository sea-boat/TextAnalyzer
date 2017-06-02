package com.seaboat.text.analyzer.util;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2017-05-24
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>memory index util.</p>
 */
public class MemoryIndexUtil {

  private static IndexReader indexReader;

  private static IndexWriter indexWriter;

  private static IndexSearcher indexSearcher;

  private static Directory directory = new RAMDirectory();;

  protected static Logger logger = Logger.getLogger(MemoryIndexUtil.class);

  public static IndexReader getIndexReader() throws IOException {
    if (null != indexReader) {
      return indexReader;
    } else {
      if (directory == null) throw new IOException("directory is null.");
      indexReader = DirectoryReader.open(directory);
      return indexReader;
    }
  }

  public static synchronized IndexWriter getIndexWriter() throws IOException {
    if (null != indexWriter) {
      return indexWriter;
    } else {
      Analyzer analyzer = new IKAnalyzer(true);
      IndexWriterConfig config = new IndexWriterConfig(analyzer);
      config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
      indexWriter = new IndexWriter(directory, config);
      return indexWriter;
    }
  }

  public static IndexSearcher getIndexSearcher() throws IOException {

    if (null != indexSearcher) {
      return indexSearcher;
    } else {
      synchronized (MemoryIndexUtil.class) {
        indexSearcher = new IndexSearcher(getIndexReader());
      }
      return indexSearcher;
    }
  }

  public static List<String> getToken(String text) {
    List<String> list = new ArrayList<String>();
    Analyzer anal = new IKAnalyzer(true);
    StringReader reader = new StringReader(text);
    TokenStream ts = anal.tokenStream("", reader);
    CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
    try {
      ts.reset();
      while (ts.incrementToken()) {
        list.add(term.toString());
      }
      reader.close();
      anal.close();
      ts.close();
    } catch (IOException e) {
      logger.error(e);
    }
    return list;
  }
}
