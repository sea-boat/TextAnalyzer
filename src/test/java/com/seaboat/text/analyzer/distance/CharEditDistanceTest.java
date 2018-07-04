package com.seaboat.text.analyzer.distance;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-05-28
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>char level edit distance test.</p>
 */
public class CharEditDistanceTest {

	@Test
	public void test() {
		System.out.println(CharEditDistance.getEditDistance("what", "where"));
		System.out.println(CharEditDistance.getEditDistance("我们是中国人", "他们是日本人吖，四贵子"));
		System.out.println(CharEditDistance.getEditDistance("是我", "我是"));
	}

}
