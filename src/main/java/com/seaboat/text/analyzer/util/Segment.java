package com.seaboat.text.analyzer.util;

import java.util.ArrayList;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.recognition.impl.FilterRecognition;
import org.ansj.splitWord.analysis.ToAnalysis;

/**
 * 
 * @author seaboat
 * @date 2017-12-02
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>word segment by ansj.</p>
 */
public class Segment {

  public static List<Term> Seg(String sentence) {
    FilterRecognition filter = new FilterRecognition();
    filter.insertStopWord(",", " ", ".", "，", "。", ":", "：", "'", "‘", "’", "　", "“", "”", "《", "》",
        "[", "]", "-");
    return ToAnalysis.parse(sentence).recognition(filter).getTerms();
  }

  public static List<String> getWords(String sentence) {
    List<Term> termList = Seg(sentence);
    List<String> wordList = new ArrayList<String>();
    for (Term wordTerm : termList) {
      wordList.add(wordTerm.getName());
    }
    return wordList;
  }
}
