package com.seaboat.text.analyzer.similarity;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-06-06
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>hownet sememe similarity test.</p>
 */
public class HownetSememeSimilarityTest {

	@Test
	public void test() {
		String s1 = "有";
		String s2 = "无";
		HownetSememeSimilarity cs = new HownetSememeSimilarity();
		System.out.println(cs.getSimilarity(s1, s2));
		s1 = "死";
		s2 = "灭亡";
		System.out.println(cs.getSimilarity(s1, s2));
		s1 = "主题";
		s2 = "事件";
		System.out.println(cs.getSimilarity(s1, s2));
	}

}
