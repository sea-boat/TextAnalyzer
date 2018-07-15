package com.seaboat.text.analyzer.data.structure;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-05-10
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>address extractor tester.</p>
 */
public class DoubleArrayTrieTest {

	@Test
	public void createAndDeleteTree() {
		DoubleArrayTrie tree = new DoubleArrayTrie();
		List<String> list = new ArrayList<String>();
		list.add("一丝不紊");
		list.add("一丝两气");
		list.add("一丝半粟");
		list.add("一串骊珠");
		list.add("一举万里");
		list.add("一举万里啊");
		list.add("一举三得");
		list.add("一举两全");
		list.add("一举两全");
		list.add("你");
		list.add("你们");
		list.add("你们是谁");
		System.out.println(tree.build(list));
		list = null;
		System.out.println(tree.commonPrefixSearch("一丝不挂"));
		System.out.println(tree.commonPrefixSearch("你们是谁啊"));
		System.out.println(tree.commonPrefixSearch("一举两全"));
		System.out.println(tree.exactMatchSearch("一举两全"));
	}

}
