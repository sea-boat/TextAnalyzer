package com.seaboat.text.analyzer.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 
 * @author seaboat
 * @date 2017-10-31
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>word segment util.</p>
 */
public class WordSegmentUtil {

  public static List<String> seg(String text) {
    List<String> list = new ArrayList<String>();
    StringReader re = new StringReader(text);
    IKSegmenter ik = new IKSegmenter(re, true);
    Lexeme lex = null;
    try {
      while ((lex = ik.next()) != null)
        list.add(lex.getLexemeText());

    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }

}
