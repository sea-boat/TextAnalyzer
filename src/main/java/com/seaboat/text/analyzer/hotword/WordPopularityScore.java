package com.seaboat.text.analyzer.hotword;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.ScoreFactor;

/**
 * 
 * @author seaboat
 * @date 2017-05-17
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>Word popularity Score is mapping to word-popularity.properties file. This means that the popularity of each word. </p>
 */
public class WordPopularityScore implements ScoreFactor {
  protected static Logger logger = Logger.getLogger(WordPopularityScore.class);
  static Properties prop = new Properties();
  static {
    InputStreamReader in;
    try {
      in = new InputStreamReader(Object.class.getResourceAsStream("/word-popularity.properties"), "UTF-8");
      prop.load(in);
    } catch (FileNotFoundException e) {
      logger.error("File not found", e);
    } catch (IOException e) {
      logger.error("IOException", e);
    }
  }

  @Override
  public float getScoreFactor(String term) {
    Object o = prop.get(term);
    if (o != null) return Float.parseFloat(String.valueOf(o));
    return 0;
  }

}
