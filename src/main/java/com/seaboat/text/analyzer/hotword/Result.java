package com.seaboat.text.analyzer.hotword;
/**
 * 
 * @author seaboat
 * @date 2017-05-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>the result of extraction.</p>
 */
public class Result {

  private float score;
  
  private int frequency;
  
  private String term;

  public Result(String term, int frequency, float score) {
   this.term=term;
   this.frequency = frequency;
   this.score = score;
  }

  public float getScore() {
    return score;
  }

  public void setScore(float score) {
    this.score = score;
  }

  public int getFrequency() {
    return frequency;
  }

  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }

  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }
  
  
  
}
