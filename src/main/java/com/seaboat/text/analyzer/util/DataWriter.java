package com.seaboat.text.analyzer.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2017-09-19
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>Data Writer.</p>
 */
public class DataWriter {

  public static void writeContent(String f, List<String> contents) {
    File file = new File(f);
    FileWriter fw = null;
    BufferedWriter writer = null;
    try {
      fw = new FileWriter(file);
      writer = new BufferedWriter(fw);
      for (String s : contents) {
        writer.write(s);
        writer.newLine();
      }
      writer.flush();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        writer.close();
        fw.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
