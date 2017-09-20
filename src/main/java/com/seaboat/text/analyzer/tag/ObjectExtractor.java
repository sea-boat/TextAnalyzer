package com.seaboat.text.analyzer.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fnlp.nlp.cn.PartOfSpeech;
import org.fnlp.nlp.cn.tag.POSTagger;

import com.seaboat.text.analyzer.IObjectExtractor;

/**
 * 
 * @author seaboat
 * @date 2017-09-20
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>An address extractor that can extract some addresses in a text.</p>
 */
public class ObjectExtractor implements IObjectExtractor {
  private static POSTagger pos = null;

  static {
    try {
//      pos = new POSTagger("./model/seg.m", "./model/pos.m", new Dictionary("src/main/resources/tag.dic"));
      pos = new POSTagger("./model/seg.m", "./model/pos.m");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<String> extract(String text) {
    HashMap<String, String> map = new HashMap<String, String>();
    try {
      String[][] res = pos.tag2Array(text);
      if (res != null) {
        for (int j = 0; j < res[0].length; j++) {
          if (PartOfSpeech.isEntiry(res[1][j])) 
            map.put(res[0][j], res[1][j]);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    List list = new ArrayList();
    for (String key : map.keySet()) {
      String value = map.get(key);
      if (value.equals("实体名") || value.equals("专有名")) 
        list.add(key);
    }
    return list;
  }

}
