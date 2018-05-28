package com.seaboat.text.analyzer.distance;

import org.junit.Test;

public class StandardEditDistanceTest {

	@Test
	public void test() {
		StandardEditDistance ed = new StandardEditDistance();
		System.out.println(ed.getEditDistance("what", "where"));
	}

}
