package com.seaboat.text.analyzer.clustering;

import java.util.List;

import org.junit.Test;

import com.seaboat.text.analyzer.util.DataReader;

/**
 * 
 * @author seaboat
 * @date 2017-06-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>vsm cluster tester.</p>
 */
public class VSMClusterTest {

  @Test
  public void testXMeans() {
    List<String> list = DataReader.readContent(VSMCluster.DATA_FILE);
    List<String> labels = new VSMCluster().learn(list);
    for (int j = 0; j < labels.size(); j++) {
      System.out.println("class " + j + " : ");
      String[] indexs = labels.get(j).split(",");
      for (int i = 0; i < indexs.length; i++)
        System.out.println(list.get(Integer.parseInt(indexs[i])));
    }
  }
}
