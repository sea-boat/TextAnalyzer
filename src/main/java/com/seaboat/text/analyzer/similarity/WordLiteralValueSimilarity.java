package com.seaboat.text.analyzer.similarity;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * @author seaboat
 * @date 2018-06-08
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>calculate words's similarity via literal value.</p>
 */
public class WordLiteralValueSimilarity implements ISimilarity {

	protected static Logger logger = Logger.getLogger(WordLiteralValueSimilarity.class);
	private double alpha = 0.6;
	private double beta = 0.4;

	@Override
	public double getSimilarity(String s1, String s2) {
		if (isBlank(s1) && isBlank(s2)) {
			return 1.0;
		}
		if (isBlank(s1) || isBlank(s2)) {
			return 0.0;
		}
		List<Character> sameHZ = new ArrayList<Character>();
		String longString = s1.length() >= s2.length() ? s1 : s2;
		String shortString = s1.length() < s2.length() ? s1 : s2;
		for (int i = 0; i < longString.length(); i++) {
			Character ch = longString.charAt(i);
			if (shortString.contains(ch.toString())) {
				sameHZ.add(ch);
			}
		}
		double dp = Math.min(1.0 * s1.length() / s2.length(), 1.0 * s2.length() / s1.length());
		double part1 = alpha * (1.0 * sameHZ.size() / s1.length() + 1.0 * sameHZ.size() / s2.length()) / 2.0;
		double part2 = beta * dp * (getWeightedResult(s1, sameHZ) + getWeightedResult(s2, sameHZ)) / 2.0;
		return part1 + part2;
	}

	private double getWeightedResult(String word1, List<Character> sameHZ) {
		double top = 0;
		double bottom = 0;
		for (int i = 0; i < word1.length(); i++) {
			if (sameHZ.contains(word1.charAt(i))) {
				top += (i + 1);
			}
			bottom += (i + 1);
		}
		return 1.0 * top / bottom;
	}

	private boolean isBlank(String str) {
		return str == null || str.trim().equals("");
	}

}
