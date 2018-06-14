package com.seaboat.text.analyzer.similarity;

import java.util.Collection;

import com.seaboat.text.analyzer.hownet.Constants;
import com.seaboat.text.analyzer.hownet.HownetSememe;

/**
 * 
 * @author from https://github.com/iamxiatian/xsimilarity
 * @date 2018-06-06
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>calculate similarity of hownet sememe.</p>
 */
public class HownetSememeSimilarity implements ISimilarity {

	@Override
	public double getSimilarity(String s1, String s2) {
		if (isBlank(s1) && isBlank(s2)) {
			return 1.0;
		} else if ((isBlank(s1) && !isBlank(s2)) || (!isBlank(s1) && isBlank(s2))) {
			return 0.0;
		} else if (s1.equals(s2)) {
			return 1.0;
		}
		String key1 = s1.trim();
		String key2 = s2.trim();
		if ((key1.charAt(0) == '(') && (key1.charAt(key1.length() - 1) == ')')) {
			if (key2.charAt(0) == '(' && key2.charAt(key2.length() - 1) == ')') {
				key1 = key1.substring(1, key1.length() - 1);
				key2 = key2.substring(1, key2.length() - 1);
			} else {
				return 0.0;
			}
		}
		int pos = key1.indexOf('=');
		if (pos > 0) {
			int pos2 = key2.indexOf('=');
			if ((pos == pos2) && key1.substring(0, pos).equals(key2.substring(0, pos2))) {
				key1 = key1.substring(pos + 1);
				key2 = key2.substring(pos2 + 1);
			} else {
				return 0.0;
			}
		}
		String symbol1 = key1.substring(0, 1);
		String symbol2 = key2.substring(0, 1);
		for (int i = 0; i < Constants.Symbol_Descriptions.length; i++) {
			if (symbol1.equals(Constants.Symbol_Descriptions[i][0])) {
				if (symbol1.equals(symbol2)) {
					key1 = s1.substring(1);
					key2 = s2.substring(1);
					break;
				} else {
					return 0.0;
				}
			}
		}
		if ((pos = key1.indexOf("|")) >= 0) {
			key1 = key1.substring(pos + 1);
		}
		if ((pos = key2.indexOf("|")) >= 0) {
			key2 = key2.substring(pos + 1);
		}
		if (key1.equals(key2)) {
			return 1.0;
		}
		return getMaxSimilarity(key1, key2);
	}

	public double getMaxSimilarity(String s1, String s2) {
		double maxValue = 0.0;
		if (s1.equals(s2)) {
			return 1.0;
		}
		Collection<String> sememeIds1 = HownetSememe.getInstance().getSememeIds(s1);
		Collection<String> sememeIds2 = HownetSememe.getInstance().getSememeIds(s2);
		if (sememeIds1.size() == 0 || sememeIds1.size() == 0) {
			return 0.0;
		}
		for (String id1 : sememeIds1) {
			for (String id2 : sememeIds2) {
				double value = getSimilarityBySememeId(id1, id2);
				if (value > maxValue) {
					maxValue = value;
				}
			}
		}
		return maxValue;
	}

	private double getSimilarityBySememeId(final String id1, final String id2) {
		int position = 0;
		String[] array1 = id1.split("-");
		String[] array2 = id2.split("-");
		for (position = 0; position < array1.length && position < array2.length; position++) {
			if (!array1[position].equals(array2[position])) {
				break;
			}
		}
		return 2.0 * position / (array1.length + array2.length);
	}

	private boolean isBlank(String s1) {
		return s1.equals("") || s1 == null;
	}

}
