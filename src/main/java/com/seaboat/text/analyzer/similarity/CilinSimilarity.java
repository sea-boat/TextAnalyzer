package com.seaboat.text.analyzer.similarity;

import java.util.Set;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.cilin.CilinDictionary;

/**
 * 
 * @author seaboat
 * @date 2018-06-04
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>calculate words's similarity via cilin.</p>
 */
public class CilinSimilarity implements ISimilarity {

	protected static Logger logger = Logger.getLogger(CilinSimilarity.class);
	public static double[] WEIGHT = new double[] { 1.2, 1.2, 1.0, 1.0, 0.8, 0.4 };
	public static double TOTAL_WEIGHT = 5.6;

	@Override
	public double getSimilarity(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return 1.0;
		} else if (s1 == null || s2 == null) {
			return 0.0;
		} else if (s1.equalsIgnoreCase(s2)) {
			return 1.0;
		}
		Set<String> codeSet1 = CilinDictionary.getInstance().getCilinCoding(s1);
		Set<String> codeSet2 = CilinDictionary.getInstance().getCilinCoding(s2);
		if (codeSet1 == null || codeSet2 == null) {
			return 0.0;
		}
		double similarity = 0.0;
		for (String code1 : codeSet1) {
			for (String code2 : codeSet2) {
				double s = sumWeight(code1, code2) / TOTAL_WEIGHT;
				logger.debug(code1 + "-" + code2 + "-" + sumWeight(code1, code2));
				if (similarity < s)
					similarity = s;
			}
		}
		return similarity;
	}

	public static double sumWeight(String code1, String code2) {
		double weight = 0.0;
		for (int i = 1; i <= 6; i++) {
			String c1 = getLevelCode(code1, i);
			String c2 = getLevelCode(code2, i);
			if (c1.equals(c2)) {
				weight += WEIGHT[i - 1];
			} else {
				break;
			}
		}
		return weight;
	}

	public static String getLevelCode(String code, int level) {
		switch (level) {
		case 1:
			return code.substring(0, 1);
		case 2:
			return code.substring(1, 2);
		case 3:
			return code.substring(2, 4);
		case 4:
			return code.substring(4, 5);
		case 5:
			return code.substring(5, 7);
		case 6:
			return code.substring(7);
		}
		return "";
	}
}
