package com.seaboat.text.analyzer.clustering;

import java.util.List;

import com.seaboat.text.analyzer.IDF;
import com.seaboat.text.analyzer.hotword.LuceneMemoryIDF;
import com.seaboat.text.analyzer.util.VectorUtil;

import smile.clustering.KMeans;

/**
 * 
 * @author seaboat
 * @date 2017-06-08
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>clustering the texts by kmeans.</p>
 */
public class KMeansCluster {

  public static String DATA_FILE = "cluster/data.txt";

  private IDF idf = new LuceneMemoryIDF();

  public static int K = 5;

  private static int ITERATE = 10000;

  public int[] learn(List<String> textList) {
    List<String> vectorList = VectorUtil.getVectorDimension(textList);
    double[][] datas = VectorUtil.getVector(textList.size(), vectorList, idf);
    KMeans kmeans = new KMeans(datas, K, ITERATE);
    return kmeans.getClusterLabel();
  }

}
