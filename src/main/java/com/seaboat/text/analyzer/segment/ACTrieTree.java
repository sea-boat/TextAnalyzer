package com.seaboat.text.analyzer.segment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * @author seaboat
 * @date 2018-05-12
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>trie tree.</p>
 */
public class ACTrieTree {

	private static Logger logger = Logger.getLogger(ACTrieTree.class);
	protected ACTrieNode root;
	private static int status = 1;

	public ACTrieTree() {
		this.root = new ACTrieNode(null);
	}

	public void put(String word) throws IllegalArgumentException {
		if (word == null) {
			throw new IllegalArgumentException();
		}
		//build goto function 
		ACTrieNode current = this.root;
		String[] ss = word.split("");
		for (int i = 0; i < ss.length; i++) {
			String s = ss[i];
			ACTrieNode child = current.getChild(s);
			if (child == null) {
				child = new ACTrieNode(s);
				current.setChild(child);
				child.setStatus(status++);
			}
			current = child;
			//build failure function
			if (i == 0) {
				current.setFailureNode(this.root);
			} else {
				ACTrieNode failureNode = null;
				label1: for (int j = i; j > 0; j--) {
					String temp = word.substring(i - j + 1, i + 1);
					failureNode = get(temp);
					if (failureNode != null)
						break label1;
				}
				if (failureNode == null)
					failureNode = this.root;
				current.setFailureNode(failureNode);
			}
		}
		//build output function
		current.addResult(word);
	}

	public ACTrieNode get(String word) throws IllegalArgumentException {
		if (word == null) {
			throw new IllegalArgumentException();
		}
		ACTrieNode current = this.root;
		for (String s : word.split("")) {
			ACTrieNode child = current.getChild(s);
			if (child == null)
				return null;
			current = child;
		}
		return current;
	}

	public List<String> acSearch(String str) {
		List<String> list = new ArrayList<String>();
		ACTrieNode current = this.root;
		for (String s : str.split("")) {
			ACTrieNode child = current.getChild(s);
			//jump the prefix which is not exist in teri tree.
			if (child == null && current == root)
				continue;
			if (child == null) {
				current = current.getFailureNode();
				//jump the word which is not exist in teri tree.
				if (current == this.root && current.getChild(s) == null)
					continue;
				child = current.getChild(s);
				if (child != null)
					current = child;
				else
					current = this.root;
			}
			if (System.getProperty("Debug") != null && System.getProperty("Debug").equalsIgnoreCase("true"))
				if (child != null)
					System.out.println(child.toString());
			current = child;
			if (current.getResults() != null) {
				ACArray[] results = current.getResults();
				for (ACArray arr : results)
					try {
						list.add(new String(arr.getValue(), "utf-8"));
					} catch (UnsupportedEncodingException e) {
						logger.warn("fail to search trie tree. ", e);
					}
			}
		}
		return list;
	}

	public void remove(String word) {
		if (word == null || word.length() <= 0) {
			return;
		}
		for (int i = 0; i < word.length(); i++) {
			String sub_word = word.substring(0, word.length() - i);
			ACTrieNode current = this.root;
			for (String s : sub_word.split("")) {
				ACTrieNode child = current.getChild(s);
				//not exist
				if (child == null)
					return;
				if (child != null && (child.getChildren() == null || child.allChildrenDeleted()))
					child.setDeleted(true);
				current = child;
			}
		}
	}

	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	public void clear() {
		this.root = new ACTrieNode(null);
	}

}