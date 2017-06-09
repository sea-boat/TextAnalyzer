package com.seaboat.text.analyzer.clustering;

import java.util.List;

import com.seaboat.text.analyzer.IDF;
import com.seaboat.text.analyzer.hotword.LuceneMemoryIDF;
import com.seaboat.text.analyzer.util.VectorUtil;
import smile.clustering.XMeans;

/**
 * 
 * @author seaboat
 * @date 2017-06-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>clustering the texts by xmeans.</p>
 */
public class XMeansCluster {

  public static String DATA_FILE = "cluster/data.txt";

  private IDF idf = new LuceneMemoryIDF();

  public static int K = 10;

  public int[] learn(List<String> textList) {
    List<String> vectorList = VectorUtil.getVectorDimension(textList);
    double[][] datas = VectorUtil.getVector(textList.size(), vectorList, idf);
    XMeans xmeans = new XMeans(datas, K);
    return xmeans.getClusterLabel();
  }

}
