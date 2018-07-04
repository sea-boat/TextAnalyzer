package com.seaboat.text.analyzer.clustering;

import java.util.ArrayList;
import java.util.List;

import com.seaboat.text.analyzer.IDF;
import com.seaboat.text.analyzer.hotword.LuceneMemoryIDF;
import com.seaboat.text.analyzer.util.VectorUtil;

/**
 * 
 * @author seaboat
 * @date 2017-06-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>clustering the texts by vector space model.</p>
 */
public class VSMCluster {

	public static String DATA_FILE = "cluster/data.txt";

	private IDF idf = new LuceneMemoryIDF();

	private static double SIMILARITY = 0.01;

	public List<String> learn(List<String> textList) {
		List<String> vectorList = VectorUtil.getVectorDimension(textList);
		double[][] datas = VectorUtil.getVector(textList.size(), vectorList, idf);
		List<String> sortTexts = new ArrayList<String>();
		for (int i = 0; i < datas.length; i++) {
			if (i == 0) {
				sortTexts.add("0");
				continue;
			}
			sortBySimilarity(i, sortTexts, datas);
		}
		return sortTexts;
	}

	private void sortBySimilarity(int index, List<String> sortTexts, double[][] datas) {
		boolean flag = false;
		for (int i = 0; i < sortTexts.size(); i++) {
			String[] labels = sortTexts.get(i).split(",");
			for (int j = 0; j < labels.length; j++) {
				double[] textVector = datas[Integer.parseInt(labels[j])];
				double similarity = getSimilarity(textVector, datas[index]);
				// if the similarity is greater than SIMILARITY, it's a same class.
				if (similarity >= SIMILARITY) {
					sortTexts.set(i, sortTexts.get(i) + "," + index);
					flag = true;
					break;
				}
			}
			// has got it,break;
			if (flag)
				return;
		}
		// it must be another class.
		sortTexts.add(String.valueOf(index));
	}

	private double getSimilarity(double[] textVector, double[] ds) {
		double sumA = 0;
		double sumB = 0;
		double sumC = 0;
		for (int i = 0; i < ds.length; i++) {
			sumA += Math.sqrt(textVector[i]);
			sumB += Math.sqrt(ds[i]);
			sumC += textVector[i] * ds[i];
		}
		return (double) sumC / ((Math.sqrt(sumA) * Math.sqrt(sumB)));
	}
}
