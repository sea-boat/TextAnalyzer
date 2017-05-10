package com.seaboat.text.analyzer.hotword;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;

import com.seaboat.text.analyzer.Extractor;
import com.seaboat.text.analyzer.IDF;
import com.seaboat.text.analyzer.util.IndexUtil;

/**
 * 
 * @author seaboat
 * @date 2017-05-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>A hotword extractor can extract some hot words in a text.</p>
 */
public class HotWordExtractor implements Extractor {

  protected static Logger logger = Logger.getLogger(HotWordExtractor.class);

  private IDF idf;

  public HotWordExtractor() {
    this(new LuceneIDF());
  }

  public HotWordExtractor(IDF idf) {
    this.idf = idf;
  }

  public List<Result> extract(int id, int topN) {
    return extract(id, topN, false);
  }

  @Override
  public List<Result> extract(int id) {
    return extract(id, 10);
  }

  @Override
  public List<Result> extract(int id, int topN, boolean useScore) {
    List<Result> list = new LinkedList<Result>();
    try {
      IndexReader reader = IndexUtil.getIndexReader();
      Terms terms = reader.getTermVector(id, "content");
      TermsEnum termsEnum = terms.iterator();
      BytesRef thisTerm = null;
      while ((thisTerm = termsEnum.next()) != null) {
        String term = thisTerm.utf8ToString();
        float idfn = idf.getIDF(term);
        int a = (int) termsEnum.totalTermFreq();
        long b = terms.size();
        float tf = (float)a/b ;
        float score = idfn * tf;
        list.add(new Result(term, (int) termsEnum.totalTermFreq(), score));
      }
      if (useScore) {
        Collections.sort(list, new Comparator<Result>() {
          @Override
          public int compare(Result o1, Result o2) {
            int flag = (o2.getScore() - o1.getScore()) > 0 ? 1 : 0;
            return flag;
          }
        });
      } else {
        Collections.sort(list, new Comparator<Result>() {
          @Override
          public int compare(Result o1, Result o2) {
            return (o2.getFrequency() - o1.getFrequency());
          }
        });
      }
      return list.subList(0, topN);
    } catch (IOException e) {
      logger.error("IOException when getting reader. ", e);
      e.printStackTrace();
    }
    return null;
  }
}
