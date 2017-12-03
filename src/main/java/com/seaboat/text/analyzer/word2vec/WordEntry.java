package com.seaboat.text.analyzer.word2vec;

/**
 * 
 * @author seaboat
 * @date 2017-12-02
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>bean.</p>
 */
public class WordEntry implements Comparable<WordEntry> {
  public String name;
  public float score;

  public WordEntry(String name, float score) {
    this.name = name;
    this.score = score;
  }

  public String toString() {
    return this.name + "\t" + score;
  }

  public int compareTo(WordEntry o) {
    if (this.score < o.score) {
      return 1;
    } else {
      return -1;
    }
  }

}
