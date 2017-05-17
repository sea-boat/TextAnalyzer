package com.seaboat.text.analyzer;

/**
 * 
 * @author seaboat
 * @date 2017-05-17
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a weight interface provides all kinds of weight.</p>
 */
public interface ScoreFactor {

  public float getScoreFactor(String term);
  
}
