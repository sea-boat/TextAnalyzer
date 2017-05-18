package com.seaboat.text.analyzer.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.hotword.Synonym;

/**
 * 
 * @author seaboat
 * @date 2017-05-17
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>synonym util for operating synonym.dic.</p>
 */
public class SynonymUtil {
  protected static Logger logger = Logger.getLogger(SynonymUtil.class);
  private static List<Synonym> synonyms = new LinkedList<Synonym>();
  static {
    InputStreamReader read;
    try {
      read = new InputStreamReader(Object.class.getResourceAsStream("/synonym.dic"), "UTF-8");
      BufferedReader bufferedReader = new BufferedReader(read);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] ss = line.split(";");
        Synonym synonym = new Synonym();
        for (String s : ss)
          synonym.addSnonoym(s);
        synonyms.add(synonym);
      }
    } catch (FileNotFoundException e) {
      logger.error("File not found", e);
    } catch (IOException e) {
      logger.error("IOException", e);
    }
  }

  public static String getSynonym(String term) {
    String str;
    for (Synonym s : synonyms)
      if ((str = s.getSynonym(term)) != null) return str;
    return null;
  }

}
