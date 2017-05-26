package com.seaboat.text.analyzer.hotword;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;

import com.seaboat.text.analyzer.IDF;
import com.seaboat.text.analyzer.util.MemoryIndexUtil;

/**
 * 
 * @author seaboat
 * @date 2017-05-25
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>inverse document frequency by lucene in momery.</p>
 */
public class LuceneMemoryIDF implements IDF {

  @Override
  public float getIDF(String term) {
    try {
      IndexReader reader = MemoryIndexUtil.getIndexReader();
      float a = reader.numDocs()+1;
      float b = reader.docFreq(new Term(term))+1;
      float idf = (float) Math.log(a / b);
      return idf;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

}
