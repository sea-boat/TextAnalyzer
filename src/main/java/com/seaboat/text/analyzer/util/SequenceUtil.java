package com.seaboat.text.analyzer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

/**
 * 
 * @author seaboat
 * @date 2017-05-17
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a util to operate sequence.</p>
 */
public class SequenceUtil {

  private static long id;

  private static long maxId;

  private static long delta = 50;

  private static String FILE = "sequence";
  
  protected static Logger logger = Logger.getLogger(SequenceUtil.class);

  static {
    iniMaxId();
  }

  public static synchronized long getId() {
    if (id >= maxId) {
      maxId = id + delta;
      writeToSequenceFile(maxId);
    }
    return ++id ;
  }

  private static void writeToSequenceFile(long maxId) {
    try {
      FileWriter fw = new FileWriter(FILE);
      fw.write(String.valueOf(maxId));
      fw.close();
    } catch (IOException e) {
      logger.error("IOException", e);
    }
  }

  public static void iniMaxId() {
    File file = new File(FILE);
    InputStreamReader read;
    try {
      read = new InputStreamReader(new FileInputStream(file), "utf-8");
      BufferedReader bufferedReader = new BufferedReader(read);
      String lineTxt = bufferedReader.readLine() ;
      if(lineTxt != null) {
        id = Long.parseLong(lineTxt);
        maxId = id + delta;
      }
      bufferedReader.close();
      // avoid suffering the repeating id
      writeToSequenceFile(maxId);
    } catch (UnsupportedEncodingException | FileNotFoundException e) {
      logger.error("Exception", e);
    } catch (IOException e) {
      logger.error("IOException", e);
    }
  }

}
