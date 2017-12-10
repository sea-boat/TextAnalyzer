package com.seaboat.text.analyzer.tag;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.fnlp.nlp.cn.tag.NERTagger;

import com.seaboat.text.analyzer.IAddressExtractor;
import com.seaboat.text.analyzer.hotword.WordPopularityScore;

/**
 * 
 * @author seaboat
 * @date 2017-09-19
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>An address extractor that can extract some addresses in a text.</p>
 */
public class AddressExtractor implements IAddressExtractor {
  static NERTagger tag = null;
  static Properties prop = new Properties();
  static {
    InputStreamReader in;
    try {
      in = new InputStreamReader(AddressExtractor.class.getResourceAsStream("/system.properties"));
      prop.load(in);
      String seg_path = prop.getProperty("seg_path");
      String pos_path = prop.getProperty("pos_path");
      tag = new NERTagger(seg_path, pos_path);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<String> extract(String text) {
    HashMap<String, String> map = new HashMap<String, String>();
    tag.tag(text, map);
    List list = new ArrayList();
    for (String key : map.keySet()) {
      String value = map.get(key);
      if (value.equals("地名")) list.add(key);
    }
    return list;
  }

}
