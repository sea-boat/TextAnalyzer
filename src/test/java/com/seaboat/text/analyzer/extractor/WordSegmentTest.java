package com.seaboat.text.analyzer.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.junit.Test;

import com.seaboat.text.analyzer.util.WordSegmentUtil;

/**
 * 
 * @author seaboat
 * @date 2017-10-31
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>word segment tester.</p>
 */
public class WordSegmentTest {

  @Test
  public void testWordSegment() {
    List<String> list = WordSegmentUtil.seg("长期向寒溪河投放带有硝酸，硫酸等没有经过处理的废水，要求市长亲自处理并且答复。");
    for (String s : list)
      System.out.println(s);
  }
}
