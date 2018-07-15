package com.seaboat.text.analyzer.data.structure;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

/**
 * 
 * @author seaboat
 * @date 2018-05-12
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>trie node on the tree.</p>
 */
public class ACTrieNode {

	private static Logger logger = Logger.getLogger(ACTrieNode.class);
	private ACTrieNode[] children;
	private byte[] value;
	private boolean deleted = false;
	private int status;
	private ACArray[] results = null;
	private ACTrieNode failureNode;
	private int frequency;
	private byte[] pos = null;
	private static String encoding = "utf-8";

	public ACTrieNode(String value) {
		try {
			this.value = value == null ? null : value.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			logger.warn("fail to create trie node. ", e);
		}
	}

	public boolean isEmpty() {
		return this.value == null && this.children == null;
	}

	public ACTrieNode[] getChildren() {
		return children;
	}

	public ACTrieNode getChild(String word) {
		try {
			byte[] w = word.getBytes(encoding);
			if (children == null)
				return null;
			for (ACTrieNode c : children) {
				if (isEqual(c.getValue(), w) && !c.deleted)
					return c;
			}
		} catch (UnsupportedEncodingException e) {
			logger.warn("fail to get child node. ", e);
		}
		return null;
	}

	private boolean isEqual(byte[] b1, byte[] b2) {
		if (b1.length != b2.length)
			return false;
		for (int i = 0; i < b1.length; i++) {
			if (b1[i] != b2[i])
				return false;
		}
		return true;
	}

	public boolean allChildrenDeleted() {
		if (children == null)
			return true;
		for (ACTrieNode c : children) {
			if (!c.deleted)
				return false;
		}
		return true;
	}

	public void setChild(ACTrieNode child) {
		if (children == null) {
			children = new ACTrieNode[1];
			children[0] = child;
		} else {
			ACTrieNode[] temp = children;
			children = new ACTrieNode[temp.length + 1];
			System.arraycopy(temp, 0, children, 0, temp.length);
			children[children.length - 1] = child;
		}
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(String value) {
		try {
			this.value = value.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			logger.warn("fail to set value. ", e);
		}
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void addResult(String word) {
		byte[] b;
		try {
			b = word.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			logger.warn("fail to add result. ", e);
			return;
		}
		if (results == null) {
			results = new ACArray[] { new ACArray(b) };
			return;
		}
		ACArray[] temp = new ACArray[results.length + 1];
		System.arraycopy(results, 0, temp, 0, results.length);
		temp[results.length] = new ACArray(b);
		results = temp;
	}

	public ACArray[] getResults() {
		return results;
	}

	public ACTrieNode getFailureNode() {
		return failureNode;
	}

	public void setFailureNode(ACTrieNode failureNode) {
		this.failureNode = failureNode;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public byte[] getPos() {
		return pos;
	}

	public void setPos(String pos) {
		try {
			this.pos = pos.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			logger.warn("toString error. ", e);
		}
	}

	public String toString() {
		try {
			if (value != null)
				return "id = " + status + "; value = " + new String(value, "utf-8") + "; failure node = "
						+ (failureNode == null ? "null" : failureNode.getStatus());
		} catch (UnsupportedEncodingException e) {
			logger.warn("toString error. ", e);
		}
		return null;
	}

}
