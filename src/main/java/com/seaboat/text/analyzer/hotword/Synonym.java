package com.seaboat.text.analyzer.hotword;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2017-05-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a model for synonym.</p>
 */
public class Synonym {

  private List<String> synonymList;

  public Synonym() {
    this.synonymList = new LinkedList<String>();
  }

  public void addSnonoym(String term) {
    this.synonymList.add(term);
  }

  public String getSynonym(String term) {
    for (int i = 1; i < synonymList.size(); i++) {
      if (synonymList.get(i).equals(term)) return synonymList.get(0);
    }
    return null;
  }
}
