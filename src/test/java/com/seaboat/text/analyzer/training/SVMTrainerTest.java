package com.seaboat.text.analyzer.training;

import java.util.List;

import org.junit.Test;

import com.seaboat.text.analyzer.training.SVMTrainer;
import com.seaboat.text.analyzer.util.DataReader;

/**
 * 
 * @author seaboat
 * @date 2017-06-02
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>index tester.</p>
 */
public class SVMTrainerTest {

  @Test
  public void testCreateIndex() {

    SVMTrainer trainer = new SVMTrainer();
    trainer.train();
    List<String> list = DataReader.readContent(SVMTrainer.TEST_FILE);
    for (String text : list) {
      double[] data = trainer.getWordVector(text);
      System.out.println(trainer.predict(data));
    }
  }
}
