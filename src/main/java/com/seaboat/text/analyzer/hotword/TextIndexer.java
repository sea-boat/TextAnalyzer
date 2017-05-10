package com.seaboat.text.analyzer.hotword;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;

import com.seaboat.text.analyzer.util.IndexUtil;

/**
 * 
 * @author seaboat
 * @date 2017-05-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>Index the text.</p>
 */
public class TextIndexer {

  protected static Logger logger = Logger.getLogger(TextIndexer.class);

  public static long index(String text) {
    IndexWriter indexWriter = null;
    try {
      indexWriter = IndexUtil.getIndexWriter();
    } catch (IOException e) {
      logger.error("IOException when getting index writer. ", e);
    }
    FieldType type = new FieldType();
    type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
    type.setStored(true);
    type.setStoreTermVectors(true);
    type.setTokenized(true);
    Document doc = new Document();
    Field field = new Field("content", text, type);
    doc.add(field);
    try {
      long id = indexWriter.addDocument(doc);
      indexWriter.commit();
      return id - 1;
    } catch (IOException e) {
      logger.error("IOException when adding document. ", e);;
    }
    return -1;
  }

}
