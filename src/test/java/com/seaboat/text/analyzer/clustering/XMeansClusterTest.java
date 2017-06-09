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
 * <p>xmeans cluster tester.</p>
 */
public class XMeansClusterTest {

  @Test
  public void testXMeans() {
    List<String> list = DataReader.readContent(XMeansCluster.DATA_FILE);
    int[] labels = new XMeansCluster().learn(list);
    for (int j = 0; j < XMeansCluster.K; j++) {
      System.out.println("class " + j + " : ");
      for (int i = 0; i < labels.length; i++)
        if (labels[i] == j) System.out.println(list.get(i));
    }
  }
}
