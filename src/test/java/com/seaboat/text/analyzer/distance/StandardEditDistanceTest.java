package com.seaboat.text.analyzer.distance;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StandardEditDistanceTest {

	@Test
	public void test() {
		StandardEditDistance ed = new StandardEditDistance();
		String s1 = "我们是中国人";
		String s2 = "他们是日本人吖，四贵子";
		List list1 = new ArrayList<String>();
		list1.add(new EditBlock("计算机",""));
		list1.add(new EditBlock("多少",""));
		list1.add(new EditBlock("钱",""));
		List list2 = new ArrayList<String>();
		list2.add(new EditBlock("电脑",""));
		list2.add(new EditBlock("多少",""));
		list2.add(new EditBlock("钱",""));
		System.out.println(ed.getEditDistance(list1, list2));
	}

}
