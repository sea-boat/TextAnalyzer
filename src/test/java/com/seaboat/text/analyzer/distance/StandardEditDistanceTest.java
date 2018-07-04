package com.seaboat.text.analyzer.distance;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-05-28
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>word level edit distance test.</p>
 */
public class StandardEditDistanceTest {

	@Test
	public void test() {
		List<EditBlock> list1 = new ArrayList<EditBlock>();
		list1.add(new EditBlock("计算机", ""));
		list1.add(new EditBlock("多少", ""));
		list1.add(new EditBlock("钱", ""));
		List<EditBlock> list2 = new ArrayList<EditBlock>();
		list2.add(new EditBlock("电脑", ""));
		list2.add(new EditBlock("多少", ""));
		list2.add(new EditBlock("钱", ""));
		System.out.println(StandardEditDistance.getEditDistance(list1, list2));
	}

}
