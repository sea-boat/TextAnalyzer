package com.seaboat.text.analyzer.segment;

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
public class TeriTreeTest {

	@Test
	public void createAndDeleteTree() {
		TrieTree tree = new TrieTree();
		tree.put("美利坚");
		tree.put("美丽");
		tree.put("金币");
		tree.put("金子");
		tree.put("帝王");
		System.out.println(tree.get("美利坚") == null);
		System.out.println(tree.get("美利") == null);
		tree.remove("美利坚");
		System.out.println(tree.get("美利坚") == null);
		System.out.println(tree.get("美利") == null);
		System.out.println(tree.get("美丽") == null);
		tree.put("美利坚");
		System.out.println(tree.get("美利坚") == null);
		System.out.println(tree.get("美利") == null);
		tree.remove("金子");
		System.out.println(tree.get("金子") == null);
		System.out.println(tree.get("金币") == null);
	}

}
