package com.seaboat.text.analyzer.segment;

/**
 * 
 * @author seaboat
 * @date 2018-05-12
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>trie tree.</p>
 */
public class TrieTree {

	protected TrieNode root;

	public TrieTree() {
		this.root = new TrieNode(null);
	}

	public void put(String word) throws IllegalArgumentException {
		if (word == null) {
			throw new IllegalArgumentException();
		}
		TrieNode current = this.root;
		for (String s : word.split("")) {
			TrieNode child = current.getChild(s);
			if (child == null) {
				child = new TrieNode(s);
				current.setChild(child);
			}
			current = child;
		}
	}

	public TrieNode get(String word) throws IllegalArgumentException {
		if (word == null) {
			throw new IllegalArgumentException();
		}
		TrieNode current = this.root;
		for (String s : word.split("")) {
			TrieNode child = current.getChild(s);
			if (child == null)
				return null;
			current = child;
		}
		return current;
	}

	public void remove(String word) {
		if (word == null || word.length() <= 0) {
			return;
		}
		for (int i = 0; i < word.length(); i++) {
			String sub_word = word.substring(0, word.length() - i);
			TrieNode current = this.root;
			for (String s : sub_word.split("")) {
				TrieNode child = current.getChild(s);
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
		this.root = new TrieNode(null);
	}

}