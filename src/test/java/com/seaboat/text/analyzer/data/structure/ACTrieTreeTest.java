package com.seaboat.text.analyzer.data.structure;

import org.junit.Test;

import com.seaboat.text.analyzer.data.structure.ACTrieTree;

/**
 * 
 * @author seaboat
 * @date 2018-05-10
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>address extractor tester.</p>
 */
public class ACTrieTreeTest {

	@Test
	public void createAndDeleteTree() {
		ACTrieTree tree = new ACTrieTree();
		tree.put("美利坚");
		tree.put("美利");
		tree.put("美丽");
		tree.put("金币");
		tree.put("金子");
		tree.put("帝王");
		tree.put("he");
		tree.put("her");
		tree.put("she");
		tree.put("his");
		tree.put("hers");
		System.out.println(tree.acSearch("uhershe"));
		System.out.println(tree.acSearch("美利坚这个美国帝王金子啊"));
		System.out.println(tree.acSearch("美x利坚这个美国帝王金子啊"));
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
