package com.seaboat.text.analyzer.distance;

import org.junit.Test;

public class CharEditDistanceTest {

	@Test
	public void test() {
		CharEditDistance cdd = new CharEditDistance();
		System.out.println(cdd.getEditDistance("what", "where"));
		System.out.println(cdd.getEditDistance("我们是中国人", "他们是日本人吖，四贵子"));
		System.out.println(cdd.getEditDistance("是我", "我是"));
	}

}
