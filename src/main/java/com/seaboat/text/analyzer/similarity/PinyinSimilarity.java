package com.seaboat.text.analyzer.similarity;

import java.util.Set;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.distance.CharEditDistance;
import com.seaboat.text.analyzer.util.PinyinUtil;

/**
 * 
 * @author seaboat
 * @date 2018-06-11
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>calculate similarity of pinyin.</p>
 */
public class PinyinSimilarity implements ISimilarity {

	protected static Logger logger = Logger.getLogger(PinyinSimilarity.class);

	@Override
	public double getSimilarity(String s1, String s2) {
		Set<String> pinyinSet1 = PinyinUtil.getInstance().getPinyin(s1);
		Set<String> pinyinSet2 = PinyinUtil.getInstance().getPinyin(s2);

		double max = 0.0;
		for (String pinyin1 : pinyinSet1) {
			for (String pinyin2 : pinyinSet2) {
				double distance = CharEditDistance.getEditDistance(pinyin1, pinyin2);
				double similarity = 1
						- distance / ((pinyin1.length() > pinyin2.length()) ? pinyin1.length() : pinyin2.length());
				max = (max > similarity) ? max : similarity;
				if (max == 1.0) {
					return max;
				}
			}
		}
		return max;
	}

}
