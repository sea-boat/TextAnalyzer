package com.seaboat.text.analyzer.extractor;

import java.util.ArrayList;
import java.util.List;

import com.seaboat.text.analyzer.util.DataReader;
import com.seaboat.text.analyzer.util.DataWriter;
import com.seaboat.text.analyzer.util.WordSegmentUtil;

/**
 * 
 * @author seaboat
 * @date 2017-10-31
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>preprocess the samples. for we want to tag the named entity, 
 * so we should do word segment first,and mark the position of the named entity.</p>
 */
public class SegmentFile {

  private static String FILE = "data/test.txt";
  private static String segFILE = "data/tag_test.txt";

  public static void main(String[] args) {
    List<String> texts = DataReader.readContent(FILE);
    List<String> segTexts = new ArrayList<String>();
    for (String text : texts) {
      List<String> words = WordSegmentUtil.seg(text);
      String segmented = "";
      for (String word : words) {
        segmented += word + "/";
      }
      segmented = segmented.substring(0, segmented.length() - 1);
      segmented = segmented+"###";
      segTexts.add(segmented);
    }
    DataWriter.writeContent(segFILE, segTexts);
  }

}
