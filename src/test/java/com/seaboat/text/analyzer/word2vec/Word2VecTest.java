package com.seaboat.text.analyzer.word2vec;


import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2017-12-02
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>word2vec tester.</p>
 */
public class Word2VecTest {

  @Test
  public void test() {
    Word2Vec vec = Word2Vec.getInstance();
    System.out.println("狗|猫: " + vec.wordSimilarity("狗", "猫"));
    System.out.println("电脑|计算机: " + vec.wordSimilarity("电脑", "计算机"));
  }
}

