package com.seaboat.text.analyzer.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fnlp.nlp.cn.tag.NERTagger;

import com.seaboat.text.analyzer.IAddressExtractor;

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

  static {
    try {
      tag = new NERTagger("./model/seg.m", "./model/pos.m");
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
