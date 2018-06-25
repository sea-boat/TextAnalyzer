package com.seaboat.text.analyzer.tendency;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-06-25
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>tendency test.</p>
 */
public class TendencyTest {

	@Test
	public void test() {
		WordSentimentTendency tendency = new WordSentimentTendency();
		System.out.println(tendency.getTendency("鼓励"));
		System.out.println(tendency.getTendency("高兴"));
		System.out.println(tendency.getTendency("伤心"));
		System.out.println(tendency.getTendency("生气"));
	}

}
