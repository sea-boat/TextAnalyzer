package com.seaboat.text.analyzer.similarity;

import java.util.ArrayList;
import java.util.List;

import com.seaboat.text.analyzer.distance.EditBlock;
import com.seaboat.text.analyzer.distance.StandardEditDistance;
import com.seaboat.text.analyzer.util.Segment;

/**
 * 
 * @author seaboat
 * @date 2018-05-28
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>sentence similarity implements by word2vec and edit distance.</p>
 */
public class SentenceSimilarity implements ISimilarity {

	@Override
	public double getSimilarity(String s1, String s2) {
		List<EditBlock> list1 = new ArrayList<EditBlock>();
		List<EditBlock> list2 = new ArrayList<EditBlock>();
		for (String s : Segment.getWords(s1))
			list1.add(new EditBlock(s, ""));
		for (String s : Segment.getWords(s2))
			list2.add(new EditBlock(s, ""));
		double a = StandardEditDistance.getEditDistance(list1, list2);
		double b = Math.max(StandardEditDistance.getEditDistance(list1, new ArrayList<EditBlock>()),
				StandardEditDistance.getEditDistance(new ArrayList<EditBlock>(), list2));
		return 1 - ((a + 0.1) / (b + 0.1));
	}

}
