package com.seaboat.text.analyzer.similarity;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-06-11
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>pinyin similarity test.</p>
 */
public class PinyinSimilarityTest {

	@Test
	public void test() {
		String s1 = "中";
		String s2 = "中";
		PinyinSimilarity cs = new PinyinSimilarity();
		System.out.println(cs.getSimilarity(s1, s2));
		s1 = "今天";
		s2 = "明天";
		System.out.println(cs.getSimilarity(s1, s2));
	}

}
