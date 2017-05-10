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
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;

import com.seaboat.text.analyzer.Extractor;
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

  public List<String> extract(int id,int topN) {
    List<String> list = new LinkedList<String>();
    try {
      IndexReader reader = IndexUtil.getIndexReader();
      Terms terms = reader.getTermVector(id, "content");
      TermsEnum termsEnum = terms.iterator();
      BytesRef thisTerm = null;
      Map<String, Integer> map = new HashMap<String, Integer>();
      while ((thisTerm = termsEnum.next()) != null) {
        String termText = thisTerm.utf8ToString();
        map.put(termText, (int) termsEnum.totalTermFreq());
      }
      List<Map.Entry<String, Integer>> sortedMap = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
      Collections.sort(sortedMap, new Comparator<Map.Entry<String, Integer>>() {
          public int compare(Map.Entry<String, Integer> kv1, Map.Entry<String, Integer> kv2) {
              return (kv2.getValue() - kv1.getValue());
          }
      });
      for (int i = 0; i < topN; i++) 
        list.add(sortedMap.get(i).getKey() );
      return list ;
    } catch (IOException e) {
      logger.error("IOException when getting reader. ", e);
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<String> extract(int id) {
    return extract(id, 10);
  }
}
