package com.seaboat.text.analyzer.segment;

/**
 * 
 * @author seaboat
 * @date 2018-05-12
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>trie node on the tree.</p>
 */
public class TrieNode {

	private TrieNode[] children;
	private String value;
	private boolean deleted = false;

	public TrieNode(String value) {
		this.value = value == null ? null : value.intern();
	}

	public boolean isEmpty() {
		return this.value == null && this.children == null;
	}

	public TrieNode[] getChildren() {
		return children;
	}

	public TrieNode getChild(String word) {
		if (children == null)
			return null;
		for (TrieNode c : children) {
			if (c.getValue() == word.intern() && !c.deleted)
				return c;
		}
		return null;
	}

	public boolean allChildrenDeleted() {
		if (children == null)
			return true;
		for (TrieNode c : children) {
			if (!c.deleted)
				return false;
		}
		return true;
	}

	public void setChild(TrieNode child) {
		if (children == null) {
			children = new TrieNode[1];
			children[0] = child;
		} else {
			TrieNode[] temp = children;
			children = new TrieNode[temp.length + 1];
			System.arraycopy(temp, 0, children, 0, temp.length);
			children[children.length - 1] = child;
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return deleted;
	}

}
