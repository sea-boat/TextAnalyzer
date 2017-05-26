package com.seaboat.text.analyzer.hotword;

import java.util.List;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2017-05-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>hot word extractor tester.</p>
 */
public class HotWordExtractorTest {

  @Test
  public void testExtract() {
    HotWordExtractor extractor = new HotWordExtractor();
    List<Result> list = extractor.extract(21, 20, true);
    if (list != null) for (Result s : list)
      System.out.println(s.getTerm() + " : " + s.getFrequency() + " : " + s.getScore());
  }
}
