package com.seaboat.text.analyzer.clustering;

import java.util.List;

import org.junit.Test;

import com.seaboat.text.analyzer.util.DataReader;

/**
 * 
 * @author seaboat
 * @date 2017-06-08
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>kmeans cluster tester.</p>
 */
public class KMeansClusterTest {

  @Test
  public void testKMeans() {
    List<String> list = DataReader.readContent(KMeansCluster.DATA_FILE);
    int[] labels = new KMeansCluster().learn(list);
    for (int j = 0; j < KMeansCluster.K; j++) {
      System.out.println("class " + j + " : ");
      for (int i = 0; i < labels.length; i++)
        if (labels[i] == j) System.out.println(list.get(i));
    }
  }
}
