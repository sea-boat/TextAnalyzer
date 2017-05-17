package com.seaboat.text.analyzer.hotword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Test;


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
