package com.seaboat.text.analyzer.hotword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2017-05-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>index tester.</p>
 */
public class TextIndexerTest {

  @Test
  public void testCreateIndex() {
    for (int i = 1; i <= 5; i++) {
      File file = new File("test/test"+i+".txt");
      StringBuilder result = new StringBuilder();
      try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str = null;
        while ((str = br.readLine()) != null) {
          result.append(System.lineSeparator() + str);
        }
        br.close();
        long id = TextIndexer.index(result.toString());
        System.out.println(id);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
