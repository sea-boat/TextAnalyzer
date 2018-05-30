package com.seaboat.text.analyzer.similarity;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-05-28
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>sentence similarity test.</p>
 */
public class SentenceSimilarityTest {

	@Test
	public void test() {
		String s1 = "我们是中国人";
		String s2 = "他们是日本人，四贵子";
		SentenceSimilarity ss = new SentenceSimilarity();
		System.out.println(ss.getSimilarity(s1, s2));
		s1 = "我们是中国人";
		s2 = "我们是中国人";
		System.out.println(ss.getSimilarity(s1, s2));
		s1 = "电脑多少钱";
		s2 = "计算机多少钱";
		System.out.println(ss.getSimilarity(s1, s2));
		s1 = "手机能修好吗";
		s2 = "计算机多少钱";
		System.out.println(ss.getSimilarity(s1, s2));
		s1 = "请问套餐1多少钱";
		s2 = "有什么好套餐推荐吗";
		System.out.println(ss.getSimilarity(s1, s2));
	}

}
