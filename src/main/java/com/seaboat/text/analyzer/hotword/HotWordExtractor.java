package com.seaboat.text.analyzer.hotword;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;

import com.seaboat.text.analyzer.Extractor;
import com.seaboat.text.analyzer.IDF;
import com.seaboat.text.analyzer.ScoreFactor;
import com.seaboat.text.analyzer.util.IndexUtil;
import com.seaboat.text.analyzer.util.StringUtil;
import com.seaboat.text.analyzer.util.SynonymUtil;

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

  private ScoreFactor factor;

  public HotWordExtractor() {
    this(new LuceneIDF(), new WordPopularityScore());
  }

  public HotWordExtractor(IDF idf, ScoreFactor factor) {
    this.idf = idf;
    this.factor = factor;
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
        if ((term.length() > 1) && (!StringUtil.isNumericAndLetter(term))
            && (!StringUtil.isMobile(term)) && (!StringUtil.isPhone(term))
            && (!StringUtil.isContainNumber(term)) && (!StringUtil.isDate(term))) {
          float idfn = idf.getIDF(term);
          float scoreFactor = factor.getScoreFactor(term);
          int a = (int) termsEnum.totalTermFreq();
          long b = terms.size();
          float tf = (float) a / b;
          float score = idfn * tf + scoreFactor;
          list.add(new Result(term, (int) termsEnum.totalTermFreq(), score));
        }
      }
      // match the synonym
      List<Result> toRemove = new LinkedList<Result>();
      for (Result result : list) {
        String synonym;
        if ((synonym = SynonymUtil.getSynonym(result.getTerm())) != null) for (Result r : list) {
          if (r.getTerm().equals(synonym)) {
            r.setFrequency(r.getFrequency() + result.getFrequency());
            r.setScore(r.getScore() + result.getScore());
            toRemove.add(result);
          }
        }
      }
      for (Result r : toRemove)
        list.remove(r);
      if (useScore) {
        Collections.sort(list, new Comparator<Result>() {
          @Override
          public int compare(Result o1, Result o2) {
            Float f2 = Float.parseFloat(String.valueOf(o2.getScore()));
            Float f1 = Float.parseFloat(String.valueOf(o1.getScore()));
            return f2.compareTo(f1);
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
      if (list.size() > topN) return list.subList(0, topN);
      return list;
    } catch (IOException e) {
      logger.error("IOException when getting reader. ", e);
      e.printStackTrace();
    }
    return null;
  }
}
