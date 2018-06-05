package com.seaboat.text.analyzer.similarity;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-06-04
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>cilin similarity test.</p>
 */
public class CilinSimilarityTest {

	@Test
	public void test() {
		String s1 = "中国人";
		String s2 = "炎黄子孙";
		CilinSimilarity cs = new CilinSimilarity();
		System.out.println(cs.getSimilarity(s1, s2));
		s1 = "汽车";
		s2 = "摩托";
		System.out.println(cs.getSimilarity(s1, s2));
		s1 = "人";
		s2 = "动物";
		System.out.println(cs.getSimilarity(s1, s2));
		s1 = "猫";
		s2 = "狗";
		System.out.println(cs.getSimilarity(s1, s2));
		s1 = "今天";
		s2 = "明天";
		System.out.println(cs.getSimilarity(s1, s2));
	}

}
